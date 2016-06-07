package com.dycraft.flappybird.property;

/**
 * Created by Admin on 2016/6/5.
 */

public class Status
{
    public enum State
    {
        Start, //标题界面，触击进入游戏
        Game, //游戏
        Over,
        Award,
        Button //结束过程分三个流程
    }

    public static State status;
    public static boolean isDead;

    //初始化方法
    public static void init()
    {
        status = State.Start;
        isDead = false;
    }

    //流程切换方法
    public static void next()
    {
        switch (status)
        {
            case Start:
                status = State.Game;
                break;

            case Game:
                status = State.Over;
                break;

            case Over:
                status = State.Award;
                break;

            case Award:
                status = State.Button;
                break;

            case Button:
                status = State.Start;
                break;
        }
    }
}

