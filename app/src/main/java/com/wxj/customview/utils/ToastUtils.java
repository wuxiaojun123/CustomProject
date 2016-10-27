package com.wxj.customview.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wuxiaojun on 16-9-14.
 */
public class ToastUtils {

    public static Toast mToast;

    public static void showToast(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
//            mToast.cancel();
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setText(content);
        mToast.show();
    }

    public static void showToast2(Context context, String content) {
        mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        mToast.setText(content);
        mToast.show();
    }


}
