package com.example.broadcastsend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class ComputeActivity extends AppCompatActivity {
    EditText et_a,et_b;
    RadioButton rb1,rb2,rb3,rb4;

    BroadcastReceiver br;


    public void initView() {
        et_a = (EditText) findViewById(R.id.op_a);
        et_b = (EditText) findViewById(R.id.op_b);
        rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);
        rb1.setOnClickListener(listener);
        rb2.setOnClickListener(listener);
        rb3.setOnClickListener(listener);
        rb4.setOnClickListener(listener);

    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            boolean checked = rb.isChecked();
            if ( checked ) {
                String a = et_a.getText().toString(); //获得操作数a
                String b = et_b.getText().toString(); //获得操作数b
                String op = rb.getText().toString(); //获取运算符
                Intent intent = new Intent();
                intent.setAction("com.example.mybroadcast");
                Bundle bundle = new Bundle();
                bundle.putString("a", a);
                bundle.putString("b", b);
                bundle.putString("op", op);
                intent.putExtras(bundle);
                sendBroadcast(intent);
                Log.d("flag", "广播发送成功");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);

        initView();
        br = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter( "com.example.back" );
        registerReceiver(br, intentFilter);

}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
    }}

