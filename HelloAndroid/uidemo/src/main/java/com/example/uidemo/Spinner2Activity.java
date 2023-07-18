package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spinner2Activity extends AppCompatActivity {
    Spinner sp_province;
    Spinner sp_city;
    List<String> provinceList = new ArrayList<String>( Arrays.asList("选择省","湖北省","湖南省") );
    List<String> cityList = new ArrayList<String>( Arrays.asList("选择市") );
    ArrayAdapter<String> adapter;
    public void setAdapter( Spinner spinner, List<String> list ) { //封装一下
        adapter = new ArrayAdapter<String>( Spinner2Activity.this, android.R.layout.simple_list_item_1, list );
        spinner.setAdapter(adapter);
    }
    public void initView() {
        sp_province = (Spinner)findViewById(R.id.sp_province);
        sp_city = (Spinner)findViewById(R.id.sp_city);
        setAdapter(sp_province,provinceList);
        setAdapter(sp_city,cityList);
        sp_province.setOnItemSelectedListener( listener );
    }

    private AdapterView.OnItemSelectedListener listener=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if ( parent.getId() == R.id.sp_province ) { //判断是哪个spinner被选中
                String pName = parent.getSelectedItem().toString(); //省份名
                switch (pName) {
                    case "选择省":
                        cityList.clear(); //先清空，再添加
                        cityList.add("选择市");
                        break;
                    case "湖北省":
                        cityList.clear();
                        cityList.add("武汉");
                        cityList.add("宜昌");
                        break;
                    case "湖南省":
                        cityList.clear();
                        cityList.add("长沙");
                        cityList.add("湘潭");
                        break;
                }
                setAdapter(sp_city, cityList); //加载新数据
                sp_city.setOnItemSelectedListener(listener);
            } else if ( parent.getId() == R.id.sp_city ) {
                Toast.makeText(Spinner2Activity.this, "选中的是：" + sp_province.getSelectedItem() +
                        sp_city.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner2);
        initView();
    }
}