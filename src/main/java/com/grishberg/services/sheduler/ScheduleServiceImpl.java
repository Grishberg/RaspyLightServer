package com.grishberg.services.sheduler;

import com.grishberg.data.models.sheduler.ScheduleElement;

import java.util.List;

/**
 * Created by grishberg on 22.12.16.
 */
public class ScheduleServiceImpl implements SchedulerService {
    private final ScheduleStorage storage;
    private final List<ScheduleElement> elements;

    public ScheduleServiceImpl(ScheduleStorage storage) {
        this.storage = storage;
        elements = storage.loadSchedule();
    }

    @Override
    public List<ScheduleElement> getSchedules() {
        return elements;
    }

    @Override
    public void addSchedule(ScheduleElement element) {
        elements.add(element);
        storage.saveSchedule(elements);
    }

    @Override
    public boolean removeSchedule(long id) {
        int index = -1;
        for (int i = 0, l = elements.size(); i < l; i++) {
            if (elements.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            elements.remove(index);
            return true;
        }
        return false;
    }
}
