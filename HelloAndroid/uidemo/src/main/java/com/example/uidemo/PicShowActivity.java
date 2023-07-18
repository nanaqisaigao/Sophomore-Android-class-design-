package com.example.uidemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PicShowActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> pic_list = new ArrayList<>();
    public void initView() {
        listView = findViewById(R.id.lv_pic);
        pic_list = getPic(); //获取assets/pic目录下所有图片名
        adapter = new ArrayAdapter<String>(
                PicShowActivity.this, android.R.layout.simple_list_item_single_choice,
                pic_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
        listView.setOnItemClickListener(listener);
    }
    public List<String> getPic() {
        List<String> pList = new ArrayList<>();
        try {
            String[] fNames = getAssets().list("pic"); //获取assets/pic目录下所有文件名
            for (String fn : fNames) {
                pList.add(fn);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pList;
    }
    // ListView监听器
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ListView lv = (ListView) parent;
            lv.setSelector(R.color.purple_200); //设置高亮背景色
            String pName = parent.getItemAtPosition(position).toString(); //获得选中项图片名称
            showPic(pName); //显示图片，见下页
        }
    };
    //显示图片
    public void showPic(String pName) {
        try {
            InputStream is = getAssets().open("pic/" + pName); // 打开assets/pic下指定的文件（输入流方式）
            Bitmap bitmap = BitmapFactory.decodeStream(is); // 将输入流转为Bitmap类型
// ①创建全屏对话框窗口
            final Dialog dialog = new Dialog(PicShowActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen); // 系统全屏样式
// ②创建一个ImageView组件，该组件显示bitmap内容
            ImageView imgView = getView(bitmap); // getView方法在下页
            dialog.setContentView(imgView); // dialog窗口加载imgView作为界面内容
            dialog.show(); //显示对话框窗口
// ③点击图片关闭对话框
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss(); //关闭对话框
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // 创建一个ImageView对象
    private ImageView getView(Bitmap bitmap) {
        ImageView iv = new ImageView( PicShowActivity.this ); //新建对象
//ImageView设置宽高为与屏幕匹配
        iv.setLayoutParams(new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT));
//加载bitmap资源
        iv.setImageBitmap(bitmap);
        return iv;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_show);
        initView();
// 设置为全屏模式（隐藏状态条）
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//设置标题和返回按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("图片查看示例");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}