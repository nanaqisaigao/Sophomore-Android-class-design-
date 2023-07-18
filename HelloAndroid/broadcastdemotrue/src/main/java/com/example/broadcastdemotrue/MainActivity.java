package com.example.broadcastdemotrue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    BroadcastReceiver br;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActionBar显示返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        br = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter( "com.example.mybroadcast" );
        intentFilter.addAction("com.example.restart");

        intentFilter.addAction( Intent.ACTION_SCREEN_OFF );
        intentFilter.addAction( Intent.ACTION_SCREEN_ON );

        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addDataScheme("package");

        registerReceiver(br, intentFilter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("flag", "onDestroy");
        unregisterReceiver(br);


        Intent intent = new Intent();
        intent.setAction("com.example.restart");
        PendingIntent pIntent = PendingIntent.getBroadcast( MainActivity.this, 0, intent, PendingIntent.FLAG_IMMUTABLE ); // 延迟启动广播
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE); // 创建闹钟
        manager.set( AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pIntent ); // 设定闹钟，在当前系统时间3秒后发送广播
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); //结束Activity
        }
        return super.onOptionsItemSelected(item);
    }

}
