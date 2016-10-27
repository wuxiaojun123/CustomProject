package com.example.proxy;

/**
 * 代理用在那些情景
 * 记录某一个方法的日志，在某个方法之前打印和方法之后打印
 * 记录某一个方法的权限，达到某一个条件才能执行
 *
 * aop是动态代理的一种应用
 * Created by wuxiaojun on 16-7-23.
 */
public class Client {

    public static void main(String[] args){

        Tank t = new Tank();

        TankTimeProxy ttp = new TankTimeProxy(t);

        TankLogProxy tlp = new TankLogProxy(ttp);

        Moveable m = tlp;

        m.move();

    }

}
