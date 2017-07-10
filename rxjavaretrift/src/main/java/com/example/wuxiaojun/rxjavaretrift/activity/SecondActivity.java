package com.example.wuxiaojun.rxjavaretrift.activity;

import android.os.Bundle;

import com.example.wuxiaojun.rxjavaretrift.R;

import nsu.edu.com.library.SwipeBackActivity;

/**
 * Created by wuxiaojun on 16-12-22.
 */

public class SecondActivity extends SwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

    }
}
