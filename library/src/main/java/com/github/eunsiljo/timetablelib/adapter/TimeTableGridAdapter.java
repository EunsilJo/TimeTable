package com.github.eunsiljo.timetablelib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableGridHeaderViewHolder;
import com.github.eunsiljo.timetablelib.viewholder.TimeTableGridItemViewHolder;


/**
 * Created by EunsilJo on 2017. 11. 9..
 */

public class TimeTableGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TimeGridData> items = new ArrayList<TimeGridData>();
    private boolean showHeader = false;
    private TimeTableView.TableMode tableMode = TimeTableView.TableMode.LONG;
    private String tableHeader;

    public String getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(String tableHeader) {
        this.tableHeader = tableHeader;
    }

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

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(TimeGridData item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<TimeGridData> items) {
        int positionStart = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(positionStart, items.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isShowHeader()) {
            return TimeTableView.VIEW_TYPE.VIEW_TYPE_HEADER;
        } else {
            return TimeTableView.VIEW_TYPE.VIEW_TYPE_ITEM;
        }
    }

    private TimeTableGridItemViewHolder.OnTimeItemClickListener mListener;
    public void setOnTimeItemClickListener(TimeTableGridItemViewHolder.OnTimeItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType){
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_HEADER:
                view = inflater.inflate(R.layout.view_time_table_grid_header, parent, false);
                return new TimeTableGridHeaderViewHolder(view);
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_ITEM:
                view = inflater.inflate(R.layout.view_time_table_grid_item, parent, false);
                return new TimeTableGridItemViewHolder(view);
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_HEADER:
                TimeTableGridHeaderViewHolder header = (TimeTableGridHeaderViewHolder) holder;
                header.setTableHeader(tableHeader);
                break;
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_ITEM:
                TimeTableGridItemViewHolder h = (TimeTableGridItemViewHolder) holder;
                if(isShowHeader()){
                    position = position - 1;
                }
                h.setTableMode(getTableMode());
                h.setTableItem(items.get(position));
                h.setOnTimeItemClickListener(mListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        int count;
        if(isShowHeader()){
            count = items.size() + 1;
        }else{
            count = items.size();
        }
        return count;
    }

    public int getRealItemCount(){
        return items.size();
    }
}