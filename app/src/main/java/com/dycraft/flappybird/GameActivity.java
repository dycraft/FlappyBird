package com.dycraft.flappybird;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dycraft.flappybird.view.GameView;

public class GameActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //默认
        super.onCreate(savedInstanceState);
        //去除任务栏和标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //调整媒体音量
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //加载游戏视图
        this.setContentView(new GameView(this));
    }
}
