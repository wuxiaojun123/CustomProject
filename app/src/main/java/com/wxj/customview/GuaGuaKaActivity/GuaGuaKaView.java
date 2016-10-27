package com.wxj.customview.GuaGuaKaActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-6-25.
 */
public class GuaGuaKaView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mOutPaint;

    private int mLastX;
    private int mLastY;

    //----------------------------------
    private Bitmap bitmap;


    public GuaGuaKaView(Context context) {
        this(context, null);
    }

    public GuaGuaKaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuaGuaKaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setDither(true);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutPaint.setStrokeJoin(Paint.Join.ROUND);
        mOutPaint.setStrokeWidth(20);
        mOutPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.t2);
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        //设置图层效果
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,0,0,null);
        drawPath();
        canvas.drawBitmap(mBitmap,0,0,null);
    }

    private void drawPath() {
        mOutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath,mOutPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(mLastX,mLastY);

                break;
            case MotionEvent.ACTION_MOVE:

                int dx = Math.abs(mLastX - x);
                int dy = Math.abs(mLastY - y);
                if(dx > 3 || dy > 3){
                    mPath.lineTo(x,y);
                }

                break;
        }

        invalidate();
        return true;
    }

}
