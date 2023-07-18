package com.example.uidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {
    EditText phone;
    EditText password;
    EditText info;
    Button btn;
    String s_phone;
    String s_password;
    String s_info;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            s_phone = phone.getText().toString(); //获取输入的值
            s_password = password.getText().toString();
            s_info = info.getText().toString();
            new AlertDialog.Builder(EditTextActivity.this)
                    .setTitle("输入的数据：")
                    .setMessage("电话:" + s_phone + "\n密码:" + s_password + "\n个人简介:\n" + s_info)
                    .setNegativeButton("取消", null)
                    .show();
        }
    };
    public void initView() {
        phone = findViewById(R.id.et_phone);
        password = findViewById(R.id.et_password);
        info = findViewById(R.id.et_multitext);
        btn = findViewById(R.id.btn_getdata);
        btn.setOnClickListener(listener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        initView();
        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        actionBar.setTitle("TextView示例"); //设置标题
        actionBar.setDisplayHomeAsUpEnabled(true); //在ActionBar最左边显示返回上一层箭头按钮
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}