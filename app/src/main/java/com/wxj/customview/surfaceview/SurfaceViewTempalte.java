package com.wxj.customview.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wuxiaojun on 16-8-5.
 */
public class SurfaceViewTempalte extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean isRunning = true;
    private Thread mThread;

    public SurfaceViewTempalte(Context context) {
        this(context, null);
    }

    public SurfaceViewTempalte(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewTempalte(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHolder = getHolder();
        mHolder.addCallback(this);
        //可以获取焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置常量
        setKeepScreenOn(true);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //开启一个线程，在线城里面进行绘制
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }


    @Override
    public void run() {
        while (isRunning){
            myDraw();
        }
    }

    private void myDraw() {
        try{
            mCanvas = mHolder.lockCanvas();
            if(mCanvas != null){
                //绘制代码

            }
        }catch (Exception e){
        }finally {
            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

}
