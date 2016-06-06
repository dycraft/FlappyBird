package com.dycraft.flappybird.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.dycraft.flappybird.config.Config;
import com.dycraft.flappybird.config.Constant;

/**
 * Created by Admin on 2016/6/5.
 */
public class BaseView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    protected Canvas canvas;
    protected Paint paint;
    protected SurfaceHolder sfh;

    protected Thread thread; //绘制线程
    protected boolean isThread;

    public BaseView(Context context)
    {
        super(context);

        this.sfh = this.getHolder();
        this.sfh.addCallback(this);
        this.paint = new Paint();
        setFocusable(true);
    }

    @Override
    public void run()
    {
        while (isThread)
        {
            long start = System.currentTimeMillis();

            //Logic
            this.logic();

            //Draw
            try
            {
                canvas = sfh.lockCanvas();

                if (canvas != null)
                {
                    //清空面板
                    //canvas.drawColor(Color.BLACK);
                    //像素放大
                    canvas.scale((float)Constant.SCREEN_WIDTH / Constant.BG_WIDTH,
                            (float)Constant.SCREEN_HEIGHT / Constant.BG_HEIGHT);
                    //绘制
                    this.draw();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (canvas != null)
                {
                    sfh.unlockCanvasAndPost(canvas);
                }
            }

            long end = System.currentTimeMillis();
            try
            {
                if (end - start < 1000 / Config.FPS)
                {
                    Thread.sleep(1000 / Config.FPS - (end - start));
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    //view改变
    @Override
    public void surfaceChanged(SurfaceHolder sfh, int a, int b, int c)
    {

    }

    //view创建
    @Override
    public void surfaceCreated(SurfaceHolder sfh)
    {
        Constant.SCREEN_WIDTH = this.getWidth();
        Constant.SCREEN_HEIGHT = this.getHeight();
        this.isThread = true;

        this.loadBitmap();

        thread = new Thread(this);
        thread.start();
    }

    //view销毁
    @Override
    public void surfaceDestroyed(SurfaceHolder sfh)
    {
        isThread = false;
        this.recycleBitmap();
    }

    //触屏事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }

    //绘制部分
    public void draw()
    {

    }

    //逻辑部分
    public void logic()
    {

    }

    //加载图片
    public void loadBitmap()
    {

    }

    //回收图片
    public void recycleBitmap()
    {
        //todo
    }
}
