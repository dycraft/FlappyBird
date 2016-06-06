package com.dycraft.flappybird;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Admin on 2016/6/5.
 *
 * Reference: http://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
 */
public class SplashScreen extends Activity
{
    private static int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        //默认
        super.onCreate(savedInstanceState);
        //去除任务栏和标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //加载启动画面布局
        setContentView(R.layout.activity_splash);

        //在2秒后关闭启动画面，并启动GameActivity
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent localIntent = new Intent(SplashScreen.this, GameActivity.class);
                SplashScreen.this.startActivity(localIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
