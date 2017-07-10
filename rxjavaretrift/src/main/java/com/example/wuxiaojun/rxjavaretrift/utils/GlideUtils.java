package com.example.wuxiaojun.rxjavaretrift.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wuxiaojun.rxjavaretrift.R;

/**
 * 加载图片常用工具类
 * Created by wuxiaojun on 16-11-29.
 */

public class GlideUtils {


    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.img_default)
                    .placeholder(R.mipmap.img_default)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
        }
    }

    public static void loadGIFImage(Context context, String imgUrl, ImageView imageView) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.clear(imageView);
            imageView.setImageDrawable(null);
            Glide.with(context)
                    .load(imgUrl)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.mipmap.img_default)
                    .placeholder(R.mipmap.img_default)
                    .into(imageView);
        }
    }


    public static void loadGifOrNormalImage(Context context, String imgUrl, ImageView imageView){
        if(!TextUtils.isEmpty(imgUrl)){
            if(imgUrl.endsWith("gif")){
                loadGIFImage(context,imgUrl,imageView);
            }else{
                loadImage(context, imgUrl, imageView);
            }
        }
    }

}
