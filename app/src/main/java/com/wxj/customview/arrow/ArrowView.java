package com.wxj.customview.arrow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-6-25.
 */
public class ArrowView extends View {
    //用于记录当前的位置，取值[0,1],映射整个path的长度
    private float currentValue = 0;
    private float[] pos;//当前点的实际位置
    private float[] tan;//当前点的tan值，用于计算图片所需旋转角度
    private Bitmap mBitmap;//箭头图片
    private Matrix mMatrix;//矩阵，用于对图片进行一些操作

    private int mViewWidth;
    private int mViewHeigth;

    private Paint mDeafultPaint;


    public ArrowView(Context context) {
        this(context, null);
    }

    public ArrowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow,options);
        mMatrix = new Matrix();

        mDeafultPaint = new Paint();
        mDeafultPaint.setColor(Color.BLACK);
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setDither(true);
        mDeafultPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeigth = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        firstMethod(canvas);
        //secondMethod(canvas);
    }

    private void secondMethod(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeigth / 2);
        Path path = new Path();
        path.addCircle(0,0,200, Path.Direction.CW);

        PathMeasure measure = new PathMeasure(path,false);
        currentValue += 0.005;
        if(currentValue >= 1){
            currentValue = 0;
        }

        //获取当前位置的坐标以及趋势的矩阵
        measure.getMatrix(measure.getLength()*currentValue,mMatrix,PathMeasure.TANGENT_MATRIX_FLAG|PathMeasure.POSITION_MATRIX_FLAG);
        mMatrix.preTranslate(-mBitmap.getWidth()/2,-mBitmap.getHeight() / 2);//将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, mDeafultPaint);
        canvas.drawBitmap(mBitmap,mMatrix,mDeafultPaint);

        invalidate();
    }

    private void firstMethod(Canvas canvas) {
        canvas.translate(mViewWidth / 2, mViewHeigth / 2);

        Path path = new Path();
        path.addCircle(0,0,200, Path.Direction.CW);//添加一个圆形

        PathMeasure measure = new PathMeasure(path,false);

        currentValue += 0.005;
        if(currentValue >= 1){
            //currentValue = 0;
        }

        //获取到
        measure.getPosTan(measure.getLength() * currentValue, pos, tan);

        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片,使用图片的中心点进行旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

        invalidate();
    }


}
