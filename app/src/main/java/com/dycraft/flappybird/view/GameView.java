package com.dycraft.flappybird.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.dycraft.flappybird.module.Panel;
import com.dycraft.flappybird.module.button.PlayButton;
import com.dycraft.flappybird.property.Constant;
import com.dycraft.flappybird.property.Status;
import com.dycraft.flappybird.module.Bird;
import com.dycraft.flappybird.module.Land;
import com.dycraft.flappybird.module.Pipe;
import com.dycraft.flappybird.module.Score;
import com.dycraft.flappybird.util.AtlasFactory;
import com.dycraft.flappybird.util.SoundPlayer;

/**
 * Created by Admin on 2016/6/5.
 */
public class GameView extends BaseView
{
    private AtlasFactory atlas;
    private int totalTurn;
    private int curScore;

    private SoundPlayer soundPlayer;

    //过程-开始
    private Bitmap bg_day;
    private Bitmap bg_night;
    private Bitmap ready;
    private final int READY_Y = 146;
    private Bitmap tutorial;
    private final int TUTORIAL_Y = 220;
    private Bird bird;
    private Land land;

    //过程-游戏
    private Pipe pipe;
    private Score score;

    //过程-结束
    private Panel panel;
    private PlayButton replayButton;
    private final int OVER_Y = 130;

    public GameView(Context context)
    {
        super(context);

        //加载工具
        this.soundPlayer = new SoundPlayer(context.getResources());
        this.atlas = new AtlasFactory(context.getResources());

        //初始化参数
        this.totalTurn = 0;
        this.curScore = 0;

        //初始化控件
        this.bird = new Bird(atlas, soundPlayer);
        this.land = new Land(atlas);
        this.pipe = new Pipe(atlas, bird);
        this.score = new Score(atlas, 0);
        this.panel = new Panel(atlas);
        this.replayButton = new PlayButton(atlas);

        Status.init();
        this.loadBitmap();
    }

    public void init()
    {
        Status.init();
        bird.init();
        land.init();
        pipe.init();
        score.init();
        panel.init();
        replayButton.init();
    }

    //触屏事件
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //触击
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch (Status.status)
            {
                case Start:
                    bird.flap();
                    Status.next();
                    break;

                case Game:
                    bird.flap();
                    score.setScore(++curScore);
                    this.soundPlayer.play("wing");
                    break;

                case Over:
                    break;
            }
            return true;
        }
        //按下
        else if (event.getAction() == event.ACTION_BUTTON_PRESS)
        {
            if (replayButton.isTouch(event) && Status.status == Status.State.Button)
            {
                replayButton.setPressed(true);
            }
        }
        //释放
        else if (event.getAction() == event.ACTION_BUTTON_RELEASE)
        {
            if (replayButton.isTouch(event) && Status.status == Status.State.Button)
            {
                replayButton.setPressed(false);
                init();
            }
        }

        return false;
    }

    @Override
    //绘制部分
    public void draw()
    {
        if (totalTurn % 2 == 0)
            canvas.drawBitmap(bg_day, 0, 0, paint);
        else
            canvas.drawBitmap(bg_night, 0, 0, paint);
        pipe.onDraw(canvas);
        land.onDraw(canvas);
        bird.onDraw(canvas);
        score.onDraw(canvas);
        replayButton.onDraw(canvas);

        if (Status.status == Status.State.Start)
        {
            canvas.drawBitmap(tutorial, (Constant.BG_WIDTH - tutorial.getWidth()) / 2,
                    TUTORIAL_Y, paint);
            canvas.drawBitmap(ready, (Constant.BG_WIDTH - ready.getWidth()) / 2,
                    READY_Y, paint);
        }

        /*switch (Status.status)
        {
            case Start:
                break;

            case Game:
                break;

            case Over:
                break;
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
        replayButton.play();
    }

    //加载图片
    @Override
    public void loadBitmap()
    {
        this.bg_day = atlas.get("bg_day");
        this.bg_night = atlas.get("bg_night");
        this.ready = atlas.get("text_ready");
        this.tutorial = atlas.get("tutorial");
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
        replayButton.recycleBitmap();

        if (!this.bg_day.isRecycled())
            this.bg_day.recycle();
        if (!this.bg_night.isRecycled())
            this.bg_night.recycle();
        if (!this.ready.isRecycled())
            this.ready.recycle();
        if (!this.tutorial.isRecycled())
            this.tutorial.recycle();
    }
}
