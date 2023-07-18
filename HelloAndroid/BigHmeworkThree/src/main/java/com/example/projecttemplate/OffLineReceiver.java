package com.example.projecttemplate;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class OffLineReceiver extends BroadcastReceiver {
//新建 BroadCastReceiver：OffLineReceiver
/*
    接收器代码逻辑
    将 xml 文件中保存的登录状态信息 loginStauts 修改为 false
    弹出提示框提醒下线消息，设置 builder.setCancelable(false)使对话框不可
    取消(不会被返回键关闭)
    通过 context 重新构造 MainActivity 同时跳转登录页
    跳转原因：recreate 方法不同于 finish+startActivity，前者会保存 fragment 的状态
    信息并在create之后恢复。所以create之后viewpager会被设置为下线按钮页的fragment，
    之后触发 viewpager 的 OnPageChangeCallback 回调，因为下线时将登录状态设置为了 false，
    所以回调中判断到未登录将 fragment 重定向为登录页
*/

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("loginStatus", false);
        editor.apply();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage("You are forced to be offline!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity)context).recreate();
            }
        });
        builder.show();
    }
}