package com.example;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wuxiaojun on 16-8-30.
 */
public class TestRxJava1 {

    public static void main(String[] args){
        mObservable.subscribe(mSubscriber);
    }

    static Observable<String> mObservable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onStart();
            subscriber.onNext("Hello World!!!");
            subscriber.onCompleted();
        }
    });

    static Subscriber<String> mSubscriber = new Subscriber<String>() {
        @Override
        public void onStart() {
            System.out.println("做初始化onStart");
        }

        @Override
        public void onCompleted() {
            System.out.println("执行onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("执行onError");
        }

        @Override
        public void onNext(String s) {
            System.out.println("输出内容"+s);
        }
    };

}
