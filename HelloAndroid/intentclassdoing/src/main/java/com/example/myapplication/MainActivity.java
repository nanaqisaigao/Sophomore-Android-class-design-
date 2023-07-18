package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et_a, et_b;
    RadioGroup rg;

    public void init() {
        et_a = (EditText) findViewById(R.id.op_a);
        et_b = (EditText) findViewById(R.id.op_b);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(listener);
    }


    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton rb = (RadioButton) findViewById(checkedId); //选中项按钮
            String a = et_a.getText().toString();
            String b = et_b.getText().toString();
            String op = rb.getText().toString();
            Intent intent = new Intent(MainActivity.this, jiusan.class);
            Bundle bundle = new Bundle();
            bundle.putString("a", a);
            bundle.putString("b", b);
            bundle.putString("op", op);
            intent.putExtras(bundle);
            startActivityForResult(intent, 100);
        }};


        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100)
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String info = bundle.getString("info");
                    Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
                }
        }


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            init();
        }
    };

