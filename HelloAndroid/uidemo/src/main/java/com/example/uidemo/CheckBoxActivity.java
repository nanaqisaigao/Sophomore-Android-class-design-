package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxActivity extends AppCompatActivity {
    private List<String> list = new ArrayList<>();
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if ( isChecked )
                list.add( buttonView.getText().toString() );
            else
                list.remove( buttonView.getText().toString() );
            Log.d("flag", list.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box);
        CheckBox cb1 = (CheckBox) findViewById(R.id.cb_music);
        CheckBox cb2 = (CheckBox) findViewById(R.id.cb_movie);
        CheckBox cb3 = (CheckBox) findViewById(R.id.cb_sport);
        cb1.setOnCheckedChangeListener(listener);
        cb2.setOnCheckedChangeListener(listener);
        cb3.setOnCheckedChangeListener(listener);
    }
}