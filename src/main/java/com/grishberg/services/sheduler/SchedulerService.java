package com.grishberg.services.sheduler;

import com.grishberg.data.models.sheduler.ScheduleElement;

import java.util.List;

/**
 * Created by grishberg on 22.12.16.
 */
public interface SchedulerService {
    List<ScheduleElement> getSchedules();

    void addSchedule(ScheduleElement element);

    boolean removeSchedule(long id);
}
