package com.example.proxy;

/**
 * Created by wuxiaojun on 16-7-23.
 */
public class TankLogProxy implements Moveable{


    public TankLogProxy(Moveable t){
        this.t = t;
    }

    Moveable t;

    @Override
    public void move(){
        System.out.println("日志开始");
        t.move();
        System.out.println("日志结束");
    }

    @Override
    public void stop() {
        t.stop();
    }
}
