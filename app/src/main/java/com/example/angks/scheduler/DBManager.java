package com.example.angks.scheduler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

/**
 * Created by angks on 2016-03-18.
 */
public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        //db테이블 구조파악
        db.execSQL("create table TODO(id integer primary key autoincrement, title varchar(32) not null, startTime varchar(32) not null, endTime varchar(32),importantPoint integer not null,pushAlarm integer, myHour integer, myMinute integer, typeNum integer);");
        db.execSQL("create table JOB(jobId integer primary key autoincrement,id integer, DDayCheck integer,location varchar(32),content varchar(32),who varchar(32),alarmCustom integer)");
        db.execSQL("create table MEMORIAL(memorialId integer primary Key autoincrement, id integer, illustration integer, DDayCheck integer,content varchar(32));");
        db.execSQL("create table PERSON(id integer primary key autoincrement, name varchar(16), phone varchar(16), memorialId integer);");
        db.execSQL("create table FIXEDJOB(fixedjobId integer primary key autoincrement, id integer, location varchar(16), professor varchar(16), doAlarm integer, content varchar(32),rotation integer);");
    }
    public void insertJob(ToDo td){
        SQLiteDatabase db=getWritableDatabase();
        String query="insert into TODO(title,startTime,endTime,importantPoint,pushAlarm,myHour,myMinute,typeNum) values('" +
                td.title+"','"+
                (td.startTime.calendar.getTimeInMillis()+"','")+
                (td.endTime.calendar.getTimeInMillis()+"',")+
                td.importantPoint+",";
        query+=td.pushAlarm?1:0+",";
        query=query+td.hour+","+td.minute+","+td.typeNum+");";
        Log.d("todo_INSERT", query);
        //db.execSQL(query);
        switch (td.typeNum){
            case ToDo.FIXED_JOB:
                FixedJob fx=(FixedJob)td;
                query="insert into FIXEDJOB(id,location,professor,doAlarm,content,rotation) values ((select id from TODO order by id desc limit 0,1),'" +
                         fx.where+"','"+
                         fx.professor+"',";
                query+=fx.doAlarm?1:0;
                query+=",'"+fx.content+"',"+fx.rotation+");";
                Log.d("FIXEDJOB INSERT",query);
                break;
            case ToDo.JOB:
                Job jb=(Job)td;
                break;
            case ToDo.MEMORIAL_JOB:
                break;
        }
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
