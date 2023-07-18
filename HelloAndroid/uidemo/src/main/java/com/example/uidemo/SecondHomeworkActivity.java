package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SecondHomeworkActivity extends AppCompatActivity {
    //                                                 listview的组件
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> music_list = new ArrayList<>();   //给adapter用的list

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                           下面是exoplayer的组件
    SeekBar SB; //进度条
    TextView SBHint; //音乐时长提示
    Button Bbegin; //播放按钮
    Button Bstop;
    Button Bbefore;
    Button Bnext;
    ExoPlayer player; //播放器
    private Timer timer; //定时器
    private boolean prepared; //播放器是否准备好

    String mName;

    ListView lv;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //                                                listview的创建
    public void initView() {
        //ListView初始化
        listView = findViewById(R.id.LV);
        music_list = getMusic(); //获取assets/pic目录下所有音乐名
        adapter = new ArrayAdapter<String>(
                SecondHomeworkActivity.this, android.R.layout.simple_list_item_1,
                music_list);
        listView.setAdapter(adapter);
        listView.setChoiceMode( ListView.CHOICE_MODE_SINGLE );
        listView.setOnItemClickListener(listener);
        //为按钮设置监听
        Bbegin=(Button)findViewById(R.id.Bbegin);
        Bstop=(Button)findViewById(R.id.Bstop);
        Bbefore=(Button)findViewById(R.id.Bbefore);
        Bnext=(Button)findViewById(R.id.Bnext);
        Bstop.setEnabled(false);
        Bbefore.setEnabled(false);
        Bnext.setEnabled(false);
        Bbegin.setEnabled(false);
        //为进度条设置监听
        SB = (SeekBar) findViewById(R.id.SB);
        SBHint = (TextView) findViewById(R.id.SBHint);
    }

    public List<String> getMusic() {    //给music_list一个放有所有音乐文件名的list，然后给adapter
        List<String> mList = new ArrayList<>();
        try {
            String[] fNames = getAssets().list("music"); //获取assets/music目录下所有文件名
            for (String fn : fNames) {
                mList.add(fn);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mList;
    }

    // ListView监听器
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        //设置给list的监控，当点击音乐时，把音乐名提取，然后新建播放器,并进入READY状态
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          lv = (ListView) view.getParent();
            lv.setSelector(R.color.purple_200); //设置高亮背景色
            mName = parent.getItemAtPosition(position).toString(); //获得选中项音乐名称
            initExoPlayer(); //建播放器
            Bstop.setEnabled(true);
            Bbefore.setEnabled(true);
            Bnext.setEnabled(true);
            Bbegin.setEnabled(true);
        }
    };



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                                 下面为exoplayer
    public void initExoPlayer(){
        //建播放器，并设置成STATE_READY   (用prepare())
    player = new ExoPlayer.Builder(SecondHomeworkActivity.this).build();
    MediaItem mediaItem = MediaItem.fromUri( "asset:///music/"+mName);
    player.setMediaItem(mediaItem); //加载媒体资源
    player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE); //单曲循环
    player.prepare(); //会触发下面的监听器 state会变成STATE_READY
}

    Player.Listener listener0 = new Player.Listener() {//播放器状态监听器
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            if (playbackState == ExoPlayer.STATE_READY) { //播放器准备好了
                prepared = true;
                long realDurationMillis = player.getDuration();//得到音乐的总时长
                SB.setMax((int) realDurationMillis);        //设置seekbar的最大值
            }
        }
    };

    View.OnClickListener listener1 = new View.OnClickListener() {  //开始，暂停按钮
        @Override
        public void onClick(View v) {
            if ( player.isPlaying() ) {
                player.pause();
                timer.cancel(); //停止定时器
            } else {
                player.play();
                timer = new Timer();
                timer.schedule(new ProgressUpdate(), 300, 500);
            }
        }
    };
    View.OnClickListener listener2= new View.OnClickListener() {    //停止按钮
        @Override
        public void onClick(View v) {
                player.stop();
                player.release();  //停止并释放播放资源
                SB.setProgress(0); // 重置进度条显示
                SBHint.setText("00.00/00.00");

        }
    };

    View.OnClickListener listener3= new View.OnClickListener() {     //上一首按钮
        @Override
        public void onClick(View v) {
            int index = music_list.indexOf(mName); //获取当前播放歌曲的索引
            listView.setItemChecked(index - 1, true); //取消之前选中的 ListView 高亮状态，然后让新的选中项高亮
            int position = music_list.indexOf(mName);
            player.pause();
            player.release();
            timer.cancel();
            if (position > 0) {
                // 暂停当前音乐
                    mName = music_list.get(position - 1);
                }
            else {
                mName=music_list.get(music_list.size()-1);
            }

                initExoPlayer();
                player.play();
                timer = new Timer();
                timer.schedule(new ProgressUpdate(), 300, 500);
            }
        };


    View.OnClickListener listener4= new View.OnClickListener() {    //下一首按钮
        @Override
        public void onClick(View v) {
            int position = music_list.indexOf(mName);
            player.pause();
            timer.cancel();

            if (position < music_list.size() - 1) {
                mName = music_list.get(position + 1);
            }
            else {
                mName=music_list.get(0);
            }
                initExoPlayer();
                player.play();
                timer = new Timer();
                timer.schedule(new ProgressUpdate(), 300, 500);

        }
    };


    SeekBar.OnSeekBarChangeListener listenerSB =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    timer.cancel();
                    SBHint.setText("正在快进");
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    player.seekTo(SB.getProgress());
                    timer=new Timer();
                    timer.schedule(new ProgressUpdate(),300,500);
                }
            };

 private class ProgressUpdate extends TimerTask {//ProgressUpdate定时任务类：定时刷新SeekBar进度条
    @Override
    public void run() {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                if(player.isPlaying()){//正在播放时显示 播放位置和seekbar进度
                long position = player.getContentPosition();//获取媒体播放的当前位置（毫秒）
                     SB.setMax((int)player.getDuration());//seekBar设置最大进度
                SB.setProgress((int) position);//SeekBar设置当前进度
                SBHint.setText( format(position)+'/'+format(player.getDuration()));//显示当前音乐时间
            }}

        });
    }
}
    public String format(long position) {
//        将音乐毫秒数转为"分:秒"格式显示
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss"); // "分:秒"格式
        String timeStr = sdf.format(position); //会自动将时长(毫秒数)转换为分秒格式
        return timeStr;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_homework);

        initView();
        prepared = false; //初始值
        initExoPlayer();
        player.addListener(listener0);
        Bbegin.setOnClickListener(listener1);
        Bstop.setOnClickListener(listener2);
        Bbefore.setOnClickListener(listener3);
        Bnext.setOnClickListener(listener4);
        SB.setOnSeekBarChangeListener(listenerSB);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }

}