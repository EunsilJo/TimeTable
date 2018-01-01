package com.github.eunsiljo.timetablelib.data;

import android.support.annotation.ColorRes;

/**
 * Created by EunsilJo on 2017. 11. 21..
 */

public class TimeData<T> {
    private int colorRes = android.R.color.transparent;
    private int textColorRes = -1;
    private long startMills;
    private long stopMills;
    private String title;
    private T key;
    private boolean showError = false;

    public TimeData(T key, String title, @ColorRes int colorRes, long startMills, long stopMills) {
        this.key = key;
        this.title = title;
        this.colorRes = colorRes;
        this.startMills = startMills;
        this.stopMills = stopMills;
    }

    public TimeData(T key, String title,
                    @ColorRes int colorRes, @ColorRes int textColorRes, long startMills, long stopMills) {
        this.key = key;
        this.title = title;
        this.colorRes = colorRes;
        this.textColorRes = textColorRes;
        this.startMills = startMills;
        this.stopMills = stopMills;
    }

    public TimeData( T key, String title,
                     @ColorRes int colorRes, @ColorRes int textColorRes, long startMills, long stopMills,
                     boolean showError) {
        this.colorRes = colorRes;
        this.textColorRes = textColorRes;
        this.startMills = startMills;
        this.stopMills = stopMills;
        this.title = title;
        this.key = key;
        this.showError = showError;
    }

    public T getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public int getColorRes() {
        return colorRes;
    }

    public int getTextColorRes() {
        return textColorRes;
    }

    public long getStartMills() {
        return startMills;
    }

    public long getStopMills() {
        return stopMills;
    }

    public boolean isShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError = showError;
    }
}
