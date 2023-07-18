package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService3 extends Service {
    MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();
    public class LocalBinder extends Binder {
        public MusicService3 getService() {
            return MusicService3.this;
        }
    }
    public void init() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.she); //创建音乐播放器
            mediaPlayer.setLooping(true); //重复播放
            mediaPlayer.seekTo(0); //从头开始
        }
    }
    public void start()
    {
        if ( !mediaPlayer.isPlaying() ) {
            mediaPlayer.start(); //播放
        }
    }
    public void pause()
    {
        if ( mediaPlayer.isPlaying() ) {
            mediaPlayer.pause(); //暂停
        }
    }
    public void stop() {
        if ( mediaPlayer != null ) {
            mediaPlayer.stop(); //停止
            mediaPlayer.release(); //回收
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("flag", "onCreate");
        init(); //初始化
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("flag", "onBind");
//获取传入的信息
        Log.d("flag", intent.getStringExtra("data"));
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("flag", "onUnbind");
        stop();
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("flag", "onDestroy");
    }
} //end service
