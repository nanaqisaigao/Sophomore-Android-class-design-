package com.example.uidemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextViewActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("TextView示例"); //设置标题
        actionBar.setDisplayHomeAsUpEnabled(true); //返回箭头按钮
        tv=findViewById(R.id.tv5);
        btn=findViewById(R.id.btn_change);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("武汉科技大学"); //设置文本内容
                tv.setTextSize(22); //设置文字大小，默认单位是sp
                tv.setTextColor(Color.BLUE); //设置文本颜色
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: /*返回按钮id*/
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}