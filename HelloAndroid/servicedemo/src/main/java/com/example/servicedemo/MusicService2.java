package com.example.servicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService2 extends Service {
    MediaPlayer mediaPlayer;
    // 通知的相关成员定义
    private static final int NOTIFY_ID = 1; //通知的id (自拟)
    private static final String CHANNEL_ID = "123"; //通知渠道的id (自拟)
    private static final String CHANNEL_Name = "mychannel"; //通知渠道的名称(自拟)
    NotificationManager manager; //通知管理类
    Notification notification; //通知类
    NotificationChannel channel; //通知渠道
    PendingIntent pendingIntent; //延迟Intent
    Bitmap bitmap; //通知的LargeIcon
    //发送前台服务通知
    public void sendNotity() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); // 创建通知管理器
        channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH); // 创建通知渠道
        manager.createNotificationChannel(channel);
        Intent intent = new Intent(MusicService2.this, MainActivity2.class); //点击通知将返回MainActivity2
        pendingIntent = PendingIntent.getActivity(MusicService2.this, 0, intent, PendingIntent.FLAG_IMMUTABLE); // 创建PendingIntent
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gu); // 创建大图标的Bitmap
// 创建通知，注：第二个参数是Channel的id，一定要和通知渠道的id保持一致，否则不然通知不会显示
        notification = new Notification.Builder(MusicService2.this, CHANNEL_ID)
                .setContentTitle("提醒") //通知标题
                .setContentText("通知内容…") //通知内容
                .setWhen(System.currentTimeMillis()) //通知产生的时间
                .setShowWhen(true) //显示时间
                .setSmallIcon(R.drawable.twitter) //小图标
                .setLargeIcon(bitmap) //大图标Bitmap
                .setAutoCancel(true) //通知点击后自动删除（注：前台服务的通知不起作用）
                .setContentIntent(pendingIntent) //设置通知点击的Intent
                .build(); //构建通知
        startForeground(NOTIFY_ID, notification); //发送前台服务通知 不用原版通知发送：manager.notify(NOTIFY_ID,notification);
    }
    public void init() {
        if ( mediaPlayer == null ) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.she); //创建音乐播放器
            mediaPlayer.setLooping(true); //重复播放
        }
    }
    public void start() {
        mediaPlayer.seekTo(0); //从头开始
        mediaPlayer.start(); //播放
    }
    public void stop() {
        if ( mediaPlayer != null ) {
            mediaPlayer.stop(); //停止播放
            mediaPlayer.release(); //回收
        }
    }
    public MusicService2() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sendNotity(); //前台服务通知只需发送1次，所以放在onCreate中
        init(); //播放器初始化
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start(); //播放
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true); //服务结束前主动移除通知
        stop(); //停止播放
    }
    @Override
    public IBinder onBind(Intent intent) {
// TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
