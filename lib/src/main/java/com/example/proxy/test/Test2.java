package com.example.proxy.test;

import com.example.proxy.Moveable;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Created by wuxiaojun on 16-7-23.
 */
public class Test2 {

    public static void main(String[] args){
        //通过反射获取到接口中的方法
        Method[] methods = Moveable.class.getMethods();
        for (Method m : methods){
            System.out.println("name="+m.getName());
        }
    }

}
