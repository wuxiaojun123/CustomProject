package com.wxj.customview.circle.scale;

import android.app.Activity;
import android.os.Bundle;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-9-1.
 */
public class CircleScaleActivity extends Activity {

    CircleScaleView progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_scale);
        progressCircle = (CircleScaleView) findViewById(R.id.progress);

    }


}
