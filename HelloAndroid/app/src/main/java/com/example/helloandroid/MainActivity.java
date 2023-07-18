package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
//上面和下面都是为了让函数更有封装性
    public void Init(){
     btn1= (Button)findViewById(R.id.button);
     btn2= (Button)findViewById(R.id.button2);
     btn3= (Button)findViewById(R.id.button3);
     btn4= (Button)findViewById(R.id.button4);
     btn5= (Button)findViewById(R.id.button5);
}

public void alert(){
          new AlertDialog.Builder( MainActivity.this )
                .setIcon(R.drawable.alert)
                        .setTitle("提示")
                        .setMessage("确定退出猪圈吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
                .setNegativeButton("取消", null)
                        .show();
    }
class BtnClickListener implements View.OnClickListener{
    @Override
        public void onClick(View v){
        switch(v.getId()) {
            case R.id.button:
                tv = (TextView) findViewById(R.id.msg);
                tv.setText("我叫胡婷");
                Toast.makeText(MainActivity.this, "已进入猪圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                tv = (TextView) findViewById(R.id.msg);
                tv.setText("不要摸我");
                break;
            case R.id.button3://Snackbar的应用
                Snackbar.make( findViewById( R.id.button3 ), "我生气了，滚", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish(); //结束当前Activity
                            }
                        }).show();
            case R.id.button4://AlertDialog的应用
                new AlertDialog.Builder( MainActivity.this )
                        .setIcon(R.drawable.alert)
                        .setTitle("提示")
                        .setMessage("确定退出猪圈吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                   finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            case R.id.button5:
                tv = (TextView) findViewById(R.id.msg);
                tv.setText(" ");
                break;
        }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnClickListener listener=new BtnClickListener();
        Init();
        btn1.setOnClickListener(listener);

        btn2.setOnClickListener(listener);

        btn3.setOnClickListener(listener);

        btn4.setOnClickListener(listener);

        btn5.setOnClickListener(listener);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode==event.KEYCODE_BACK){
        alert();
    }
        return super.onKeyDown(keyCode, event);
    }
}