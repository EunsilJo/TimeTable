package com.github.eunsiljo.timetablelib.data;

/**
 * Created by EunsilJo on 2017. 11. 21..
 */

public class TimeGridData {
    private TimeData time;
    private int rowCount = 1;

    public TimeGridData(int rowCount) {
        this.rowCount = rowCount;
    }

    public TimeGridData(TimeData time, int rowCount) {
        this.time = time;
        this.rowCount = rowCount;
    }

    public TimeData getTime() {
        return time;
    }

    public int getRowCount() {
        return rowCount;
    }
}
