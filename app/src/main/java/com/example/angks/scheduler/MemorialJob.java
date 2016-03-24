package com.example.angks.scheduler;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by angks on 2016-03-01.
 */
public class MemorialJob extends ToDo implements Parcelable {
    public int illustration;
    public boolean dDayCheck;
    public Person[] list;
    public String content;
    public MemorialJob(MyTime start, MyTime end){
        super(start,end,MEMORIAL_JOB);
        dDayCheck=false;
        illustration=0;
    }

    protected MemorialJob(Parcel in) {
        super(in);
        illustration = in.readInt();
        list = in.createTypedArray(Person.CREATOR);
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(illustration);
        dest.writeTypedArray(list, flags);
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemorialJob> CREATOR = new Creator<MemorialJob>() {
        @Override
        public MemorialJob createFromParcel(Parcel in) {
            return new MemorialJob(in);
        }

        @Override
        public MemorialJob[] newArray(int size) {
            return new MemorialJob[size];
        }
    };
}
