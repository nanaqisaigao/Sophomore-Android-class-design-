package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> skill_list = new ArrayList<>(Arrays.asList("Java", "C", "Python"));
    List<String> selected_list = new ArrayList<>(); //存放选中项
    public void initView() {
        listView = findViewById(R.id.lv_skill);
        adapter= new ArrayAdapter<String>(
                ListViewActivity.this,
                android.R.layout.simple_list_item_multiple_choice,
                skill_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(listener);
    }
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView lv = (ListView) parent;
            if ( ((CheckedTextView) view).isChecked() ) {
                selected_list.add(lv.getItemAtPosition(position).toString());
            } else {
                selected_list.remove(lv.getItemAtPosition(position).toString());
            }
            Log.d("flag", selected_list.toString());
            Log.d("flag", "一共选中" + lv.getCheckedItemCount() + "/" + lv.getCount());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();
    }
}

