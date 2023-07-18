package com.example.helloandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MyActivity extends AppCompatActivity {

    ConstraintLayout layout;

    int[] resIds=new int[]{R.drawable.mm1,R.drawable.mm2};

    int idx = 0;
    Timer timer = new Timer();//定时器
    TimerTask task = new TimerTask() { //定时任务--子线程（TimerTask是一个抽象类）
        public void run() {
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    layout.setBackgroundResource(resIds[idx]);
                    if(idx<resIds.length-1){
                        idx++;
                    }else{
                        idx = 0;
                           }
                                    }
            });
                            }
                                        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        layout = findViewById(R.id.layout);

        ActionBar actionBar = getSupportActionBar(); //获取ActionBar
        actionBar.setTitle("欢迎使用"); //设置标题
        actionBar.setDisplayHomeAsUpEnabled(true);
       //设置logo (3行代码)
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo( R.drawable.twitter );
        actionBar.setDisplayUseLogoEnabled(true);


    }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate( R.menu.main_menu, menu );
    MenuItem item=menu.findItem(R.id.menu_search);
    SearchView searchView=(SearchView) item.getActionView();

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
//            Toast.makeText(MyActivity.this, "你搜你？呢", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction( Intent.ACTION_VIEW );
            intent.setData( Uri.parse("https://www.kugeci.com/song/oioa7F1G"+query) );
            startActivity( intent );
            return false;
        }
        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });

    return super.onCreateOptionsMenu(menu);
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.menu_login:
                timer.schedule(task,50,5000);
                Toast.makeText( MyActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_exit:
                timer.cancel();
                Toast.makeText(MyActivity.this,"中止成功！",Toast.LENGTH_LONG).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}