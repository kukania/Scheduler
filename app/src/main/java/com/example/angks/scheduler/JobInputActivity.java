package com.example.angks.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;

import static com.example.angks.scheduler.MainActivity.*;

public class JobInputActivity extends Activity implements View.OnClickListener {
    toDoLayout myLayout;
    Job myJob;
    TextView who;
    TextView where;
    TextView content;
    CheckBox dDay;
    CheckBox pushAlarm;
    Spinner spinner;
    Button OK;
    Button Cancel;
    int alarmCustomSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_input);
        myLayout = (toDoLayout) findViewById(R.id.include_layout);
        init();
    }

    public void init() {
        who = (TextView) findViewById(R.id.JobInputWho);
        where = (TextView) findViewById(R.id.JobInputWhere);
        content = (TextView) findViewById(R.id.JobInputContent);
        dDay = (CheckBox) findViewById(R.id.JobInputDDay);
        pushAlarm = (CheckBox) findViewById(R.id.JobInputPush);
        spinner = (Spinner) findViewById(R.id.JobInputSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                (String[]) getResources().getStringArray(R.array.alarm));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        alarmCustomSetting=-1;
                        break;
                    case 1:
                        alarmCustomSetting=10;
                        break;
                    case 2:
                        alarmCustomSetting=20;
                        break;
                    case 3:
                        alarmCustomSetting=30;
                        break;
                    case 4:
                        alarmCustomSetting=45;
                        break;
                    case 5:
                        alarmCustomSetting=60;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(adapter);
        OK = (Button) findViewById(R.id.JobInputOK);
        Cancel = (Button) findViewById(R.id.JobInputCancel);
        OK.setOnClickListener(this);
        Cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.JobInputOK:
                myJob = new Job(new MyTime(myLayout.tvStartDate.getText().toString()), new MyTime(myLayout.tvEndDate.getText().toString()));
                myJob.location = where.getText().toString();
                myJob.content = content.getText().toString();
                myJob.dDayCheck = dDay.isChecked();
                myJob.with = who.getText().toString();
                myJob.title = myLayout.tvTitle.getText().toString();
                myJob.importantPoint = myLayout.importPoint.getChildCount();
                myJob.pushAlarm = pushAlarm.isChecked();
                myJob.hour = myLayout.hour;
                myJob.minute = myLayout.minute;
                myJob.color=myLayout.color;
                Intent goRegister = new Intent(MainActivity.myAction);
                goRegister.putExtra("ToDo",myJob);
                sendBroadcast(goRegister);
                finish();
                break;
            case R.id.JobInputCancel:
                finish();
                break;
        }
    }
}
