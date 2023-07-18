package com.example.uidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends AppCompatActivity {
    SeekBar seekBar; //进度条
    TextView seekBarHint; //音乐时长提示
    ImageButton btn_player; //播放按钮
    ExoPlayer player; //播放器
    private Timer timer; //定时器
    private boolean prepared; //播放器是否准备好
    private class ProgressUpdate extends TimerTask {
        @Override
        public void run() {
            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    long position = player.getContentPosition();
                    Log.d("flag", "pos=" + position);
                    seekBar.setProgress((int) position);
                    seekBarHint.setText( format(position) );
                }
            });
        }
    }
    public String format(long position) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss"); // "分:秒"格式
        String timeStr = sdf.format(position); //会自动将时长(毫秒数)转换为分秒格式
        return timeStr;
    }
    public void initView(){
        btn_player = (ImageButton) findViewById(R.id.btn_player);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarHint = (TextView) findViewById(R.id.seekBarHint);
    }
    public void initExoPlayer() {
        player = new ExoPlayer.Builder(MusicActivity.this).build();
        MediaItem mediaItem = MediaItem.fromUri(
                RawResourceDataSource.buildRawResourceUri( R.raw.she ));
        player.setMediaItem(mediaItem); //加载媒体资源
        player.setRepeatMode(ExoPlayer.REPEAT_MODE_ONE); //单曲循环
        player.prepare(); //会触发下面的监听器↓
    }
    Player.Listener listener1 = new Player.Listener() {
        @Override
//播放器状态监听器
        public void onPlaybackStateChanged(int playbackState) {
            if (playbackState == ExoPlayer.STATE_READY) { //播放器准备好了
                prepared = true;
                long realDurationMillis = player.getDuration();
                seekBar.setMax((int) realDurationMillis);
            }
        }
    };
    View.OnClickListener listener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ( !prepared ) //播放器没有准备好
                return;
            if ( player.isPlaying() ) {
                player.pause();
                btn_player.setImageResource(android.R.drawable.ic_media_play);
                timer.cancel(); //停止定时器
                timer = new Timer(); //新建定时器
            } else {
                player.play();
                btn_player.setImageResource(android.R.drawable.ic_media_pause);
                timer = new Timer();
                timer.schedule(new ProgressUpdate(), 300, 500);
            }
        }
    };
    SeekBar.OnSeekBarChangeListener listener3 =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    if (prepared && fromUser) {
                        player.seekTo(progress);
                    }
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    seekBarHint.setText(format(seekBar.getProgress()));
                }
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        prepared = false; //初始值
        initExoPlayer();
//3个监听
        player.addListener(listener1);
        btn_player.setOnClickListener(listener2);
        seekBar.setOnSeekBarChangeListener(listener3);
    } //end create
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
} //end activity