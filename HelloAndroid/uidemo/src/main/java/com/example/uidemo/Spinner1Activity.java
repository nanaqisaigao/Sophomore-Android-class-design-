package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Spinner1Activity extends AppCompatActivity {
    Spinner sp_major;
    TextView tv_major;
    List<String> list;
    ArrayAdapter<String> adapter;
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tv_major.setText("专业选择：" + parent.getSelectedItem());
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    public void initView(){
        tv_major=findViewById(R.id.tv_major);
        sp_major=findViewById(R.id.sp_major);
        sp_major.setOnItemSelectedListener(listener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner1);
        initView();
        list = new ArrayList<String>();
        list.add("软件工程");
        list.add("计算机科学");
        list.add("网络工程");
        adapter = new ArrayAdapter<String>( Spinner1Activity.this,
                android.R.layout.simple_list_item_1, list );

        // 下面这个是自定义布局：见下页
       adapter = new ArrayAdapter<String>( Spinner1Activity.this,
       R.layout.sipnner_custom, R.id.tv_item, list );
        sp_major.setAdapter(adapter);
    }
}