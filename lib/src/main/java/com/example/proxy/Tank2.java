package com.example.proxy;

/**
 * 继承
 * Created by wuxiaojun on 16-7-23.
 */
public class Tank2 extends Tank {

    @Override
    public void move() {
        long start = System.currentTimeMillis();
        super.move();
        long end = System.currentTimeMillis();
        System.out.println("time:=" + (start - end));
    }


}
