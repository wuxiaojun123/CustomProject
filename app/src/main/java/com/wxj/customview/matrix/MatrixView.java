package com.wxj.customview.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-9-18.
 */
public class MatrixView extends View {

    private Matrix mMatrix;

    private Bitmap mBitmap;

    private float povitX, povitY;

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        povitX = w / 2;
        povitY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        //对图片进行缩放，并且进行平移到中心点
        mMatrix.reset();
        mMatrix.postScale(0.5f, 0.5f);
        mMatrix.preTranslate(-povitX,-povitY);
        mMatrix.postTranslate(povitX,povitY);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }

}
