package com.github.eunsiljo.timetablelib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.adapter.TableAdapter;
import com.github.eunsiljo.timetablelib.adapter.TimeTableAdapter;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.utils.TableUtils;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;

/**
 * Created by EunsilJo on 2017. 11. 21..
 */

public class TimeTableView extends LinearLayout {

    public static class VIEW_TYPE {
        public static final int VIEW_TYPE_HEADER = 0;
        public static final int VIEW_TYPE_ITEM = 1;
    }

    private RecyclerView recyclerTable;
    private TableAdapter mTableAdapter;
    private LinearLayoutManager mTableLayoutManager;

    private RecyclerView recyclerTimeTable;
    private TimeTableAdapter mTimeTableAdapter;
    private GridLayoutManager mTimeTableLayoutManager;

    public enum TableMode {
        SHORT,
        LONG
    }

    private int mStartHour = 0;
    private boolean mShowHeader = false;
    protected TableMode mTableMode = TableMode.LONG;

    public TimeTableView(Context context) {
        super(context);
        init();
    }

    public TimeTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_time_table, this);

        recyclerTable = (RecyclerView)findViewById(R.id.recyclerTable);
        mTableAdapter = new TableAdapter();
        mTableLayoutManager = new LinearLayoutManager(getContext());
        recyclerTable.setAdapter(mTableAdapter);
        recyclerTable.setLayoutManager(mTableLayoutManager);

        recyclerTimeTable = (RecyclerView)findViewById(R.id.recyclerTimeTable);
        mTimeTableAdapter = new TimeTableAdapter();
        mTimeTableLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerTimeTable.setAdapter(mTimeTableAdapter);
        recyclerTimeTable.setLayoutManager(mTimeTableLayoutManager);

        recyclerTimeTable.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                recyclerTable.scrollBy(dx, dy);
            }
        });
    }

    public void setTimeTable(long date, ArrayList<TimeTableData> times){
        mTimeTableAdapter.setShowHeader(mShowHeader);
        mTimeTableAdapter.setTableMode(mTableMode);
        mTimeTableAdapter.clear();
        if(times != null && times.size() != 0) {
            mTimeTableLayoutManager.setSpanCount(times.size());

            DateTime start = new DateTime(date).withHourOfDay(mStartHour);
            long startMillis = start.getMillis();
            long endMillis = start.plusHours(24).getMillis();

            mTimeTableAdapter.setRange(startMillis, endMillis);
            mTimeTableAdapter.addAll(times);
        }

        setTable();
    }

    private void setTable(){
        mTableAdapter.setShowHeader(mShowHeader);
        mTableAdapter.clear();
        ArrayList<String> tables = new ArrayList<>();
        for(int i=0; i<24; i++){
            int hour = i + mStartHour;
            if(hour > 24){
                hour -= 24;
            }
            String time;
            if(mTableMode == TableMode.LONG){
                time = TableUtils.formatTime(getContext(), hour * 3600);
            }else{
                time = String.format(getContext().getString(R.string.time_table_format_time_h), hour);
            }
            tables.add(time);
        }
        mTableAdapter.addAll(tables);

        int left;
        if(mTableMode == TableMode.LONG){
            left = 0;
        }else{
            left = getContext().getResources().getDimensionPixelSize(R.dimen.common_margin);
        }
        recyclerTimeTable.setPadding(left, 0, left, 0);
    }

    public void setStartHour(int start){
        if(start < 0 || start > 24){
            start = 0;
        }
        mStartHour = start;
    }

    public void setShowHeader(boolean showHeader) {
        this.mShowHeader = showHeader;
    }

    public void setTableMode(TableMode tableMode) {
        this.mTableMode = tableMode;
    }

    public void setOnTimeItemClickListener(TimeTableItemViewHolder.OnTimeItemClickListener listener) {
        mTimeTableAdapter.setOnTimeItemClickListener(listener);
    }
}
