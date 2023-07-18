package com.example.projecttemplate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.List;

/*短信发送后台服务：
        新建 Service SmsService
        解析出 intent 携带的电话号码和信息内容
        通过 SmsManager 发送短信*/


public class SmsService extends Service {
    public SmsService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        String phone = bundle.getString("phone");
        String msg = bundle.getString("msg");
        SmsManager smsManager = getSystemService(SmsManager.class);
        List<String> list = smsManager.divideMessage(msg);
        for (String m : list) {
            smsManager.sendTextMessage(phone, null, m, null, null);
            /*
        第一个参数：电话号码
        第二个参数：运营商，传入null就行，系统会自动调用
        第三个参数，短信的内容
        第四个，第五个参数：短信发送状态的广播，这里不用广播，传入null
         */
        }

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}