package com.github.eunsiljo.timetablelib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.data.TimeTableData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableItemViewHolder;


/**
 * Created by EunsilJo on 2017. 11. 9..
 */

public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private long startMillis;
    private long endMillis;
    private List<TimeTableData> items = new ArrayList<TimeTableData>();
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

    public void setRange(long startMillis, long endMillis) {
        this.startMillis = startMillis;
        this.endMillis = endMillis;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(TimeTableData item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<TimeTableData> items) {
        int positionStart = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, items.size());
    }

    private TimeTableItemViewHolder.OnTimeItemClickListener mListener;
    public void setOnTimeItemClickListener(TimeTableItemViewHolder.OnTimeItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_time_table_item, parent, false);
        return new TimeTableItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeTableItemViewHolder h = (TimeTableItemViewHolder) holder;
        h.setShowHeader(isShowHeader());
        h.setTableMode(getTableMode());
        h.setTableItem(startMillis, endMillis, items.get(position));
        h.setOnTimeItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}