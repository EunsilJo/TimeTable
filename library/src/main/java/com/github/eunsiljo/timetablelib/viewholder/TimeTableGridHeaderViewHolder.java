package com.github.eunsiljo.timetablelib.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.eunsiljo.timetablelib.R;

/**
 * Created by EunsilJo on 2017. 11. 22..
 */

public class TimeTableGridHeaderViewHolder extends RecyclerView.ViewHolder{

    private Context mContext;
    private View itemView;
    private TextView txtHeader;

    public TimeTableGridHeaderViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        this.itemView = itemView;

        txtHeader = (TextView)itemView.findViewById(R.id.txtHeader);
    }

    public void setTableHeader(String header) {
        txtHeader.setText(header);
    }
}