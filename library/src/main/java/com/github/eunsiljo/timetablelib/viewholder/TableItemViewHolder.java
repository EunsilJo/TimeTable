package com.github.eunsiljo.timetablelib.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.eunsiljo.timetablelib.R;

/**
 * Created by EunsilJo on 2017. 11. 22..
 */

public class TableItemViewHolder extends RecyclerView.ViewHolder{

    private Context mContext;
    private View itemView;
    private TextView txtTime;

    public TableItemViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        this.itemView = itemView;

        txtTime = (TextView)itemView.findViewById(R.id.txtTime);
    }

    public void setTableItem(String time) {
        txtTime.setText(time);
    }
}