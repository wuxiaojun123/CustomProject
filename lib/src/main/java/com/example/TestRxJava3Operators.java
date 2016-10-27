package com.example;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by wuxiaojun on 16-8-30.
 */
public class TestRxJava3Operators {
    /***
     * 需求，当我们需要在打印的Hello World中添加一个字符(hahaha)的时候，就需要用到操作符map
     * 操作符的作用是在Obserable和subscriber之间修改Obserable发出的事件的
     *
     * @param args
     */
    public static void main(String[] args) {

        Observable.just("Hello World")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + "--hahaha";
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return Integer.toString(s.hashCode());
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("数据是：" + s);
                    }
                });
    }
}
