package com.wxj.customview.sqlite;

/**
 * Created by wuxiaojun on 16-9-14.
 */
public class Student {

    public static final String NAME = "name";
    public static final String AGE = "age";

    public int id;
    public String name;
    public int age;

    public Student(int id,String name,int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(String name,int age){
        this.name = name;
        this.age = age;
    }

}
