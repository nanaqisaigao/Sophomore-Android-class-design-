package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class MainActivity extends AppCompatActivity {
        Button btn_start;
        Button btn_stop;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, MusicService.class );
                intent.putExtra("msg", "hello");
                switch( v.getId() ){
                    case R.id.btn_start:
                        startService(intent); //启动服务
                        break;
                    case R.id.btn_stop:
                        stopService(intent); //停止服务
                        break;
                }
            }
        };
        public void initView() {
            btn_start = (Button) findViewById(R.id.btn_start);
            btn_stop = (Button) findViewById(R.id.btn_stop);
            btn_start.setOnClickListener(listener);
            btn_stop.setOnClickListener(listener);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            initView();
        }
    }
