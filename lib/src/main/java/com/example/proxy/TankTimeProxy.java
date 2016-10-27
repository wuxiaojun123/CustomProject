package com.example.proxy;

/**
 * 聚合
 *
 * Created by wuxiaojun on 16-7-23.
 */
public class TankTimeProxy implements Moveable {

    public TankTimeProxy(Moveable t){
        this.t = t;
    }

    Moveable t;

    @Override
    public void move(){
        long start = befor();
        t.move();
        after(start);
    }

    @Override
    public void stop() {
        long start = befor();
        t.stop();
        after(start);
    }

    private long befor(){
        long start = System.currentTimeMillis();
        System.out.println("start time:"+start);
        return start;
    }

    private void after(long startTime){
        long end = System.currentTimeMillis();
        System.out.println("end time:=" + (end - startTime));
    }

}
