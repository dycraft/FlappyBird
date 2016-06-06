package com.dycraft.flappybird.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dycraft.flappybird.config.Config;
import com.dycraft.flappybird.config.Status;
import com.dycraft.flappybird.util.AtlasFactory;
import com.dycraft.flappybird.util.RandomFromTime;

import java.util.Random;

/**
 * Created by Admin on 2016/6/5.
 */
public class Bird extends BaseWidget
{
    //3*3 个鸟的图片
    private Bitmap[][] birdImages;
    private Bitmap mainBirdImg; //当前需要绘制的鸟的图像

    private int curBird;

    //物理量
    private final double v0 = Config.VELOCITY;
    private final double g = Config.GRAVITY;
    private double v;
    private double h;
    private float angle;

    //常数
    private final int BIRD_POX_X = 72;
    private final int BIRD_POX_Y = 180;
    private final int BIRD_MAX_V = 10;
    private final int BIRD_MAX_ANGLE = 120;
    private final float BIRD_ROTATE_SPEED = (float)4.5;
    private final int FLAPPY_FRAME = Config.FPS / 10; //振动的帧数

    public Bird(AtlasFactory atlas)
    {
        super(atlas);

        birdImages = new Bitmap[3][3];
        angle = 0;

        this.loadBitmap();
    }

    @Override
    public void play()
    {
        super.play();
        if (Status.status == Status.STATUS.Game)
        {
            //下降
            v = v - g;
            if (v < -BIRD_MAX_V)
                v = -BIRD_MAX_V;
            y = y - (int)v;
            angle -= BIRD_ROTATE_SPEED;
            if (angle <= -90)
                angle = -90;
        }
        //振翅动作
        mainBirdImg = birdImages[curBird][curFrame / FLAPPY_FRAME % 3];
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        //利用画布状态的存储来旋转
        canvas.save();
        if (angle > 30)
            canvas.rotate(-30, x+width/2, y+height/2);
        else
            canvas.rotate(-angle, x+width/2, y+height/2);
        canvas.drawBitmap(mainBirdImg, x, y, paint);
        canvas.restore();
    }

    @Override
    public void loadBitmap()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                birdImages[i][j] = atlas.get(String.format("bird%d_%d", i, j));
            }
        }

        curBird = RandomFromTime.getRandomInt() % 3;
        mainBirdImg = birdImages[curBird][0];

        this.setRect(BIRD_POX_X, BIRD_POX_Y,
                mainBirdImg.getWidth(), mainBirdImg.getHeight());
    }

    @Override
    public void recycleBitmap()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (!birdImages[i][j].isRecycled())
                    birdImages[i][j].recycle();
            }
        }
        mainBirdImg.recycle();
    }

    //振翅
    public void flap()
    {
        v = v0;
        angle = BIRD_MAX_ANGLE;
    }
}
