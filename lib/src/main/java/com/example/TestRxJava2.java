package com.example;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wuxiaojun on 16-8-30.
 */
public class TestRxJava2 {

    public static void main(String[] args) {
        //Observable<String> mObservable = Observable.just("Hello World!!!");
        //mObservable.subscribe(mAction0);

        //简洁写法,just的作用是创建只发出一个事件就结束的Obserable对象
        Observable.just("Hello World!!!").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("输出内容是:"+s);
            }
        });
    }
    static Action1<String> mAction0 = new Action1<String>() {

        @Override
        public void call(String s) {
            System.out.println("输出内容是" + s);
        }
    };
}
