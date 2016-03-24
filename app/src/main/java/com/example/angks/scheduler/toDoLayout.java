package com.example.angks.scheduler;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by angks on 2016-03-02.
 */
public class toDoLayout extends LinearLayout implements View.OnClickListener {
    public TextView tvStartDate;
    public TextView tvEndDate;
    public ImageButton btnStartDatePicker;
    public ImageButton btnEndDatePicker;
    public TextView tvColorPicker;
    public EditText tvTitle;
    public TimePicker timePicker;
    public int color;
    public Spinner importPoint;
    Calendar today;
    Calendar startDate;
    Calendar endDate;
    public int hour;
    public int minute;
    public toDoLayout(Context context) {
        super(context);
        init();
    }
    public toDoLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }
    public void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.todo_layout, this, true);
        tvStartDate = (TextView) this.findViewById(R.id.todo_startDate);
        tvEndDate = (TextView) this.findViewById(R.id.todo_endDate);
        btnStartDatePicker = (ImageButton) this.findViewById(R.id.todo_startDatePicker);
        btnEndDatePicker = (ImageButton) this.findViewById(R.id.todo_endDatePicker);
        tvColorPicker = (TextView) this.findViewById(R.id.todo_colorPicker);
        tvTitle = (EditText) this.findViewById(R.id.todo_title);
        timePicker = (TimePicker) this.findViewById(R.id.todo_timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int gminute) {
                hour=hourOfDay;
                minute=gminute;
            }
        });
        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);
        tvColorPicker.setOnClickListener(this);
        today = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        tvStartDate.setText(formatter.format(today.getTimeInMillis()));
        tvEndDate.setText(formatter.format(today.getTimeInMillis()));
        importPoint=(Spinner)this.findViewById(R.id.importantPoint);
        ArrayList<String> list=new ArrayList<String>();
        list.add("중요도");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_dropdown_item,list);
        importPoint.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        DatePickerDialog dp;
        switch (v.getId()) {
            case R.id.todo_colorPicker:
                final ColorPicker cp = new ColorPicker((Activity) this.getContext(), 0, 0, 0);
                cp.show();
                Button okColor = (Button) cp.findViewById(R.id.okColorButton);
                okColor.setText("선택!");
                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cp.dismiss();
                        color = cp.getColor();
                        tvColorPicker.setBackgroundColor(color);
                        tvColorPicker.setTextColor(getContrastColor(color));
                    }
                });
                break;
            case R.id.todo_startDatePicker:
                dp = new DatePickerDialog(this.getContext(), listenerS, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                dp.show();
                break;
            case R.id.todo_endDatePicker:
                dp = new DatePickerDialog(this.getContext(), listenerE, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
                dp.show();
                break;
        }
    }

    public int getContrastColor(int tempcolor) {
        double y = (299 * Color.red(tempcolor) + 587 * Color.green(tempcolor) + 114 * Color.blue(tempcolor)) / 1000;
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }
    public DatePickerDialog.OnDateSetListener listenerS = new DatePickerDialog.OnDateSetListener(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startDate = Calendar.getInstance();
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, monthOfYear);
            startDate.set(Calendar.DATE, dayOfMonth);
            tvStartDate.setText(formatter.format(startDate.getTimeInMillis()));
        }
    };
    public DatePickerDialog.OnDateSetListener listenerE = new DatePickerDialog.OnDateSetListener() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endDate = Calendar.getInstance();
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, monthOfYear);
            endDate.set(Calendar.DATE, dayOfMonth);
            tvEndDate.setText(formatter.format(endDate.getTimeInMillis()));
        }
    };
}
