package com.example.broadcastsend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.back")) {
            Bundle bundle = intent.getExtras();
            String back = bundle.getString("result");
            Log.d("flag", "接收到=" + back);
        }
    }
}
