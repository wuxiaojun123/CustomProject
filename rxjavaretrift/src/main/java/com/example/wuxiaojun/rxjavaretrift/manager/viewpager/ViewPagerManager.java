package com.example.wuxiaojun.rxjavaretrift.manager.viewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.wuxiaojun.rxjavaretrift.R;

import com.example.wuxiaojun.rxjavaretrift.adapter.BannerPageAdapter;
import com.example.wuxiaojun.rxjavaretrift.bean.BannerResp;
import com.example.wuxiaojun.rxjavaretrift.minterface.OnItemClickListener;
import com.example.wuxiaojun.rxjavaretrift.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager的管理类
 * <p>
 * Created by wuxiaojun on 16-11-29.
 */

public class ViewPagerManager {

    private Context mContext;
    private int currentPosition;
    private List<ImageView> mImageViewList;
    private ImageView[] indicators;// 小圆点数组
    public List<BannerResp.BannerBean> mBannerBeanList;
    private ViewPager viewPager;
    private LinearLayout dotView;
    private ImageView id_iv_one;//只有一张图片的情况，显示
    private OnItemClickListener mOnItemClickListener;

    public ViewPagerManager(Context context, ViewPager viewPager,
                            LinearLayout dotView, ImageView id_iv_one,
                            List<BannerResp.BannerBean> mBannerBeanList,
                            OnItemClickListener mOnItemClickListener) {
        this.mContext = context;
        this.viewPager = viewPager;
        this.dotView = dotView;
        this.id_iv_one = id_iv_one;
        this.mBannerBeanList = mBannerBeanList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /***
     * 初始化
     */
    public void initViewPager() {
        if (mBannerBeanList == null || mBannerBeanList.isEmpty()) {
            return;
        }
        final int size = mBannerBeanList.size();
        if (size == 1) {
            if (id_iv_one.getVisibility() == View.VISIBLE) {
                return;
            }
            id_iv_one.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
            //只需要显示imageview一张图片即可
            GlideUtils.loadGifOrNormalImage(mContext, mBannerBeanList.get(0).iconPath, id_iv_one);
            setOnClickListener(id_iv_one, mBannerBeanList.get(0).h5url);
        } else {
            if (viewPager.getAdapter() != null) {
                return;
            }
            mImageViewList = new ArrayList<>();
            indicators = new ImageView[mBannerBeanList.size()];

            for (int i = 0; i < size; i++) {
                ImageView iv = new ImageView(mContext);
                GlideUtils.loadGifOrNormalImage(mContext, mBannerBeanList.get(i).iconPath, iv);

                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageViewList.add(iv);

                ImageView ivIndicator = new ImageView(mContext);
                if (i != 0) {
                    ivIndicator.setImageResource(R.mipmap.choose_false);
                } else {
                    ivIndicator.setImageResource(R.mipmap.choose_true);
                }
                ivIndicator.setPadding(0, 0, 20, 0);
                dotView.addView(ivIndicator);
                indicators[i] = ivIndicator;
            }

            // 如果图片集合等于2，那么添加两倍图片,防止左滑动出现空白页，自行优化是一张图片的时候就不让左右滑动和轮播
            if (size == 2) {
                for (int i = size; i < size * 2; i++) {
                    ImageView iv = new ImageView(mContext);
                    GlideUtils.loadGifOrNormalImage(mContext, mBannerBeanList.get(i % size).iconPath, iv);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    mImageViewList.add(iv);
                }
            }
            setEvent(size);
            startViewPager(size);
        }
    }

    /***
     * 设置事件
     *
     * @param size
     */
    private void setEvent(final int size) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                //设置小圆点
                setCurrentIndicator(arg0 % size);
                currentPosition = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        BannerPageAdapter adapter = new BannerPageAdapter(mImageViewList, mImageViewList.size());
        viewPager.setAdapter(adapter);
    }

    /***
     * 开始循环动画
     *
     * @param size
     */
    public void startViewPager(int size) {
        if (size >= 2) {
            viewPager.setCurrentItem(5000);
            mHandler.sendEmptyMessageDelayed(1, 3000);
        }
        if (mImageViewList != null && !mImageViewList.isEmpty()) {
            for (int i = 0; i < mImageViewList.size(); i++) {
                setOnClickListener(mImageViewList.get(i), mBannerBeanList.get(i % size).h5url);
            }
        }
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                currentPosition++;
                viewPager.setCurrentItem(currentPosition);
            }
            mHandler.sendEmptyMessageDelayed(1, 3000);
        }
    };

    /***
     * 设置选中小圆点
     */
    protected void setCurrentIndicator(int currentPosition) {
        for (int i = 0; i < indicators.length; i++) {
            if (i != currentPosition) {
                indicators[i].setImageResource(R.mipmap.choose_false);
            } else {
                indicators[i].setImageResource(R.mipmap.choose_true);
            }
        }
    }

    /***
     * 设置点击事件
     *
     * @param imageView 显示图片控件
     * @param url       需要传递的参数
     */
    private void setOnClickListener(ImageView imageView, final String url) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(url,null,null);
                }
            }
        });
    }

    /***
     * 销毁handler和其他资源文件
     */
    public void destroyViewPager() {
        if (mHandler != null) {
            mHandler.removeMessages(1);
            mHandler = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }

}
