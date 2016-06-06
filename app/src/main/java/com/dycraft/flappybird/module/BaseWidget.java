package com.dycraft.flappybird.module;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.dycraft.flappybird.util.AtlasFactory;

/**
 * Created by Admin on 2016/6/5.
 */
public abstract class BaseWidget
{
    protected int width;
    protected int height;
    protected int x;
    protected int y;

    protected int curFrame;

    protected AtlasFactory atlas;
    protected Paint paint;

    public BaseWidget(AtlasFactory atlas)
    {
        this.curFrame = 0;
        this.atlas = atlas;
        this.paint = new Paint();

        //this.loadBitmap() 需自己去写
    }

    //随帧运动
    public void play()
    {
        curFrame++;
    }

    //绘图-Param：在主环境中绘图
    public abstract void onDraw(Canvas canvas);

    //加载图片
    public abstract void loadBitmap();

    //回收图片
    public abstract void recycleBitmap();

    //get & set 方法
    public void setRect(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void setRect(Rect rect)
    {
        this.x = rect.left;
        this.y = rect.top;
        this.width = rect.width();
        this.height = rect.height();
    }

    public Rect getRect()
    {
        return new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
    }

    public int getWidth()
    {
        return this.width;
    }

    public void setWidth(int w)
    {
        this.width = w;
    }

    public int getHeight()
    {
        return this.width;
    }

    public void setHeight(int h)
    {
        this.height = h;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getMidX()
    {
        return this.x + this.width / 2;
    }

    public int getMidY()
    {
        return this.y + this.height / 2;
    }
}
