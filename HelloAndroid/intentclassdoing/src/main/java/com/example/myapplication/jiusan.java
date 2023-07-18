package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class jiusan extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiusan);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String a=bundle.getString("a");
        String b=bundle.getString("b");
        String op=bundle.getString("op");

        double aa=Double.parseDouble(a);
        double bb=Double.parseDouble(b);
        double result=0;
                switch (op) {
                    case  "+":
                        result=aa+bb;
                        break;
                    case  "-":
                        result=aa-bb;
                        break;
                    case  "*":
                        result=aa*bb;
                        break;
                    case  "/":
                        result=aa/bb;
                        break;
                }
        String result0=String.valueOf(result);
        bundle.putString("info",result0);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
}