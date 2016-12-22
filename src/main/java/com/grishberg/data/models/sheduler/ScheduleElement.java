package com.grishberg.data.models.sheduler;

import lombok.Getter;
import lombok.Setter;

import java.util.BitSet;

/**
 * Created by grishberg on 22.12.16.
 */
@Getter
@Setter
public class ScheduleElement {
    private long id;
    private String dayOfWeek;
    private long dateTime;
    private boolean isEnabled;

    public ScheduleElement() {
    }

    public ScheduleElement(final String dow, long dateTime, boolean isEnabled) {
        dayOfWeek = dow;
        this.dateTime = dateTime;
        this.isEnabled = isEnabled;
    }

    private static BitSet fromString(final String string) {
        return BitSet.valueOf(new long[]{Long.parseLong(string, 2)});
    }
}
