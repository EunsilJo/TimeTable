package com.github.eunsiljo.timetablelib.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.adapter.TimeTableGridAdapter;
import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.utils.TableUtils;
import com.github.eunsiljo.timetablelib.view.TimeTableView;

/**
 * Created by EunsilJo on 2017. 11. 9..
 */

public class TimeTableItemViewHolder extends RecyclerView.ViewHolder{
    private static final int TIME_COLUMN_NUM = 288;
    private static final long TIME_INTERVAL_MILLS = 24 * 60 * 60 * 1000 / TIME_COLUMN_NUM;

    private Context mContext;
    private RecyclerView mListView;
    private TimeTableGridAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private boolean showHeader = false;
    private TimeTableView.TableMode tableMode = TimeTableView.TableMode.LONG;

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public TimeTableView.TableMode getTableMode() {
        return tableMode;
    }

    public void setTableMode(TimeTableView.TableMode tableMode) {
        this.tableMode = tableMode;
    }

    public interface OnTimeItemClickListener {
        void onTimeItemClick(View view, int position, TimeGridData item);
    }
    private OnTimeItemClickListener mListener;
    public void setOnTimeItemClickListener(OnTimeItemClickListener listener) {
        mListener = listener;
    }

    public TimeTableItemViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();

        mAdapter = new TimeTableGridAdapter();
        mListView = (RecyclerView)itemView.findViewById(R.id.recyclerGrid);
        mListView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(mContext);
        mListView.setLayoutManager(mLayoutManager);

        mAdapter.setOnTimeItemClickListener(new TimeTableGridItemViewHolder.OnTimeItemClickListener() {

            @Override
            public void onTimeItemClick(View view, TimeGridData item) {
                if (mListener != null) {
                    mListener.onTimeItemClick(view, getLayoutPosition(), item);
                }
            }
        });
    }

    public void setTableItem(long startMillis, long endMillis, TimeTableData timeData) {
        if(timeData != null) {
            mAdapter.setShowHeader(isShowHeader());
            mAdapter.setTableMode(getTableMode());
            mAdapter.setTableHeader(timeData.getHeader());

            mAdapter.clear();

            ArrayList<TimeData> values = TableUtils.getValidateTimeData(startMillis, endMillis,
                    timeData.getValues());
            if(values == null || values.size() == 0){
                return;
            }

            int index = 0;
            long start = values.get(index).getStartMills() / 1000 * 1000;
            long end = values.get(index).getStopMills() / 1000 * 1000;
            long time = startMillis / 1000 * 1000;

            int tCount = 0;
            int fCount = 0;

            ArrayList<TimeGridData> activates = new ArrayList<>();
            for (int i = 0; i < TIME_COLUMN_NUM; i++) {
                time += TIME_INTERVAL_MILLS;

                if (time > start && time <= end) {
                    if(fCount > 0){
                        activates.add(new TimeGridData(fCount));
                        fCount = 0;
                    }
                    tCount++;
                }else{
                    fCount++;
                }
                if (time >= end) {
                    if(tCount > 0){
                        activates.add(new TimeGridData(values.get(index), tCount));
                        tCount = 0;
                    }

                    if(index < values.size()-1) {
                        index++;
                        start = values.get(index).getStartMills() / 1000 * 1000;
                        end = values.get(index).getStopMills() / 1000 * 1000;
                    }
                }

                if(i == TIME_COLUMN_NUM - 1){
                    if(fCount > 0){
                        activates.add(new TimeGridData(fCount));
                        fCount = 0;
                    }
                    if(tCount > 0){
                        activates.add(new TimeGridData(values.get(index), tCount));
                        tCount = 0;
                    }
                }
            }
            mAdapter.addAll(activates);
        }
    }
}