package com.grishberg.services.sheduler;

import com.grishberg.data.models.sheduler.ScheduleElement;

import java.util.List;

/**
 * Created by grishberg on 22.12.16.
 */
public interface ScheduleStorage {
    boolean saveSchedule(List<ScheduleElement> elements);

    List<ScheduleElement> loadSchedule();
}
