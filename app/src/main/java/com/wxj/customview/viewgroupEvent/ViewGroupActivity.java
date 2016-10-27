package com.wxj.customview.viewgroupEvent;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-6-29.
 */
public class ViewGroupActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewgroup_event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
