package com.wxj.customview.surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-8-5.
 */
public class SurfaceViewLuckyPan extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean isRunning = true;
    private Thread mThread;


    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财", "ＩＰＨＯＮＥ", "服装一套", "哈哈哈"};
    private int[] mImgs = new int[]{R.mipmap.danfan, R.mipmap.ipad, R.mipmap.f015, R.mipmap.iphone, R.mipmap.meizi, R.mipmap.f040};
    private int[] mColor = new int[]{0xFFFC3000, 0xFFF17e01, 0xFFFC3000, 0xFFF17e01, 0xFFFC3000, 0xFFF17e01};

    private int mItemCount = 6;
    //与图片对应的bitmap数组
    private Bitmap[] mImgsBitmap;
    //整个盘块的范围
    private RectF mRange = new RectF();
    //整个盘块的直径
    private int mRadius;
    //圆弧画笔
    private Paint mArcPaint;
    //文本的画笔
    private Paint mTextPaint;
    //滚动的速度
    private double mSpeed;
    //有两个线程去改变
    private volatile int mStartAngle = 0;
    //是否点击了停止
    private boolean isShouldEnd;
    //转盘的中心位置
    private int mCenter;
    //只要用户设置了，直接取最小值
    private int mPadding;
    //背景
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg2);
    //文字的大小
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());


    public SurfaceViewLuckyPan(Context context) {
        this(context, null);
    }

    public SurfaceViewLuckyPan(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewLuckyPan(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置背景大小
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mPadding = getPaddingLeft();
        //直径
        mRadius = width - mPadding * 2;
        //中心点
        mCenter = mRadius / 2;

        setMeasuredDimension(width, width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setTextSize(mTextSize);
        //范围
        mRange = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding + mRadius);
        //初始化图片
        mImgsBitmap = new Bitmap[mItemCount];
        for (int i = 0; i < mItemCount; i++) {
            mImgsBitmap[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }

        //开启一个线程，在线城里面进行绘制
        isRunning = true;
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
        while (isRunning) {
            long start = System.currentTimeMillis();
            myDraw();
            long end = System.currentTimeMillis();
            if (end - start < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void myDraw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //绘制代码
                drawBg();
                //绘制盘块
                float mTemAngle = mStartAngle;
                float sweepAngle = 360/mItemCount;

                for (int i = 0; i < mItemCount; i++) {
                    //绘制盘块
                    mArcPaint.setColor(mColor[i]);
                    mCanvas.drawArc(mRange,mTemAngle,sweepAngle,true,mArcPaint);
                    //绘制文本
                    drawText(mStrs[i],mTemAngle,sweepAngle);
                    //绘制图片
                    //drawBitmap();

                    mTemAngle += sweepAngle;
                }
            }
        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    //绘制文本
    private void drawText(String text,float startAngle,float sweepAngle) {
        Path mPath = new Path();
        mPath.addArc(mRange,startAngle,sweepAngle);
        //测量文本大小
        float textWidth = mTextPaint.measureText(text,0,text.length());
        //圆周长/6 -
        float hOffset = (float) ((Math.PI*mRadius/6 - textWidth)/2);
        //hOffset是文本绘制的起始位置----水平偏移量
        //vOffset是文本绘制的距离高度----垂直偏移量
        mCanvas.drawTextOnPath(text,mPath,hOffset,mPadding,mTextPaint);
    }

    private void drawBg() {
        //绘制背景颜色
        mCanvas.drawColor(0xFFFFFFFF);
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding / 2, mPadding / 2, getMeasuredWidth() - mPadding / 2, getMeasuredHeight() - mPadding / 2), null);

    }

}
