package com.example.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    public void init() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create( getApplicationContext(), R.raw.she ); //创建播放器并加载音乐
            mediaPlayer.setLooping(true); //重复播放
        }
    }
    public void start() {
        mediaPlayer.seekTo(0); //播放器从头开始
        mediaPlayer.start(); //播放
    }
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); //停止播放
            mediaPlayer.release(); //回收
        }
    }
    public MusicService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("flag", "onCreate");
        init();
    }
    @Override
    public int onStartCommand( Intent intent, int flags, int startId ) {
        Log.d("flag", "onStartCommand");
        Log.d("flag", intent.getStringExtra("msg") );
        start();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("flag", "onDestroy");
        stop();
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("flag", "onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}