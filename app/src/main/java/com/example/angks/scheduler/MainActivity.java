package com.example.angks.scheduler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends Activity implements View.OnClickListener{
    public static final String myAction="com.angks.schedule.android.ACTION";
    public DBManager myDB;
    LinearLayout mainBody;
    MyTime startTime;
    MyTime endTime;
    Spinner quickMenu;
    long milisecondDay;
    OneDay []oneDays=new OneDay[43];
    int[] oneDayIdList = new int[43]; //하루 레이아웃
    int[] weekLayoutID = new int[7];
    TextView addMenuText[] = new TextView[3];
    int clickCnt;
    View addMenuList[];
    addMenuMoveListener moveUpAnimListenerList[] = new addMenuMoveListener[6];
    RelativeLayout contentBody;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public BroadcastReceiver Scheduler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ToDo data =intent.getParcelableExtra("ToDo");
            myDB.insertJob(data);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(Scheduler, new IntentFilter("com.angks.schedule.android.ACTION"));
        contentBody = (RelativeLayout) findViewById(R.id.contentBody);
        makeHead();
        makeBody();
        initBody();
        addMenuSetting();
        touchEventAttacher();
        myDB=new DBManager(getApplicationContext(),"Scheduler",null,1);
    }

    public void makeHead() {
        /*
    * makeHead()
    * 퀵메뉴
    * startTime,endTime
    * month text setting
    * make day to milisecond
     */
        Calendar calendar = Calendar.getInstance();
        String thisMonth =  calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"+"01";
        startTime = new MyTime(thisMonth);
        thisMonth = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"+ calendar.getActualMaximum(Calendar.DATE);
        endTime = new MyTime(thisMonth);
        TextView month = (TextView) findViewById(R.id.mainMonth);
        month.setText("" + startTime.getMonth() + "월");
        //spinner setting
        quickMenu = (Spinner) findViewById(R.id.mainQuickMenu);
        ArrayList<String> spinnerList = new ArrayList<String>();
        spinnerList.add("퀵메뉴1");
        spinnerList.add("퀵메뉴2");
        spinnerList.add("퀵메뉴3");
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        quickMenu.setAdapter(adp);
        milisecondDay = 24 * 60 * 60;
    }

    public void makeBody() {
        /*
        * make grid layout
        * attach id to Date, Job, Memorial, Oneweek
        * */
        int idCnt = 0;
        int weekIdCnt = 0;
        LinearLayout oneWeek;
        mainBody = (LinearLayout) findViewById(R.id.mainBody);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        LinearLayout oneDayLayout;
        for (int i = 0; i < 6; i++) {
            oneWeek = new LinearLayout(this);
            oneWeek.setLayoutParams(lp);
            oneWeek.setOrientation(LinearLayout.HORIZONTAL);
            weekLayoutID[weekIdCnt] = generateViewId();
            weekIdCnt++;
            mainBody.addView(oneWeek);
            for (int j = 0; j < 7; j++) {
                oneDayLayout = (LinearLayout) inflater.inflate(R.layout.one_day, null);
                oneWeek.addView(oneDayLayout);
                LinearLayout.LayoutParams tempLp = (LinearLayout.LayoutParams) oneDayLayout.getLayoutParams();
                tempLp.weight = 1;
                oneDays[idCnt]=new OneDay(2,5);
                oneDayIdList[idCnt] = generateViewId();
                oneDayLayout.setId(oneDayIdList[idCnt]);
                oneDays[idCnt].dateText=((TextView) ((LinearLayout) oneDayLayout.getChildAt(0)).getChildAt(0));
                oneDays[idCnt].jobListLayout=((LinearLayout) (LinearLayout) oneDayLayout.getChildAt(1));
                oneDays[idCnt].memroialStickerLayout=((LinearLayout) ((LinearLayout) oneDayLayout.getChildAt(0)).getChildAt(1));
                //date and memorial wrapper
                ((LinearLayout) oneDayLayout.getChildAt(0)).setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 3f));
                ((TextView) ((LinearLayout) oneDayLayout.getChildAt(0)).getChildAt(0)).setLayoutParams(new TableLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
                ((LinearLayout) ((LinearLayout) oneDayLayout.getChildAt(0)).getChildAt(1)).setLayoutParams(new TableLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3f));
                //job wrapper
                ((LinearLayout) oneDayLayout.getChildAt(1)).setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 5f));
                idCnt++;
            }
        }
    }

    public void addMenuSetting() {
        //setting of add menu
        addMenuList = new View[4];
        addMenuList[0] = (View) findViewById(R.id.addMenu);
        addMenuList[0].setTag(0);
        addMenuList[1] = (View) findViewById(R.id.addMemorial);
        addMenuList[1].setTag(1);
        addMenuList[2] = (View) findViewById(R.id.addJob);
        addMenuList[3] = (View) findViewById(R.id.addTimeTable);
        for (int i = 1; i < 4; i++) {
            moveUpAnimListenerList[i - 1] = new addMenuMoveListener(addMenuList[i], i, true);
            moveUpAnimListenerList[i - 1].setMain(this);
            moveUpAnimListenerList[i - 1].contentBody = contentBody;
            moveUpAnimListenerList[i - 1].menu = addMenuList[0];
        }
        for (int i = 4; i < 7; i++) {
            moveUpAnimListenerList[i - 1] = new addMenuMoveListener(addMenuList[i - 3], i - 3, false);
            moveUpAnimListenerList[i - 1].setMain(this);
            moveUpAnimListenerList[i - 1].contentBody = contentBody;
            moveUpAnimListenerList[i - 1].menu = addMenuList[0];
        }
    }

    public void initBody() {
        /*
        * setting date
        * */
        int startNumberOfOneday = startTime.calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int lastDate = endTime.getDay();
        for (int i = 0; i < lastDate; i++) {
            oneDays[startNumberOfOneday+i].dateText.setText("" + (i + 1));
        }
    }

    public void bodyClear() {
        for (int i = 0; i < oneDayIdList.length - 1; i++) {
            oneDays[i].dateText.setText("");
        }
    }

    public void addMenuClick(View v) {
        clickCnt = (int) v.getTag();
        Animation moveUpMenu[] = new Animation[3];
        if (clickCnt % 2 == 0) {
            moveUpMenu[0] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_addcontent);
            moveUpMenu[1] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_addcontent2);
            moveUpMenu[2] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_addcontent3);
            for (int i = 0; i < 3; i++) {
                moveUpMenu[i].setAnimationListener(moveUpAnimListenerList[i]);
            }
        } else {
            moveUpMenu[0] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_backcontent);
            moveUpMenu[1] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_backcontent2);
            moveUpMenu[2] = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.move_backcontent3);
            for (int i = 0; i < 3; i++) {
                moveUpMenu[i].setAnimationListener(moveUpAnimListenerList[i + 3]);
            }
        }
        for (int i = 1; i < 4; i++) {
            addMenuList[i].setVisibility(View.VISIBLE);
            addMenuList[i].startAnimation(moveUpMenu[i - 1]);
        }
        clickCnt = clickCnt % 2 + 1;
        v.setTag(clickCnt);
    }

    public void touchEventAttacher() {
        /*
        *  attach touchEvent to relativeLayout - contnent body;
        *  left - right : previous month
        *  right - left : next month
        * */
        contentBody.setOnTouchListener(new View.OnTouchListener() {
            float firstTouchX;
            float firstTouchY;
            float afterTouchX;
            float afterTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    firstTouchX = event.getX();
                    firstTouchY = event.getY();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    afterTouchX = event.getX();
                    afterTouchY = event.getY();
                    if (Math.abs(firstTouchX - afterTouchX) > Math.abs(firstTouchY - afterTouchY)) {
                        if (firstTouchX - afterTouchX < -70) {
                            bodyClear();
                            startTime.calendar.add(Calendar.MONTH, -1);
                            startTime.calendar.set(Calendar.DATE, startTime.calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                            endTime.calendar.add(Calendar.MONTH, -1);
                            endTime.calendar.set(Calendar.DATE, startTime.calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            TextView month = (TextView) findViewById(R.id.mainMonth);
                            month.setText("" + startTime.getMonth() + "월");
                            initBody();
                        } else if (firstTouchX - afterTouchX > 70) {
                            bodyClear();
                            startTime.calendar.add(Calendar.MONTH, 1);
                            startTime.calendar.set(Calendar.DATE, startTime.calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                            endTime.calendar.add(Calendar.MONTH, 1);
                            endTime.calendar.set(Calendar.DATE, startTime.calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                            TextView month = (TextView) findViewById(R.id.mainMonth);
                            month.setText("" + startTime.getMonth() + "월");
                            initBody();
                        }
                    } else {
                        if (firstTouchY - afterTouchY < -70) {
                            Log.d("debug", "timeTable");
                        } else if (firstTouchY - afterTouchY > 70) {
                            Log.d("debug", "search");
                        }
                    }
                }
                return true;
            }
        });
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.addJob:
                intent = new Intent(this, JobInputActivity.class);
                startActivity(intent);
                break;
            case R.id.addMemorial:
                intent=new Intent(this,MemorialInputActivity.class);
                startActivity(intent);
                break;
            case R.id.addTimeTable:
                intent =new Intent(this,TimeTableInputActivity.class);
                startActivity(intent);
                break;

        }
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}

