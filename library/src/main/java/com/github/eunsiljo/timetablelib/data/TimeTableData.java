package com.github.eunsiljo.timetablelib.data;

import java.util.ArrayList;


/**
 * Created by EunsilJo on 2017. 11. 21..
 */

public class TimeTableData {
    private String header;
    private ArrayList<TimeData> values;

    public TimeTableData(String header, ArrayList<TimeData> values) {
        this.header = header;
        this.values = values;
    }

    public String getHeader() {
        return header;
    }

    public ArrayList<TimeData> getValues() {
        return values;
    }
}
