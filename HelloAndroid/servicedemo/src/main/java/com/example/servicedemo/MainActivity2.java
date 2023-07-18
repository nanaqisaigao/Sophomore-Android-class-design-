package com.example.servicedemo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
    //多权限申请
    private final ActivityResultLauncher permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {
                    if (result.get(Manifest.permission.POST_NOTIFICATIONS) != null
                            && result.get(Manifest.permission.FOREGROUND_SERVICE) != null) {
                        if (Objects.requireNonNull(result.get(Manifest.permission.POST_NOTIFICATIONS)).equals(true)
                                && Objects.requireNonNull(result.get(Manifest.permission.FOREGROUND_SERVICE)).equals(true)) {
//权限全部获取到之后的动作
                            Log.d("flag", "获得所有相关权限");
                        } else {
//有权限没有获取到的动作
                        }
                    }
                }
            }
    );
    //在需要的时候启动权限请求 移动平台软件设计
    private void requestPermission() {
//多个权限 或直接 new String[]{…}
        String[] permissions = { Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE };
        permissionLauncher.launch(permissions); //启动权限申请
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent( MainActivity2.this, MusicService2.class );
            switch( v.getId() ){
                case R.id.btn_start:
                    startForegroundService(intent); //启动服务（外部）：初次启动会调用服务的onCreate()
                    break;
                case R.id.btn_stop:
                    stopService(intent); //停止服务（外部）：终止前会执行服务的onDestroy()
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );
        requestPermission(); //申请权限
        Button btn_start = (Button) findViewById(R.id.btn_start);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_start.setOnClickListener(listener);
        btn_stop.setOnClickListener(listener);
    }
}