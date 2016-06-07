package com.dycraft.flappybird.module;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.dycraft.flappybird.property.Config;
import com.dycraft.flappybird.property.Constant;
import com.dycraft.flappybird.property.Status;
import com.dycraft.flappybird.util.AtlasFactory;

/**
 * Created by Admin on 2016/6/5.
 */
public class Land extends BaseWidget
{
    private Bitmap landImage;

    private final int LAND_POX_X = 0;
    private final int LAND_POX_Y = Constant.LAND_H;

    public Land(AtlasFactory atlas)
    {
        super(atlas);

        this.loadBitmap();
        this.init();
    }

    @Override
    public void init()
    {
        setRect(LAND_POX_X, LAND_POX_Y,
                landImage.getWidth(), landImage.getHeight());
    }

    @Override
    public void play()
    {
        if (!Status.isDead)
        {
            int bound = Constant.BG_WIDTH - landImage.getWidth();
            if (x <= bound)
            {
                x = 0;
            }
            else
            {
                x -= Config.SPEED;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(landImage, x, y, paint);
    }

    @Override
    public void loadBitmap()
    {
        landImage = atlas.get("land");
    }

    @Override
    public void recycleBitmap()
    {
        if (!landImage.isRecycled())
            landImage.recycle();
    }
}
