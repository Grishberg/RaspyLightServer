package com.grishberg.services.sheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grishberg.data.models.sheduler.ScheduleContainer;
import com.grishberg.data.models.sheduler.ScheduleElement;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grishberg on 22.12.16.
 */
public class FileScheduleStorageImpl implements ScheduleStorage {
    public static final String PATHNAME = "schedules.json";
    private final ObjectMapper mapper;

    public FileScheduleStorageImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public boolean saveSchedule(List<ScheduleElement> elements) {
        try {
            String prettyJson = mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(new ScheduleContainer(elements));
            try (PrintStream out = new PrintStream(new FileOutputStream(PATHNAME))) {
                out.print(prettyJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ScheduleElement> loadSchedule() {
        ScheduleContainer container;
        try {
            container = mapper.readValue(new File(PATHNAME), ScheduleContainer.class);
        } catch (IOException e) {
            container = null;
            e.printStackTrace();
        }
        ArrayList<ScheduleElement> result = new ArrayList<>();
        if (container != null && container.getElements() != null) {
            for (ScheduleElement element : container.getElements()) {
                result.add(element);
            }
        }
        return result;
    }
}
