package com.example.proxy.test;

public class TankTimeProxy implements com.example.proxy.Moveable {
    public TankTimeProxy(com.example.proxy.Moveable t) {
        this.t = t;
    }

    com.example.proxy.Moveable t;

    @Override
    public void move() {
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

    private long befor() {
        long start = System.currentTimeMillis();
        System.out.println("start time:" + start);
        return start;
    }

    private void after(long startTime) {
        long end = System.currentTimeMillis();
        System.out.println("end time:=" + (end - startTime));
    }
}