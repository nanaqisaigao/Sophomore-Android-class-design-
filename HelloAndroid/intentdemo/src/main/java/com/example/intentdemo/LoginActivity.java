package com.example.intentdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity {
    Button btn;
    public void init(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.login_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_phone = ((EditText)findViewById(R.id.phone)).getText().toString();
                String s_password = ((EditText)findViewById(R.id.password)).getText().toString();
                // 准备数据 (bundle)
                Intent intent = new Intent( LoginActivity.this, WelcomeActivity.class );
                Bundle bundle = new Bundle();
                bundle.putString( "phone" , s_phone);
                bundle.putString( "password" , s_password);
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String info = bundle.getString("info");
                Snackbar snackbar = Snackbar.make( findViewById(R.id.login_btn), "回传信息=" + info,
                        Snackbar.LENGTH_INDEFINITE );
                snackbar.setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss(); //手工清除Snackbar
                    }
                });
                snackbar.show();
            }
    }

}