package com.example.angks.scheduler;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by angks on 2016-03-17.
 */
public class OneDay {
    public MemorialJob memorialJob[];
    public Job job[];
    public TextView dateText;
    public LinearLayout memroialStickerLayout;
    public LinearLayout jobListLayout;
    OneDay(int memorialJobSize,int jobSize){
        memorialJob=new MemorialJob[memorialJobSize];
        job=new Job[jobSize];
    }
}
