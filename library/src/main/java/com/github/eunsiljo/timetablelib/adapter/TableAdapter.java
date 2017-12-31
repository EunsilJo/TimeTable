package com.github.eunsiljo.timetablelib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.view.TimeTableView;
import com.github.eunsiljo.timetablelib.viewholder.TableHeaderViewHolder;
import com.github.eunsiljo.timetablelib.viewholder.TableItemViewHolder;

/**
 * Created by EunsilJo on 2017. 11. 22..
 */

public class TableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> items = new ArrayList<String>();
    private boolean showHeader = false;

    public boolean isShowHeader() {
        return showHeader;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void add(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<String> items) {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType){
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_HEADER:
                view = inflater.inflate(R.layout.view_table_header, parent, false);
                return new TableHeaderViewHolder(view);
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_ITEM:
                view = inflater.inflate(R.layout.view_table_item, parent, false);
                return new TableItemViewHolder(view);
        }
        throw new IllegalArgumentException("invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_HEADER:
                break;
            case TimeTableView.VIEW_TYPE.VIEW_TYPE_ITEM:
                TableItemViewHolder h = (TableItemViewHolder) holder;
                if(isShowHeader()){
                    position = position - 1;
                }
                h.setTableItem(items.get(position));
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