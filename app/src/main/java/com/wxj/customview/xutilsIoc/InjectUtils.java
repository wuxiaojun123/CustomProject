package com.wxj.customview.xutilsIoc;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * Created by wuxiaojun on 16-7-22.
 */
public class InjectUtils {

    /***
     * 注入
     * @param activity
     */
    public static void inject(Activity activity){
        //注入布局
        injectLayout(activity);
    }

    private static void injectViews(Activity activity){
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            ViewInject mViewInject = field.getAnnotation(ViewInject.class);
            int id = mViewInject.value();

        }

    }


    /***
     *注入布局
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        int value = contentView.value();
        activity.setContentView(value);
    }

}
