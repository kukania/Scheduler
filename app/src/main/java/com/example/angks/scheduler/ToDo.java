package com.example.angks.scheduler;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;

/**
 * Created by angks on 2016-03-01.
 */
public class ToDo implements Parcelable{
    public MyTime startTime;
    public MyTime endTime;
    public String title;
    public int importantPoint;
    public boolean pushAlarm;
    public int hour;
    public int minute;
    public short typeNum;
    public int color;
    public static final short MEMORIAL_JOB=1;
    public static final short JOB=2;
    public static final short FIXED_JOB=3;
    public ToDo(MyTime start, MyTime end, short typeNum) {
        startTime = start;
        endTime = end;
        this.typeNum = typeNum;
    }
    public ToDo(Parcel in){
        this.startTime=new MyTime(in.readLong());
        this.endTime=new MyTime(in.readLong());
        this.title=in.readString();
        this.importantPoint=in.readInt();
        if(in.readInt()==1)
            this.pushAlarm=true;
        else
            this.pushAlarm=false;
        this.hour=in.readInt();
        this.minute=in.readInt();
        this.typeNum=(short)in.readInt();
        this.color=in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(startTime.calendar.getTimeInMillis());
        dest.writeLong(endTime.calendar.getTimeInMillis());
        dest.writeString(title);
        dest.writeInt(importantPoint);
        if(pushAlarm){
            dest.writeInt(1);
        }else
            dest.writeInt(1);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeInt(typeNum);
        dest.writeInt(color);
    }
    public static final Parcelable.Creator<ToDo> CREATOR = new Creator<ToDo>() {
        @Override
        public ToDo createFromParcel(Parcel source) {
            return new ToDo(source);
        }

        @Override
        public ToDo[] newArray(int size) {
            return new ToDo[0];
        }
    };
}
