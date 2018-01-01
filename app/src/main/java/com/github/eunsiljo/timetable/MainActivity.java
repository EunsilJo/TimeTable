package com.github.eunsiljo.timetable;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private View btnMode;
    private TimeTableView timeTable;

    private ArrayList<TimeTableData> mShortSamples = new ArrayList<>();
    private ArrayList<TimeTableData> mLongSamples = new ArrayList<>();

    private List<String> mTitles = Arrays.asList("Korean", "English", "Math", "Science", "Physics", "Chemistry", "Biology");
    private List<String> mLongHeaders =  Arrays.asList("Plan", "Do");
    private List<String> mShortHeaders = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

    private long mNow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initListener();
        initData();
    }

    private void initLayout() {
        btnMode = findViewById(R.id.btnMode);
        timeTable = (TimeTableView)findViewById(R.id.timeTable);
    }

    private void initListener() {
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleMode();

                if(v.isActivated()){
                    timeTable.setShowHeader(false);
                    timeTable.setTableMode(TimeTableView.TableMode.SHORT);
                    //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mShortSamples);
                    timeTable.setTimeTable(mNow, getSamples(mNow, mShortHeaders, mTitles));

                }else{
                    timeTable.setShowHeader(true);
                    timeTable.setTableMode(TimeTableView.TableMode.LONG);
                    //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
                    timeTable.setTimeTable(mNow, getSamples(mNow, mLongHeaders, mTitles));
                }
            }
        });

        timeTable.setOnTimeItemClickListener(new TimeTableItemViewHolder.OnTimeItemClickListener() {
            @Override
            public void onTimeItemClick(View view, int position, TimeGridData item) {
                TimeData time = item.getTime();
                showToast(MainActivity.this,
                        time.getTitle() + ", " + new DateTime(time.getStartMills()).toString() +
                        " ~ " + new DateTime(time.getStopMills()).toString());
            }
        });
    }

    private void initData(){
        //initLongSamples();
        //initShortSamples();

        timeTable.setStartHour(4);
        timeTable.setShowHeader(true);
        timeTable.setTableMode(TimeTableView.TableMode.LONG);

        DateTime now = DateTime.now();
        mNow = now.withTimeAtStartOfDay().getMillis();

        //timeTable.setTimeTable(getMillis("2017-11-10 00:00:00"), mLongSamples);
        timeTable.setTimeTable(mNow, getSamples(mNow, mLongHeaders, mTitles));
    }

    private ArrayList<TimeTableData> getSamples(long date, List<String> headers, List<String> titles){
        int[] colors_table = getResources().getIntArray(R.array.colors_table);
        int[] colors_table_light = getResources().getIntArray(R.array.colors_table_light);

        ArrayList<TimeTableData> tables = new ArrayList<>();
        for(int i=0; i<headers.size(); i++){
            ArrayList<TimeData> values = new ArrayList<>();
            DateTime start = new DateTime(date);
            DateTime end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
            for(int j=0; j<titles.size(); j++){
                int color = colors_table_light[j];
                int textColor = R.color.black;
                //TEST
                if(headers.size() == 2 && i == 1){
                    color = colors_table[j];
                    textColor = R.color.white;
                }

                TimeData timeData = new TimeData(j, titles.get(j), color, textColor, start.getMillis(), end.getMillis());

                //TEST
                if(headers.size() == 2 && j == 2){
                    timeData.setShowError(true);
                }
                values.add(timeData);

                start = end.plusMinutes((int)((Math.random() * 10) + 1) * 10);
                end = start.plusMinutes((int)((Math.random() * 10) + 1) * 30);
            }

            tables.add(new TimeTableData(headers.get(i), values));
        }
        return tables;
    }

    private void initLongSamples(){
        //TEST
        ArrayList<TimeData> values = new ArrayList<>();
        values.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 04:00:00"), getMillis("2017-11-10 05:00:00")));
        values.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:00:00"), getMillis("2017-11-10 08:00:00")));

        ArrayList<TimeData> values2 = new ArrayList<>();
        values2.add(new TimeData(0, "Korean", R.color.color_table_1, R.color.white, getMillis("2017-11-10 03:00:00"), getMillis("2017-11-10 06:00:00")));

        TimeData timeData = new TimeData(1, "English", R.color.color_table_2, R.color.white, getMillis("2017-11-10 07:30:00"), getMillis("2017-11-10 08:55:00"));
        timeData.setShowError(true);
        values2.add(timeData);

        values2.add(new TimeData(2, "Math", R.color.color_table_3, R.color.white, getMillis("2017-11-10 10:40:00"), getMillis("2017-11-10 11:45:00")));
        values2.add(new TimeData(3, "Science", R.color.color_table_4, R.color.white, getMillis("2017-11-10 15:00:00"), getMillis("2017-11-10 17:10:00")));
        values2.add(new TimeData(4, "Physics", R.color.color_table_5, R.color.white, getMillis("2017-11-10 17:30:00"), getMillis("2017-11-10 21:30:00")));
        values2.add(new TimeData(5, "Chemistry", R.color.color_table_6, R.color.white, getMillis("2017-11-10 21:31:00"), getMillis("2017-11-10 22:45:00")));
        values2.add(new TimeData(6, "Biology", R.color.color_table_7, R.color.white, getMillis("2017-11-10 23:00:00"), getMillis("2017-11-11 02:30:00")));

        ArrayList<TimeTableData> tables = new ArrayList<>();
        tables.add(new TimeTableData("Plan", values));
        tables.add(new TimeTableData("Do", values2));

        mLongSamples.addAll(tables);
    }

    private void initShortSamples(){
        //TEST
        ArrayList<TimeData> values = new ArrayList<>();
        values.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 04:00:00"), getMillis("2017-11-10 05:00:00")));
        values.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:00:00"), getMillis("2017-11-10 08:00:00")));

        ArrayList<TimeData> values2 = new ArrayList<>();
        values2.add(new TimeData(0, "Korean", R.color.color_table_1_light, getMillis("2017-11-10 03:00:00"), getMillis("2017-11-10 06:00:00")));
        values2.add(new TimeData(1, "English", R.color.color_table_2_light, getMillis("2017-11-10 07:30:00"), getMillis("2017-11-10 08:30:00")));
        values2.add(new TimeData(2, "Math", R.color.color_table_3_light, getMillis("2017-11-10 11:40:00"), getMillis("2017-11-10 11:45:00")));
        values2.add(new TimeData(3, "Science", R.color.color_table_4_light, getMillis("2017-11-10 18:00:00"), getMillis("2017-11-10 18:10:00")));
        values2.add(new TimeData(4, "Physics", R.color.color_table_5_light, getMillis("2017-11-10 20:00:00"), getMillis("2017-11-10 21:30:00")));
        values2.add(new TimeData(5, "Chemistry", R.color.color_table_6_light, getMillis("2017-11-10 21:31:00"), getMillis("2017-11-10 22:45:00")));
        values2.add(new TimeData(6, "Biology", R.color.color_table_7_light, getMillis("2017-11-10 23:00:00"), getMillis("2017-11-11 02:30:00")));

        ArrayList<TimeTableData> tables = new ArrayList<>();
        tables.add(new TimeTableData("Sun", values));
        tables.add(new TimeTableData("Mon", values2));
        tables.add(new TimeTableData("Tue", values));
        tables.add(new TimeTableData("Wed", values2));
        tables.add(new TimeTableData("Thu", values));
        tables.add(new TimeTableData("Fri", values2));
        tables.add(new TimeTableData("Sat", values));

        mShortSamples.addAll(tables);
    }

    private void toogleMode() {
        btnMode.setActivated(!btnMode.isActivated());
    }

    // =============================================================================
    // Date format
    // =============================================================================

    private long getMillis(String day){
        DateTime date = getDateTimePattern().parseDateTime(day);
        return date.getMillis();
    }

    private DateTimeFormatter getDateTimePattern(){
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    }

    // =============================================================================
    // Toast
    // =============================================================================

    private void showToast(Activity activity, String msg){
        Toast toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }
}
