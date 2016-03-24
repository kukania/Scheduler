package com.example.angks.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TimeTableInputActivity extends Activity implements View.OnClickListener{
    FixedJob myFixedJob;
    toDoLayout myLayout;
    TextView timetableRotation;
    Button timetableBtnUp;
    Button timetableBtnDown;
    TextView timetableWhere;
    TextView timetableWho;
    TextView timetableContent;
    CheckBox timetableCheckAlarm;
    CheckBox timetableCheckPush;
    Button timetableBtnOk;
    Button timetableBtnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_input);
        init();
    }
    public void init(){
        myLayout=(toDoLayout)findViewById(R.id.TimeTableToDo);
        timetableRotation=(TextView)findViewById(R.id.TimeTableRotate);

        timetableBtnUp=(Button)findViewById(R.id.TimeTableUpBtn);
        timetableBtnDown=(Button)findViewById(R.id.TimeTableDownBtn);
        timetableBtnUp.setOnClickListener(UpDownPro);
        timetableBtnDown.setOnClickListener(UpDownPro);

        timetableWhere=(TextView)findViewById(R.id.TimeTableWhere);
        timetableWho=(TextView)findViewById(R.id.TimeTableWho);
        timetableContent=(TextView)findViewById(R.id.TimeTableContent);
        timetableCheckAlarm=(CheckBox)findViewById(R.id.TimeTableAlarm);
        timetableCheckPush=(CheckBox)findViewById(R.id.TimeTablePush);

        timetableBtnOk=(Button)findViewById(R.id.TimeTableOk);
        timetableBtnCancel=(Button)findViewById(R.id.TimeTableCancel);
        timetableBtnOk.setOnClickListener(this);
        timetableBtnCancel.setOnClickListener(this);
    }
    //up down btn event listener
    private View.OnClickListener UpDownPro=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int num;
            switch (v.getId()){
                case R.id.TimeTableUpBtn:
                    num=Integer.parseInt(timetableRotation.getText().toString());
                    num++;
                    timetableRotation.setText(""+num);
                    break;
                case R.id.TimeTableDownBtn:
                    num=Integer.parseInt(timetableRotation.getText().toString());
                    if(num>1)
                        num--;
                    timetableRotation.setText(""+num);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.TimeTableOk:
                myFixedJob=new FixedJob(new MyTime(myLayout.tvStartDate.getText().toString()),new MyTime(myLayout.tvEndDate.getText().toString()));
                myFixedJob.hour=myLayout.hour;
                myFixedJob.minute=myLayout.minute;
                myFixedJob.title=myLayout.tvTitle.getText().toString();
                myFixedJob.importantPoint=myLayout.importPoint.getChildCount();
                myFixedJob.term=Integer.parseInt(timetableRotation.getText().toString());
                myFixedJob.where=timetableWhere.getText().toString();
                myFixedJob.professor=timetableWho.getText().toString();
                myFixedJob.content=timetableContent.getText().toString();
                myFixedJob.doAlarm=timetableCheckAlarm.isChecked();
                myFixedJob.pushAlarm=timetableCheckPush.isChecked();
                myFixedJob.color=myLayout.color;
                Intent goRegister = new Intent(MainActivity.myAction);
                goRegister.putExtra("ToDo",myFixedJob);
                sendBroadcast(goRegister);
                finish();
                break;
            case R.id.TimeTableCancel:
                this.finish();
                break;
        }
    }
}
