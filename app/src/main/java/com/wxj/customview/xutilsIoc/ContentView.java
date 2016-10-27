package com.wxj.customview.xutilsIoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by wuxiaojun on 16-7-22.
 */
@Target(ElementType.TYPE)//这里的target是作用于某个类上边，也就是写在public class MyActivity extends Activity{}上边
@Retention(RetentionPolicy.RUNTIME)//这个表示在运行时期去加载
public @interface ContentView {
    int value();
}
