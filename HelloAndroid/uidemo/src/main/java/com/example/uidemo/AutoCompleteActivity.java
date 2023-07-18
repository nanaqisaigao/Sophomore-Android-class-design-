package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoCompleteActivity extends AppCompatActivity {
    AutoCompleteTextView auto;
    Button btn;
    List<String> data = new ArrayList<>(Arrays.asList("Apple", "AMD", "Acer", "Intel", "Microsoft"));
    ArrayAdapter<String> adapter;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String input = auto.getText().toString();
            if( data.contains(input) ){
                Toast.makeText(AutoCompleteActivity.this,"查询中...",Toast.LENGTH_SHORT).show();
            }else{
                data.add(input); // list更新不能更新adapter
                adapter.add(input); // 要用adapter的方法才能更新UI组件
                adapter.notifyDataSetChanged(); // 通知UI组件更新数据
                Toast.makeText(AutoCompleteActivity.this,"已添加",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public void initView() {
        btn = findViewById(R.id.btn_search);
        btn.setOnClickListener(listener);
        auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        adapter = new ArrayAdapter<String>(AutoCompleteActivity.this,
                android.R.layout.simple_dropdown_item_1line, data);
        auto.setThreshold(1); // 输入1个字符即弹出提示
        auto.setCompletionHint("公司名称"); // 提示文字
        auto.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        initView();
    }
}