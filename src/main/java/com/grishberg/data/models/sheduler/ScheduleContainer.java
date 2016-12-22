package com.grishberg.data.models.sheduler;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by grishberg on 23.12.16.
 */
@Getter
@Setter
public class ScheduleContainer {
    private List<ScheduleElement> elements;

    public ScheduleContainer() {
    }

    public ScheduleContainer(final List<ScheduleElement> elements) {
        this.elements = elements;
    }
}
