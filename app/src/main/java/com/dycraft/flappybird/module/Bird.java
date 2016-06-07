package com.dycraft.flappybird.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dycraft.flappybird.property.Config;
import com.dycraft.flappybird.property.Constant;
import com.dycraft.flappybird.property.Status;
import com.dycraft.flappybird.util.AtlasFactory;
import com.dycraft.flappybird.util.RandomFromTime;
import com.dycraft.flappybird.util.SoundPlayer;

/**
 * Created by Admin on 2016/6/5.
 */
public class Bird extends BaseWidget
{
    //3*3 个鸟的图片
    private Bitmap[][] birdImages;
    private Bitmap mainBirdImg; //当前需要绘制的鸟的图像

    private SoundPlayer soundPlayer;

    private int curBird;

    //物理量
    private final double g = Config.GRAVITY;
    private double v; //速度
    private double w; //角速度
    private double h; //高度
    private float angle;

    //常数
    private final int BIRD_POX_X = 80;
    private final int BIRD_POX_Y = 246;
    private final double BIRD_MAX_V = 8.0;
    private final int BIRD_MAX_ANGLE = 120;
    private final int FLAPPY_FRAME = Config.FPS / 10; //振动的帧数

    public Bird(AtlasFactory atlas, SoundPlayer soundPlayer)
    {
        super(atlas);

        birdImages = new Bitmap[3][3];
        angle = 0;

        this.soundPlayer = soundPlayer;

        this.loadBitmap();
        this.init();
    }

    @Override
    public void init()
    {
        angle = 0;
        v = 0;
        w = 0;

        curBird = RandomFromTime.getRandomInt() % 3;
        mainBirdImg = birdImages[curBird][0];

        this.setRect(BIRD_POX_X, BIRD_POX_Y,
                mainBirdImg.getWidth(), mainBirdImg.getHeight());
    }

    @Override
    public void play()
    {
        curFrame++;

        if (Status.status == Status.State.Start)
        {
            angle = 0;
            //振翅动作
            mainBirdImg = birdImages[curBird][curFrame / FLAPPY_FRAME % 3];
        }
        else
        {
            if (h < Constant.LAND_H)
            {
                //下降，到地面停止，与是否死亡无关
                v = v - g;
                if (v < -BIRD_MAX_V)
                    v = -BIRD_MAX_V;
                h = h - v;
            }

            //旋转
            angle -= v;
            v += Config.ANGLE_A;
            if (angle <= -90)
                angle = -90;

            //振翅动作
            if (!Status.isDead)
            {
                mainBirdImg = birdImages[curBird][curFrame / FLAPPY_FRAME % 3];
            }
        }

        //死亡判定
        if (!Status.isDead && h > Constant.LAND_H)
        {
            die();
        }

        /*if (isDead)
        {

        }*/
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        if (Status.status == Status.State.Game)
        {
            canvas.save();
            if (angle > 20)
                canvas.rotate(-20, x+width/2, (int)h+height/2);
            else
                canvas.rotate(-angle, x+width/2, (int)h+height/2);
            canvas.drawBitmap(mainBirdImg, x, (int)h, paint);
            canvas.restore();
        }
        else
        {
            //不包括旋转
            canvas.drawBitmap(mainBirdImg, x, (int)h, paint);
        }
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
        v = Config.VELOCITY;
        w = Config.ANGLE_SPEED;
        angle = BIRD_MAX_ANGLE;
    }

    //死亡命令
    public void die()
    {
        Status.isDead = true;
        soundPlayer.play("die");
        Status.next();
    }
}
