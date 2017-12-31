package com.github.eunsiljo.timetablelib.utils;

import android.content.Context;

import org.joda.time.DateTime;

import java.util.ArrayList;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.data.TimeData;


/**
 * Created by EunsilJo on 2017. 11. 21..
 */

public class TableUtils {

    public static ArrayList<TimeData> getValidateTimeData(long startMillis, long endMillis,
                                                          ArrayList<TimeData> values){
        ArrayList<TimeData> result = new ArrayList<>();
        if(values != null && values.size() != 0){
            long prevEnd = startMillis;

            for(TimeData value : values){
                long start = value.getStartMills();
                long end = value.getStopMills();

                if(start < endMillis && end > prevEnd && start < end){
                    if(start < startMillis){
                        start = startMillis;
                    }
                    if(end > endMillis){
                        end = endMillis;
                    }
                    result.add(new TimeData(value.getKey(), value.getTitle(), value.getColorRes(),
                            value.getTextColorRes(), start, end, value.isShowError()));

                    prevEnd = value.getStopMills();
                }
            }
        }
        return result;
    }

    public static String formatTime(Context context, int time) {
        int h = time / 3600;
        int m = (time - h * 3600) / 60;
        String strTime;
        strTime = String.format(context.getString(R.string.time_table_format_time_h_m), h, m);
        return strTime;
    }

    public static String formatTime(Context context, long time) {
        DateTime date = new DateTime(time);
        int h = date.getHourOfDay();
        int m = date.getMinuteOfHour();
        String strTime;
        strTime = String.format(context.getString(R.string.time_table_format_time_h_m), h, m);
        return strTime;
    }
}
