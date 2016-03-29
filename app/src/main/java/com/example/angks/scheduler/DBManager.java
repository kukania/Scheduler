package com.example.angks.scheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

/**
 * Created by angks on 2016-03-18.
 */
public class DBManager extends SQLiteOpenHelper {
    public static int id;
    public SQLiteDatabase myDb;
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);

    }
    public void setID(){
        SQLiteDatabase db = getReadableDatabase();
        String query="select id from TODO order by id desc limit 0,1;";
        Cursor cursor=db.rawQuery(query,null);
        id=cursor.getInt(0);
        Log.d("SETID",id+"");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        myDb=db;
        db.execSQL("create table TODO(id integer primary key autoincrement, title varchar(32) not null, startTime varchar(32) not null, endTime varchar(32),importantPoint integer not null,pushAlarm integer, myHour integer, myMinute integer, color integer, typeNum integer);");
        Log.d("test","test");
        db.execSQL("create table JOB(jobId integer primary key autoincrement,id integer, DDayCheck integer,location varchar(32),content varchar(32),who varchar(32),alarmCustom integer)");
        db.execSQL("create table MEMORIAL(memorialId integer primary Key autoincrement, id integer, illustration integer, DDayCheck integer,content varchar(32));");
        db.execSQL("create table PERSON(id integer primary key autoincrement, name varchar(16), phone varchar(16), memorialId integer);");
        db.execSQL("create table FIXEDJOB(fixedjobId integer primary key autoincrement, id integer, location varchar(16), professor varchar(16), doAlarm integer, content varchar(32),rotation integer);");
    }
    public void insertJob(ToDo td){
        //importantPoint integer not null,pushAlarm integer, myHour integer, myMinute integer, typeNum integer);");
        SQLiteDatabase db=getWritableDatabase();
        ContentValues ct=new ContentValues();
        ct.put("title",td.title);
        ct.put("startTime",td.startTime.calendar.getTimeInMillis()+"");
        ct.put("endTime",td.endTime.calendar.getTimeInMillis()+"");
        ct.put("importantPoint",td.importantPoint);
        ct.put("pushAlarm",td.pushAlarm);
        ct.put("myHour",td.hour);
        ct.put("myMinute",td.minute);
        ct.put("color",td.color);
        ct.put("typeNum", td.typeNum);
        long get = db.insert("TODO", null, ct);

        Log.d("dodo","test");
        //db.execSQL(query);
        switch (td.typeNum){
            case ToDo.FIXED_JOB:
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
