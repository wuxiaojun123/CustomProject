package com.wxj.customview.xutilsIoc;

import android.app.Activity;
import android.os.Bundle;

import com.wxj.customview.R;

import java.lang.reflect.Proxy;

/**
 * Created by wuxiaojun on 16-7-22.
 */
@ContentView(R.layout.activity_arrow)
public class IocBaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调用contentView
        InjectUtils.inject(this);


    }

}
