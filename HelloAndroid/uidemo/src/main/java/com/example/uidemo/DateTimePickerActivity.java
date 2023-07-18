package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePickerActivity extends AppCompatActivity {
    EditText et_date, et_time;
    public void initView() {
        et_date = findViewById(R.id.et_date);
        et_time = findViewById(R.id.et_time);
        et_date.setOnClickListener(listener);
        et_time.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.et_date:
                    getDate(); //设置日期
                    break;
                case R.id.et_time:
                    getTime(); //设置时间
                    break;
            }
        }
    };
    public void getDate() {
        Calendar data = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(DateTimePickerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String str;
                str = String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(day);
                et_date.setText(str);
            }
        }, data.get(Calendar.YEAR), data.get(Calendar.MONTH), data.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String str;
                str = String.valueOf(hourOfDay) + ":" + String.valueOf(minute) ;
                et_time.setText(str);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        tpd.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);
        initView();
    }
}

