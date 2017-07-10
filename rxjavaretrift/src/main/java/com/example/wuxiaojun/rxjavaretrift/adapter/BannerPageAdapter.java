package com.example.wuxiaojun.rxjavaretrift.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by wuxiaojun on 16-11-11.
 */
public class BannerPageAdapter extends PagerAdapter {

    private List<ImageView> mImageViewList;
    private int size;

    public BannerPageAdapter(List<ImageView> mImageViewList, int size) {
        this.mImageViewList = mImageViewList;
        this.size = size;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = mImageViewList.get(position % size);
        if (iv.getParent() != null) {
            ((ViewGroup) iv.getParent()).removeView(iv);
        }
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

}
