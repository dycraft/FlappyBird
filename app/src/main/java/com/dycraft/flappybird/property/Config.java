package com.dycraft.flappybird.property;

/**
 * Created by Admin on 2016/6/5.
 */
public class Config
{
    public static int FPS = 50; //每秒的帧数

    public static int SPEED = 3; //卷动速度
    public static double GRAVITY = 1.0; //重力
    public static double ANGLE_A = 0.4; //角加速度
    public static double VELOCITY = 5.0; //初速度
    public static double ANGLE_SPEED = -10; //初角速度

    public static int PIPE_U_D_DISTANCE = 100; //上下两桶之间的距离
    public static int PIPE_L_R_DISTANCE = 144; //左右两桶之间的距离(包括桶宽)
}
