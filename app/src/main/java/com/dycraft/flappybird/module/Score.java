package com.dycraft.flappybird.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dycraft.flappybird.config.Constant;
import com.dycraft.flappybird.util.AtlasFactory;

/**
 * Created by Admin on 2016/6/5.
 */
public class Score extends BaseWidget
{
    Bitmap numImage[];

    private int score;
    private int Font;

    public Score(AtlasFactory atlas, int Font)
    {
        super(atlas);

        this.score = 0;
        this.Font = Font;
        numImage = new Bitmap[10];

        this.loadBitmap();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        String s = String.valueOf(score);
        for (int i = 0; i < s.length(); i++)
        {
            //考虑1的大小和别的不一样
            int num = Integer.parseInt(String.valueOf(s.charAt(i)));
            canvas.drawBitmap(numImage[num],
                    x + (i + 1) * numImage[0].getWidth() - numImage[num].getWidth(), y, paint);
        }
    }

    @Override
    public void loadBitmap()
    {
        switch(Font)
        {
            case 0:
                for (int i = 0; i < 10; i++)
                {
                    numImage[i] = atlas.get(String.format("font_0%d", 48 + i));
                }
                break;
            case 1:
                for (int i = 0; i < 10; i++)
                {
                    numImage[i] = atlas.get(String.format("number_score_0%d", i));
                }
                break;
            case 2:
                for (int i = 0; i < 10; i++)
                {
                    numImage[i] = atlas.get(String.format("number_context_0%d", i));
                }
                break;
        }

        updateSize();
    }

    @Override
    public void recycleBitmap()
    {
        for (int i = 0; i < 10; i++)
        {
            if (!numImage[i].isRecycled())
                numImage[i].recycle();
        }
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int n)
    {
        this.score = n;
        updateSize();
    }

    private void updateSize()
    {
        int len = String.valueOf(score).length();
        setRect((Constant.BG_WIDTH - len * numImage[0].getWidth()) / 2, Constant.BG_HEIGHT / 8,
                len * numImage[0].getWidth(), numImage[0].getHeight());
    }
}
