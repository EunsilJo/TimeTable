package com.github.eunsiljo.timetablelib.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.eunsiljo.timetablelib.R;
import com.github.eunsiljo.timetablelib.data.TimeData;
import com.github.eunsiljo.timetablelib.data.TimeGridData;
import com.github.eunsiljo.timetablelib.utils.TableUtils;
import com.github.eunsiljo.timetablelib.view.TimeTableView;


/**
 * Created by EunsilJo on 2017. 11. 9..
 */

public class TimeTableGridItemViewHolder extends RecyclerView.ViewHolder{

    private Context mContext;
    private View itemView;
    private TimeGridData mTimeGridData;

    private TextView txtTime;
    private TextView txtPlan;
    private ImageView imgError;

    private Animation mAnimation;

    private TimeTableView.TableMode tableMode = TimeTableView.TableMode.LONG;

    public TimeTableView.TableMode getTableMode() {
        return tableMode;
    }

    public void setTableMode(TimeTableView.TableMode tableMode) {
        this.tableMode = tableMode;
    }

    public interface OnTimeItemClickListener {
        void onTimeItemClick(View view, TimeGridData item);
    }
    private OnTimeItemClickListener mListener;
    public void setOnTimeItemClickListener(OnTimeItemClickListener listener) {
        mListener = listener;
    }

    public TimeTableGridItemViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        this.itemView = itemView;

        txtTime = (TextView)itemView.findViewById(R.id.txtTime);
        txtPlan = (TextView)itemView.findViewById(R.id.txtPlan);
        imgError = (ImageView)itemView.findViewById(R.id.imgError);

        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out_anim);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if(mTimeGridData != null && mTimeGridData.getTime() != null) {
                        mListener.onTimeItemClick(v, mTimeGridData);
                    }
                }
            }
        });
    }

    public void setTableItem(TimeGridData activate) {
        if(activate != null) {
            mTimeGridData = activate;

            TimeData time = activate.getTime();
            int colorRes = android.R.color.transparent;

            if(time != null) {
                colorRes = time.getColorRes();
                int textColor = time.getTextColorRes();
                if(textColor != -1) {
                    textColor = mContext.getResources().getColor(textColor);
                }else{
                    textColor = mContext.getResources().getColor(R.color.black);
                }
                txtTime.setTextColor(textColor);
                txtPlan.setTextColor(textColor);

                txtTime.setText(TableUtils.formatTime(mContext, time.getStartMills()));
                txtPlan.setText(time.getTitle());

                if(getTableMode() == TimeTableView.TableMode.LONG){
                    txtTime.setVisibility(View.VISIBLE);
                    txtPlan.setVisibility(View.VISIBLE);
                }else{
                    txtTime.setVisibility(View.GONE);
                    txtPlan.setVisibility(View.VISIBLE);
                }

                if(time.isShowError()){
                    imgError.setVisibility(View.VISIBLE);
                    imgError.startAnimation(mAnimation);
                }else{
                    imgError.setVisibility(View.GONE);
                    imgError.clearAnimation();
                }
            }else{
                txtTime.setVisibility(View.GONE);
                txtPlan.setVisibility(View.GONE);
                imgError.setVisibility(View.GONE);
            }

            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            lp.height = mContext.getResources().getDimensionPixelSize(R.dimen.time_table_item_height) * activate.getRowCount();
            itemView.setLayoutParams(lp);
            itemView.setBackgroundColor(colorRes);
        }
    }
}