package com.example;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaojun on 16-8-30.
 */
public class TestRxJava4 {

    public static void main(String[] args){

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("禽兽");
                subscriber.onNext("禽兽２号");

                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行完成");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("出错了");
            }

            @Override
            public void onNext(String s) {
                System.out.println("s="+s);
            }
        });

    }

}
