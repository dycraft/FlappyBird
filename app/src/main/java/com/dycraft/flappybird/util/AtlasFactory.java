package com.dycraft.flappybird.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.dycraft.flappybird.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Admin on 2016/6/5.
 */
public class AtlasFactory
{
    private int atlasWidth; //1024
    private int atlasHeight; //1024

    private Bitmap atlasBitMap;
    private HashMap<String, Bitmap> atlasHashMap;

    public AtlasFactory(Resources resources)
    {
        atlasHashMap = new HashMap<>();
        try
        {
            atlasBitMap = BitmapFactory.decodeStream(resources.getAssets().open("gfx/atlas.png"));
            atlasWidth = atlasBitMap.getWidth();
            atlasHeight = atlasBitMap.getHeight();
            InputStream in = resources.openRawResource(R.raw.atlas);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();

            String content = new String(buffer);
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i++)
            {
                String[] data = lines[i].split(" ");
                int w = Integer.parseInt(data[1]);
                int h = Integer.parseInt(data[2]);
                int x = (int)(Double.parseDouble(data[3]) * atlasWidth);
                int y = (int)(Double.parseDouble(data[4]) * atlasHeight);
                atlasHashMap.put(data[0], Bitmap.createBitmap(atlasBitMap, x, y, w, h));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public Bitmap get(String key)
    {
        //bitmap不要赋值
        Bitmap bmp = atlasHashMap.get(key);
        return bmp.copy(bmp.getConfig(), true);
    }

    //回收bitmap
    public void recycle()
    {
        Iterator it = atlasHashMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            if (!((Bitmap)entry.getValue()).isRecycled())
                ((Bitmap)entry.getValue()).recycle();
        }
    }
}
