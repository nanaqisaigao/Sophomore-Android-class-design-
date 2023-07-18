package com.example.servicedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity3 extends AppCompatActivity {
    ImageButton btn_start;
    private MusicService3 musicService; // 要调用的服务（从服务代理中获取）
    private boolean mBound = false; // 服务连接状态
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService3.LocalBinder binder = (MusicService3.LocalBinder) iBinder; //iBinder获取服务代理
            musicService = binder.getService(); //获取代理端服务实例（实现服务绑定）
            mBound = true; //设置连接状态为true
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false; //设置连接状态为true
        }
    };
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    boolean isPlaying = musicService.mediaPlayer.isPlaying();
                    if ( !isPlaying ) {
                        musicService.start();
                        btn_start.setImageDrawable(getDrawable(android.R.drawable.ic_media_pause));
                    } else {
                        musicService.pause();
                        btn_start.setImageDrawable(getDrawable(android.R.drawable.ic_media_play));
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d("flag", "onCreate");
        btn_start = (ImageButton) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(listener);
        Intent intent = new Intent(MainActivity3.this, MusicService3.class);
        intent.putExtra("data", "hello");
        bindService( intent, connection, BIND_AUTO_CREATE);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("myflag", "onDestroy");
        if( mBound ) {
            unbindService(connection);
            mBound = false;
        }
    }
}
