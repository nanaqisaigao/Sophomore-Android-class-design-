package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView seekBarValue;
    ImageView imageView;
    Matrix matrix = new Matrix();
    public void scale(float sx,float sy) {
        matrix.set( imageView.getImageMatrix() ); // 获得图片的matrix
// 获得图片中心点
        int x = imageView.getDrawable().getBounds().centerX();
        int y = imageView.getDrawable().getBounds().centerY();
// sx,sy 为x轴、y轴缩放比例
//        matrix.setScale( sx, sy , x, y ); //以(x,y)为起始点进行缩放
        matrix.setRotate(sx*360,x,y);
        imageView.setImageMatrix( matrix ); //matrix送回图片框
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);


        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBarValue = (TextView) findViewById(R.id.seekbarvalue);
        imageView = findViewById(R.id.photo2);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
//再次确认将scaleType属性设置为"matrix"
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean
                    fromUser) {
                seekBarValue.setText( progress/10.0f + "×" );
                scale( progress/10.0f, progress/10.0f ); //等比缩放
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
