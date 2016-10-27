package com.wxj.customview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.util.LruCache;

import java.io.IOException;

/**
 * Created by wuxiaojun on 16-8-1.
 */
public class ImageLoader {

    private static LruCache<String,Bitmap> mMemoryCache;

    private static ImageLoader mImageLoader;

    private ImageLoader(){
        //获取应用程序最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int mImageMemory = maxMemory/8;

        mMemoryCache = new LruCache<String, Bitmap>(mImageMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


    public static ImageLoader getInstance(){
        if(mImageLoader == null){
            mImageLoader = new ImageLoader();
        }
        return mImageLoader;
    }

    public void addBitmapToMemoryCache(String key,Bitmap mBitmap){
        if(getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key,mBitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    /***
     * 解析image图片
     *
     * 提醒一下：bitmapFactory.decodeStream比bitmapFactory.decodeResource性能以及时间上都要优化很多
     * 另外Drawable比bitmap在内存上要节省非常多
     *
     * @param imgPath
     * @param reqSize
     * @return
     */
    public static Bitmap decodeSampleBitmapFromResource(String imgPath,int reqSize) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//获取图片大小,并不加载到内存中
        BitmapFactory.decodeFile(imgPath,options);
        //计算缩小比例大小
        options.inSampleSize = calculateInsampleSize(options.outWidth,reqSize);
        if(options.inSampleSize == 1){
            //采用EX
            ExifInterface mExifInterface = new ExifInterface(imgPath);
            //获取图片的宽度
            int imgWidth = mExifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH,0);
            if(imgWidth != 0){
                //继续计算
                options.inSampleSize = calculateInsampleSize(imgWidth,reqSize);
            }
        }
        //将图片加载到内存中
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imgPath,options);
    }

    public static int calculateInsampleSize(int imageWidth,int reqWidth){
        int insampleSize = 1;
        //如果图片宽度大于控件宽度，那么需要计算压缩比例
        if(imageWidth > reqWidth){
            insampleSize = Math.round(imageWidth/reqWidth);
        }
        return insampleSize;
    }

}
