package com.example.angks.scheduler;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by angks on 2016-03-01.
 */
public class FixedJob extends ToDo {
    public int term;
    public boolean doAlarm;
    public String where;
    public String professor;
    public String content;
    public int rotation;
    public FixedJob(MyTime start, MyTime end){
        super(start,end,FIXED_JOB);
    }
    @Override
    public void writeToParcel(Parcel dest,int flag){
        super.writeToParcel(dest,flag);
        dest.writeInt(term);
        if(doAlarm)
            dest.writeInt(1);
        else
            dest.writeInt(0);
        dest.writeString(where);
        dest.writeString(professor);
        dest.writeString(content);
        dest.writeInt(rotation);
    }

    public FixedJob(Parcel in){
        super(in);
        term=in.readInt();
        if(in.readInt()==1)
            doAlarm=true;
        else
            doAlarm=false;
        where=in.readString();
        professor=in.readString();
        content=in.readString();
        rotation=in.readInt();
    }
    public static final Parcelable.Creator<FixedJob> CREATOR=new Creator<FixedJob>() {
        @Override
        public FixedJob createFromParcel(Parcel source) {
            return new FixedJob(source);
        }

        @Override
        public FixedJob[] newArray(int size) {
            return new FixedJob[0];
        }
    };
}
