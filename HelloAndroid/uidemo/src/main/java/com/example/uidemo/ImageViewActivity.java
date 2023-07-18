package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {
    ImageView imageView;
    Button btn1, btn2;
    int idx = 0;
    int[ ] resIds = new int[ ]{ R.drawable.lstx_001, R.drawable.lstx_002,
            R.drawable.lstx_003, R.drawable.lstx_004 };
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageView.setImageResource(resIds[idx]);
            if (idx < resIds.length - 1) {
                idx++;
            } else {
                idx = 0;
            }
            handler.postDelayed(this, 2000); // 2秒后执行当前runnable
        }
    };
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_start:
                    handler.postDelayed(runnable, 2000); // 2秒后执行一次Runnable
                    break;
                case R.id.btn_cancel:
                    handler.removeCallbacks(runnable); // 关闭定时器
                    break;
            }
        }
    };
    public void initView() {
        imageView = findViewById(R.id.iv_photo);
        btn1 = findViewById(R.id.btn_start);
        btn2 = findViewById(R.id.btn_cancel);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        initView();
    }
}
