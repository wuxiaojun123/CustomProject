package com.example.proxy;

import java.util.Random;

/**
 * http://www.icoolxue.com
 * Created by wuxiaojun on 16-7-23.
 */
public class Tank implements Moveable {

    @Override
    public void move() {
        System.out.println("Tank Moving....");
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("Tank stopping......");
    }
}
