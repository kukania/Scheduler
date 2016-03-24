package com.example.angks.scheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by angks on 2016-02-22.
 */
public class MyTime {
    public Calendar calendar;
    public MyTime(String input){
        DateFormat formatter=new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date=formatter.parse(input);
            calendar= new GregorianCalendar();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            calendar=Calendar.getInstance();
        }
    }
    public MyTime(){
        calendar=Calendar.getInstance();
    }
    public MyTime(long input){calendar=Calendar.getInstance();
    calendar.setTimeInMillis(input);}
    public int getMonth(){return calendar.get(Calendar.MONTH)+1;}
    public int getYear(){return calendar.get(Calendar.YEAR);}
    public int getDay(){return calendar.get(Calendar.DATE);}
}
