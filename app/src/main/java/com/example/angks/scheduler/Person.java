package com.example.angks.scheduler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by angks on 2016-03-01.
 */
public class Person implements Parcelable{
    public String phone;
    public String name;
    Person(String ph, String na){
        phone=ph; name=na;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public Person(Parcel in){
        name=in.readString();
        phone=in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
    }
    public static final Parcelable.Creator<Person> CREATOR=new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
