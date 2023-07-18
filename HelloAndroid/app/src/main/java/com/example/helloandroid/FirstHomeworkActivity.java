package com.example.helloandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class FirstHomeworkActivity extends AppCompatActivity {

    private TextView dateTextView;
    private TextView secondTextView;
    private TextView amPmTextView;
    private TextView HandMTextView;
    private TextView YMDTextView;
    private TextView WeekTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_homework);
        //创建事件源
        secondTextView = findViewById(R.id.secondTextView);
        amPmTextView = findViewById(R.id.amPmTextView);
        HandMTextView = findViewById(R.id.HandMViewText);
        YMDTextView = findViewById(R.id.YMDTextView);
        WeekTextView = findViewById(R.id.WeekTextView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 1000);
    }

    private void updateTime() {
//        在定时任务线程中再启动一个runOnUiThread 线程去更新U

//         Android规定只有主线程才能更新UI，在TimerTask线程中是不能直接更新UI的
//         但可调用runOnUiThread方法将一个Runnable子线程任务放到主线程中执行。

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Date currentDate = new Date();

//                SimpleDateFormat是Java中的一个类，它允许您将日期和时间格式化为特定的格式，并将其转换为字符串。
//                SimpleDateFormat使用模式字符串来指定要生成的格式。模式字符串包含特殊字符，
//                例如yyyy表示四位数的年份，MM表示两位数的月份，dd表示两位数的日期，hh表示两位数的小时（12小时制）
//                ，HH表示两位数的小时（24小时制），mm表示两位数的分钟，ss表示两位数的秒数，a表示上午或下午

                // 设置日期的格式，例如：2023年03月18日 星期六
                String dateFormat = "yyyy/MM/dd EEEE";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.CHINA);
                String date = simpleDateFormat.format(currentDate);
                //将年月日和周几分开
                String[] dateParts=date.split(" ");
                String YMD=dateParts[0];
                String Week=dateParts[1];
                //将年月日和星期分别设置到不同的TextView中
                YMDTextView.setText(YMD);
                WeekTextView.setText(Week);

                // 设置时间的格式，例如：下午 04:32:15
                String timeFormat = "a hh:mm:ss";
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(timeFormat, Locale.CHINA);
                String time = simpleTimeFormat.format(currentDate);

                // 将时间字符串分解为小时、分钟、秒和AM/PM值
                String[] timeParts = time.split(" ");
                String[] hourAndMinute = timeParts[1].split(":");
                String hour = hourAndMinute[0];
                String minute = hourAndMinute[1];
                String second = hourAndMinute[2];
                String amPm = timeParts[0];

                // 将小时、分钟、秒和AM/PM值分别设置到不同的TextView中
                HandMTextView.setText(hour+":"+minute);
                secondTextView.setText(second);
                amPmTextView.setText(amPm);
            }
        });
    }
}