package com.dycraft.flappybird.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

import com.dycraft.flappybird.config.Status;
import com.dycraft.flappybird.module.Bird;
import com.dycraft.flappybird.module.Land;
import com.dycraft.flappybird.module.Pipe;
import com.dycraft.flappybird.module.Score;
import com.dycraft.flappybird.util.AtlasFactory;

import java.io.IOException;

/**
 * Created by Admin on 2016/6/5.
 */
public class GameView extends BaseView
{
    private AtlasFactory atlas;
    private int curTurn; //第几回合
    private int curScore;

    private Bird bird;
    private Land land;
    private Pipe pipe;
    private Score score;
    private Bitmap scoreBoard;
    private Score panelScore;
    private Score maxScore;

    private SoundPool sound;
    private int wingSound;

    public GameView(Context context)
    {
        super(context);

        this.atlas = new AtlasFactory(context.getResources());
        this.curTurn = 0;
        this.curScore = 0;
        Status.status = Status.STATUS.Start;

        this.bird = new Bird(atlas);
        this.land = new Land(atlas);
        this.pipe = new Pipe(atlas);
        this.score = new Score(atlas, 0);
        this.panelScore = new Score(atlas, 1);
        this.maxScore = new Score(atlas, 1);

        this.loadBitmap();

        this.sound = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        try
        {
            wingSound = this.sound.load(getResources().getAssets().openFd("sounds/sfx_wing.ogg"), 1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //触屏事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch (Status.status)
            {
                case Start:
                    bird.flap();
                    Status.status = Status.STATUS.Game;
                    break;

                case Game:
                    bird.flap();
                    //score.setScore(++curScore);
                    this.sound.play(wingSound, 1, 1, 1, 0, 1.0f);
                    break;

                case Over:
                    break;
            }
            return true;
        }

        return false;
    }

    @Override
    //绘制部分
    public void draw()
    {
        if (curTurn % 2 == 0)
            canvas.drawBitmap(atlas.get("bg_day"), 0, 0, paint);
        else
            canvas.drawBitmap(atlas.get("bg_night"), 0, 0, paint);
        pipe.onDraw(canvas);
        land.onDraw(canvas);
        bird.onDraw(canvas);
        score.onDraw(canvas);
        /*if (Status.status = Status.STATUS.Over)
        {
            //canvas.drawBitmap(, );
        }*/
    }

    @Override
    //逻辑部分
    public void logic()
    {
        bird.play();
        land.play();
        pipe.play();
        score.play();
    }

    //加载图片
    @Override
    public void loadBitmap()
    {

    }

    //回收图片
    @Override
    public void recycleBitmap()
    {
        atlas.recycle();

        bird.recycleBitmap();
        land.recycleBitmap();
        pipe.recycleBitmap();
        score.recycleBitmap();
    }
}
