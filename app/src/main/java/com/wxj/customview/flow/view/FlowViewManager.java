package com.wxj.customview.flow.view;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by wuxiaojun on 16-9-1.
 */
public class FlowViewManager {

    private static FlowViewManager mInstance;

    private WindowManager mWindowManager;

    private FlowViewManager(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static FlowViewManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mInstance) {
                mInstance = new FlowViewManager(context);
            }
        }
        return mInstance;
    }

}
