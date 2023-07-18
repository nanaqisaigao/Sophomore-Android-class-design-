package com.example.broadcastdemotrue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if ( intent.getAction().equals( Intent.ACTION_PACKAGE_REMOVED ) ) {
            MediaPlayer.create( context, R.raw.delete ).start();
            String packageName = intent.getDataString(); //获得要删除的app包名
            Log.d("flag", "卸载了:" + packageName);
        }
        if ( intent.getAction().equals( Intent.ACTION_PACKAGE_ADDED ) ) {
            MediaPlayer.create( context, R.raw.success ).start();
            String packageName = intent.getDataString(); //获得要安装的app包名
            Log.d("flag", "安装了:"+packageName);
        }

        if ( intent.getAction().equals( Intent.ACTION_SCREEN_OFF ) ) {
            MediaPlayer.create( context, R.raw.delete ).start();
            Log.d("flag", "屏幕关闭");
        }
        if ( intent.getAction().equals( Intent.ACTION_SCREEN_ON ) ) {
            MediaPlayer.create(context, R.raw.success).start();
            Log.d("flag", "屏幕开启");
        }

       if( intent.getAction().equals("com.example.restart") ){
                Intent it = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                context.startActivity(it);
       }


       if ( intent.getAction().equals( "com.example.mybroadcast" ) ) {
//获取数据
            Bundle bundle = intent.getExtras();
            String aa = bundle.getString("a");
            String bb = bundle.getString("b");
            String op = bundle.getString("op");
//业务处理
            double a = Double.parseDouble(aa);
            double b = Double.parseDouble(bb);
            double result = 0;
            switch (op) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    result = a / b;
                    break;
            }
            Log.d("flag", aa + op + bb + "=" + result); //或者使用广播回传数据

            Intent intent1 = new Intent();
            intent.setAction("com.example.back");
            Bundle bundle1 = new Bundle();
            bundle1.putString("result",result+"="+aa + op + bb  );
            intent1.putExtras(bundle1);
            context.sendBroadcast(intent1);
            Log.d("flag", "广播回传");
        }


    }
}
