package com.dycraft.flappybird.util;

import java.util.Random;

/**
 * Created by Admin on 2016/6/5.
 */
public class RandomFromTime
{
    //获取时间为种子的随机数
    public static int getRandomInt()
    {
        return new Random(System.currentTimeMillis()).nextInt();
    }
}
