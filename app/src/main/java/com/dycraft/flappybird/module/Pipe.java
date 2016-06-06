package com.dycraft.flappybird.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dycraft.flappybird.config.Config;
import com.dycraft.flappybird.config.Constant;
import com.dycraft.flappybird.util.AtlasFactory;
import com.dycraft.flappybird.util.RandomFromTime;

import java.util.Random;

/**
 * Created by Admin on 2016/6/5.
 */
public class Pipe extends BaseWidget
{
    private final int PIPE_NUM = 1 + Constant.BG_WIDTH / Config.PIPE_L_R_DISTANCE;
    //为了边缘绘制的方便，就直接加一个
    private final int PIPE_MIN_H = 50;
    //桶露出的最少长度
    private final int PIPE_H_RANGE = Constant.LAND_H - PIPE_MIN_H * 2 - Config.PIPE_U_D_DISTANCE;
    //桶的高度可变化的范围

    private Bitmap pipe_up[], pipe_down[];

    private int pipeX[], pipeY[];

    private Random random;

    public Pipe(AtlasFactory atlas)
    {
        super(atlas);

        pipe_up = new Bitmap[PIPE_NUM];
        pipe_down = new Bitmap[PIPE_NUM];
        pipeX = new int[PIPE_NUM];
        pipeY = new int[PIPE_NUM];
        random = new Random(System.currentTimeMillis());

        this.loadBitmap();
    }

    @Override
    public void play()
    {
        for (int i = 0; i < PIPE_NUM; i++)
        {
            pipeX[i] -= Config.SPEED;

            if (pipeX[i] <= -pipe_up[i].getWidth())
            {
                pipeX[i] = Config.PIPE_L_R_DISTANCE * PIPE_NUM - pipe_up[i].getWidth();
                pipeY[i] = random.nextInt(PIPE_H_RANGE) + PIPE_MIN_H;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        for (int i = 0; i < PIPE_NUM; i++)
        {
            canvas.drawBitmap(pipe_up[i], pipeX[i], pipeY[i] - pipe_up[i].getHeight(), paint);
            canvas.drawBitmap(pipe_down[i], pipeX[i], pipeY[i] + Config.PIPE_U_D_DISTANCE, paint);
        }
    }

    @Override
    public void loadBitmap()
    {
        for (int i = 0; i < PIPE_NUM; i++)
        {
            pipe_up[i] = atlas.get("pipe_down");
            pipe_down[i] = atlas.get("pipe_up");
        }

        init();
    }

    @Override
    public void recycleBitmap()
    {
        for (int i = 0; i < PIPE_NUM; i++)
        {
            if (!pipe_up[i].isRecycled())
                pipe_up[i].recycle();
            if (!pipe_down[i].isRecycled())
                pipe_down[i].recycle();
        }
    }

    //初始化桶的位置
    public void init()
    {
        for (int i = 0; i < PIPE_NUM; i++)
        {
            pipeX[i] = Config.PIPE_L_R_DISTANCE * (i + PIPE_NUM);
            pipeY[i] = random.nextInt(PIPE_H_RANGE) + PIPE_MIN_H;
        }
    }
}
