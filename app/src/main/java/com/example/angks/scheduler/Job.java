package com.example.angks.scheduler;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by angks on 2016-03-01.
 */
public class Job extends ToDo {
    public boolean dDayCheck;
    public String location;
    public String content;
    public String with;
    int alarmCustomSetting;

    public Job(MyTime start, MyTime end) {
        super(start, end, JOB);
    }
    public static final Parcelable.Creator<Job> CREATOR=new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[0];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flag) {
        super.writeToParcel(dest, flag);
        if (dDayCheck)
            dest.writeInt(1);
        else
            dest.writeInt(0);
        dest.writeString(location);
        dest.writeString(content);
        dest.writeString(with);
        dest.writeInt(alarmCustomSetting);
    }

    public Job(Parcel in) {
        super(in);
        if (in.readInt() == 1)
            dDayCheck = true;
        else
            dDayCheck = false;
        location=in.readString();
        content=in.readString();
        with=in.readString();
        alarmCustomSetting=in.readInt();
    }
}
