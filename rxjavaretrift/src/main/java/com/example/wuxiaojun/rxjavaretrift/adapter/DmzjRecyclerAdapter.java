package com.example.wuxiaojun.rxjavaretrift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.DmzjViewHolderType1;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.DmzjViewHolderType2;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.DmzjViewHolderType3;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.DmzjViewHolderType4;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.DmzjViewHolderTypeAdmob;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.FooterViewHolder;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.Header2ViewHolder;
import com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder.HeaderViewHolder;
import com.example.wuxiaojun.rxjavaretrift.bean.BannerResp;
import com.example.wuxiaojun.rxjavaretrift.bean.DmzjBean;
import com.example.wuxiaojun.rxjavaretrift.manager.viewpager.ViewPagerManager;
import com.example.wuxiaojun.rxjavaretrift.minterface.OnItemClickListener;
import com.example.wuxiaojun.rxjavaretrift.utils.Constant;
import com.example.wuxiaojun.rxjavaretrift.utils.GlideUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.media.CamcorderProfile.get;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public class DmzjRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //头部view集合
    private ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    private static final int VIEW_TYPE_NORMAL = 10000;//表示当前view类型为正常viewType
    private static final int VIEW_TYPE_FOOTER = 10001;//表示当前view类型是footerView
    private static final int VIEW_TYPE_1 = 10002;//当前view类型是10002
    private static final int VIEW_TYPE_2 = 10003;//当前类型是10003
    private static final int VIEW_TYPE_3 = 10004;//当前类型是10004
    private static final int VIEW_TYPE_4 = 10005;//当前类型是10005
    private static final int VIEW_TYPE_ADMOB = 19999;//当前类型是10005

    public static final int LOAD_MORE_LOADING = 0;//正在加载
    public static final int LOAD_MORE_COMPILE = 1;//加载完成
    //    public List<DmzjBean.PostsBean> mHeader2List;//第二个头部布局的数据集合

    public Context mContext;
    private View footerView;//加载更多布局
    private int status_add_more;//加载更多状态
    public List<DmzjBean.PostsBean> mList;//数据集合
    public static final int LOAD_MORE_NO = 2;//没有更多
    public ViewPagerManager mViewPagerManager;//viewpager轮播图片管理类
    private OnItemClickListener mOnItemClickListener;
    public List<BannerResp.BannerBean> mBannerBeanList;//viewpager图片的集合

    private int admobCount = 0;//广告个数

    private LayoutInflater inflater;

    public DmzjRecyclerAdapter(Context context, List<DmzjBean.PostsBean> list) {
        inflater = LayoutInflater.from(context);
        mHeaderViewInfos.add(new FixedViewInfo(inflater.inflate(R.layout.item_dmzj_header, null), 0));
        mHeaderViewInfos.add(new FixedViewInfo(inflater.inflate(R.layout.item_dmzj_header2, null), 1));
        this.mContext = context.getApplicationContext();
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeader(viewType)) {
            int headerPosition = getHeaderPositionByViewType(viewType);
            if (headerPosition == 0) {
                return new HeaderViewHolder(mHeaderViewInfos.get(0).view);
            } else if (headerPosition == 1) {
                return new Header2ViewHolder(mHeaderViewInfos.get(1).view);
            }
        } else if (VIEW_TYPE_1 == viewType) {
            return new DmzjViewHolderType1(inflater.inflate(R.layout.item_dmzj_type1, null));
        } else if (VIEW_TYPE_2 == viewType) {
            return new DmzjViewHolderType2(inflater.inflate(R.layout.item_dmzj_type2, null));
        } else if (VIEW_TYPE_3 == viewType) {
            return new DmzjViewHolderType3(inflater.inflate(R.layout.item_dmzj_type3, null));
        } else if (VIEW_TYPE_4 == viewType) {
            return new DmzjViewHolderType4(inflater.inflate(R.layout.item_dmzj_type4, null));
        } else if (VIEW_TYPE_ADMOB == viewType) {
            return new DmzjViewHolderTypeAdmob(inflater.inflate(R.layout.item_dmzj_native_ad, null));
        }
        return new FooterViewHolder(footerView);
    }

    /*private HashMap<String, NativeAd> nativeAdHashMap = new HashMap<>();
    private HashMap<String, List<View>> mNativeClickViewMap = new HashMap<>();
    private HashMap<String, AdChoicesView> mNativeAdChoicesMap = new HashMap<>();*/


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= 2) {
            if (position < (getItemCount() - 1)) {//判断是否需要显示底部加载更多布局
                int currentPosition = position - mHeaderViewInfos.size() + 3;
                if (currentPosition >= mList.size()) {
                    return;
                }
                DmzjBean.PostsBean bean = mList.get(currentPosition);
                if (bean != null) {
                    List<DmzjBean.Tags> tags = bean.tags;
                    if (tags != null && !tags.isEmpty()) {
                        String slug = tags.get(0).slug;
                        if (Constant.ITEM_TYPE1.equals(slug)) {
                            DmzjViewHolderType1 dmzjViewHolder = (DmzjViewHolderType1) holder;
                            initViewHolderType1(dmzjViewHolder, currentPosition);

                        } else if (Constant.ITEM_TYPE2.equals(slug)) {
                            DmzjViewHolderType2 dmzjViewHolder = (DmzjViewHolderType2) holder;
                            initViewHolderType2(dmzjViewHolder, currentPosition);

                        } else if (Constant.ITEM_TYPE3.equals(slug)) {
                            initViewHolderType3((DmzjViewHolderType3) holder, currentPosition);

                        } else if (Constant.ITEM_TYPE4.equals(slug)) {
                            DmzjViewHolderType4 dmzjViewHolder = (DmzjViewHolderType4) holder;
                            initViewHolderType4(dmzjViewHolder, currentPosition);
                        }
                    }
                } else {//加载广告,需要把没一个加载过的广告放到hashmap中，然后再去取每一条广告
                    /*final DmzjViewHolderTypeAdmob dmzjViewHolder = (DmzjViewHolderTypeAdmob) holder;

                    NativeAd mNativeAd = nativeAdHashMap.get(currentPosition + "");
                    if (mNativeAd == null) {//实例化广告
                        AdSettings.addTestDevice("a39e667da1364b38f973c6ca8120623d");
                        mNativeAd = new NativeAd(mContext, Constant.FACEBOOK_PLACEMENT_ID);
                        mNativeAd.setAdListener(new NativeAdListener(dmzjViewHolder, mNativeAd, currentPosition + ""));
                        // Request an ad
                        mNativeAd.loadAd();
                        nativeAdHashMap.put(currentPosition + "", mNativeAd);
                        LogUtils.e("新加载广告");
                    } else {
                        //设置view上的内容
                        LogUtils.e("使用缓存广告");
                        setNativeAdView(dmzjViewHolder, mNativeAd, currentPosition + "");
                    }*/
                }
            } else {
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                switch (status_add_more) {
                    case LOAD_MORE_LOADING:
                        footerViewHolder.progressBar.showNow();
                        footerViewHolder.id_ll_footer.setVisibility(View.VISIBLE);
                        break;
                    case LOAD_MORE_COMPILE:
                        footerViewHolder.progressBar.hideNow();
                        footerViewHolder.id_ll_footer.setVisibility(View.GONE);
                        break;
                    case LOAD_MORE_NO:
                        footerViewHolder.progressBar.hideNow();
                        footerViewHolder.id_ll_footer.setVisibility(View.GONE);
                        break;
                }
            }
        } else if (position == 0) {//第一个头部布局
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            if (mViewPagerManager == null) {
                mViewPagerManager = new ViewPagerManager(mContext,
                        headerViewHolder.id_viewpager, headerViewHolder.id_ll_dot,
                        headerViewHolder.id_iv_one, mBannerBeanList, mOnItemClickListener);
            }
            mViewPagerManager.initViewPager();

        } else if (position == 1) {//第二个头部布局
            if (mList == null || mList.isEmpty()) {
                return;
            }
            initHeader2ViewHolder(holder);
        }

    }

    /*private void setNativeAdView(DmzjViewHolderTypeAdmob dmzjViewHolder, NativeAd mNativeAd, String currentPosition) {
        // Set the Text.
        dmzjViewHolder.nativeAdTitle.setText(mNativeAd.getAdTitle());
        dmzjViewHolder.nativeAdBody.setText(mNativeAd.getAdBody());
        dmzjViewHolder.nativeAdCallToAction.setText(mNativeAd.getAdCallToAction());
        dmzjViewHolder.nativeAdMedia.setNativeAd(mNativeAd);
        AdChoicesView adChoicesView = mNativeAdChoicesMap.get(currentPosition);
        if (adChoicesView != null) {
            if (adChoicesView.getParent() != null) {
                ((ViewGroup) adChoicesView.getParent()).removeView(adChoicesView);
            }
            dmzjViewHolder.adChoicesContainer.addView(adChoicesView);
        }

        // Register the Title and CTA button to listen for clicks.
        List<View> clickableViews = mNativeClickViewMap.get(currentPosition);
        if (clickableViews != null) {
            clickableViews.add(dmzjViewHolder.nativeAdTitle);
            clickableViews.add(dmzjViewHolder.nativeAdCallToAction);
            mNativeAd.registerViewForInteraction(dmzjViewHolder.native_ad_unit, clickableViews);
        }
    }*/

    /***
     * 广告回调内部类
     */
    /*private class NativeAdListener implements AdListener {

        DmzjViewHolderTypeAdmob dmzjViewHolder;
        NativeAd mNativeAd;
        String currentPosition;

        public NativeAdListener(DmzjViewHolderTypeAdmob dmzjViewHolder, NativeAd mNativeAd, String currentPosition) {
            this.dmzjViewHolder = dmzjViewHolder;
            this.mNativeAd = mNativeAd;
            this.currentPosition = currentPosition;
        }

        @Override
        public void onError(Ad ad, AdError error) {
        }

        @Override
        public void onAdLoaded(Ad ad) {
            // Set the Text.
            dmzjViewHolder.nativeAdTitle.setText(mNativeAd.getAdTitle());
            dmzjViewHolder.nativeAdBody.setText(mNativeAd.getAdBody());
            dmzjViewHolder.nativeAdCallToAction.setText(mNativeAd.getAdCallToAction());
            dmzjViewHolder.nativeAdMedia.setNativeAd(mNativeAd);
            AdChoicesView adChoicesView = new AdChoicesView(mContext, mNativeAd, true);
            dmzjViewHolder.adChoicesContainer.addView(adChoicesView);
            mNativeAdChoicesMap.put(currentPosition, adChoicesView);

            // Register the Title and CTA button to listen for clicks.
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(dmzjViewHolder.nativeAdTitle);
            clickableViews.add(dmzjViewHolder.nativeAdCallToAction);
            mNativeAd.registerViewForInteraction(dmzjViewHolder.native_ad_unit, clickableViews);
            mNativeClickViewMap.put(currentPosition, clickableViews);
        }

        @Override
        public void onAdClicked(Ad ad) {
            // Ad clicked callback
        }
    }*/

    /***
     * 实例化viewHolderType4控件中的数据
     *
     * @param dmzjViewHolder
     * @param currentPosition
     */
    private void initViewHolderType4(DmzjViewHolderType4 dmzjViewHolder, int currentPosition) {
        final DmzjBean.PostsBean bean = mList.get(currentPosition);
        if (bean != null) {
            //设置信息
            dmzjViewHolder.id_tv_title.setText(Html.fromHtml(bean.title));
            dmzjViewHolder.id_tv_tag.setText(bean.categories.get(0).title);
            if (TextUtils.isEmpty(bean.content)) {
                dmzjViewHolder.id_tv_description.setVisibility(View.GONE);
            } else {
                dmzjViewHolder.id_tv_description.setVisibility(View.VISIBLE);
                Document parse = Jsoup.parse(bean.content);
                String text = parse.text();
                if (!TextUtils.isEmpty(text)) {
                    dmzjViewHolder.id_tv_description.setText(text);
                }
            }
            setOnClickListener(dmzjViewHolder.id_rl_item, bean.url, bean.thumbnail, bean.title);
        }
    }

    /**
     * 实例化viewHolderType3控件中的数据
     *
     * @param holder
     * @param currentPosition
     */
    private void initViewHolderType3(DmzjViewHolderType3 holder, int currentPosition) {
        DmzjViewHolderType3 dmzjViewHolder = holder;
        final DmzjBean.PostsBean bean = mList.get(currentPosition);
        //解析content中的html代码获取img的路径
        Document parse = Jsoup.parse(bean.content);
        dmzjViewHolder.id_tv_title.setText(Html.fromHtml(bean.title));
        dmzjViewHolder.id_tv_tag.setText(bean.categories.get(0).title);
        //解析img属性
        Elements imgElements = parse.select("img");
        int size = imgElements.size();//获取img的长度
        if (size >= 3) {
            GlideUtils.loadGIFImage(mContext, imgElements.get(0).attr("src"), dmzjViewHolder.id_iv_img_left);
            GlideUtils.loadGIFImage(mContext, imgElements.get(1).attr("src"), dmzjViewHolder.id_iv_img_center);
            GlideUtils.loadGIFImage(mContext, imgElements.get(2).attr("src"), dmzjViewHolder.id_iv_img_right);
        }
        setOnClickListener(dmzjViewHolder.id_ll_item, bean.url, imgElements.get(0).attr("src"), bean.title);
    }

    /***
     * 实例化viewHolderType2控件中的数据
     *
     * @param dmzjViewHolder
     * @param currentPosition
     */
    private void initViewHolderType2(DmzjViewHolderType2 dmzjViewHolder, int currentPosition) {
        final DmzjBean.PostsBean bean = mList.get(currentPosition);
        if (bean != null) {
            //设置图片 android:background="@mipmap/img_default"
            GlideUtils.loadGIFImage(mContext, bean.thumbnail, dmzjViewHolder.id_iv_img);
            //设置信息
            dmzjViewHolder.id_tv_title.setText(Html.fromHtml(bean.title));
            dmzjViewHolder.id_tv_tag.setText(bean.categories.get(0).title);
            setOnClickListener(dmzjViewHolder.id_ll_item, bean.url, bean.thumbnail, bean.title);
        }
    }

    /***
     * 实例化viewHolderType1控件中的数据
     *
     * @param dmzjViewHolder
     * @param currentPosition
     */
    private void initViewHolderType1(DmzjViewHolderType1 dmzjViewHolder, int currentPosition) {
        final DmzjBean.PostsBean bean = mList.get(currentPosition);
        if (bean != null) {
            //设置图片 android:background="@mipmap/img_default"
            GlideUtils.loadGIFImage(mContext, bean.thumbnail, dmzjViewHolder.id_iv_img);
            //设置信息
            dmzjViewHolder.id_tv_title.setText(Html.fromHtml(bean.title));
            dmzjViewHolder.id_tv_tag.setText(bean.categories.get(0).title);
            if (TextUtils.isEmpty(bean.content)) {
                dmzjViewHolder.id_tv_description.setVisibility(View.GONE);
            } else {
                dmzjViewHolder.id_tv_description.setVisibility(View.VISIBLE);
                Document parse = Jsoup.parse(bean.content);
                dmzjViewHolder.id_tv_description.setText(parse.text());
            }
            setOnClickListener(dmzjViewHolder.id_ll_item, bean.url, bean.thumbnail, bean.title);
        }
    }


    /***
     * 给view设置点击事件
     *
     * @param view
     * @param url
     */
    private void setOnClickListener(View view, final String url, final String imgUrl, final String title) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(url, imgUrl, title);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() - 2 + mHeaderViewInfos.size() + admobCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViewInfos.get(position).viewType;
        } else if (isFooterPosition(position)) {
            return VIEW_TYPE_FOOTER;
        } else {
            return judgmentType(position);
        }
    }

    /***
     * 根据position获取当前显示的类型
     *
     * @param position
     * @return
     */
    private int judgmentType(int position) {
        if (mList != null && !mList.isEmpty()) {
            int currentPosition = position - mHeaderViewInfos.size() + 3;
            DmzjBean.PostsBean bean = mList.get(currentPosition);
            if (bean != null) {
                List<DmzjBean.Tags> tags = bean.tags;
                if (tags != null && !tags.isEmpty()) {
                    String slug = tags.get(0).slug;
                    if (Constant.ITEM_TYPE1.equals(slug)) {
                        return VIEW_TYPE_1;
                    } else if (Constant.ITEM_TYPE2.equals(slug)) {
                        return VIEW_TYPE_2;
                    } else if (Constant.ITEM_TYPE3.equals(slug)) {
                        return VIEW_TYPE_3;
                    } else if (Constant.ITEM_TYPE4.equals(slug)) {
                        return VIEW_TYPE_4;
                    }
                }
            } else {
                return VIEW_TYPE_ADMOB;
            }
        }
        return VIEW_TYPE_1;
    }

    /***
     * 初始化头部布局样式2
     *
     * @param holder
     */
    private void initHeader2ViewHolder(RecyclerView.ViewHolder holder) {
        Header2ViewHolder header2ViewHolder = (Header2ViewHolder) holder;
        DmzjBean.PostsBean bean1 = mList.get(0);
        if (bean1 != null) {
            initHeader2Content(header2ViewHolder.id_tv_text_first, header2ViewHolder.id_iv_image_first, bean1.thumbnail, bean1.title, bean1.url);
        }
        DmzjBean.PostsBean bean2 = mList.get(1);
        if (bean2 != null) {
            initHeader2Content(header2ViewHolder.id_tv_text_second, header2ViewHolder.id_iv_image_second, bean2.thumbnail, bean2.title, bean2.url);
        }
        DmzjBean.PostsBean bean3 = mList.get(2);
        if (bean3 != null) {
            initHeader2Content(header2ViewHolder.id_tv_text_third, header2ViewHolder.id_iv_image_third, bean3.thumbnail, bean3.title, bean3.url);
        }
    }

    /***
     * 初始化头部布局样式2的控件内容
     */
    private void initHeader2Content(TextView textview, ImageView imageview, String imgUrl, String title, String url) {
        GlideUtils.loadGIFImage(mContext, imgUrl, imageview);
        textview.setText(Html.fromHtml(title));
        setOnClickListener(imageview, url, imgUrl, title);
    }

    /***
     * 添加更多
     *
     * @param list
     */
    public void addMoreItem(List<DmzjBean.PostsBean> list, int status) {
        this.mList.addAll(list);
        this.status_add_more = status;
        notifyDataSetChanged();
    }

    /***
     * 重置数据
     *
     * @param list
     */
    public void resetAdapter(List<DmzjBean.PostsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 设置广告个数
     *
     * @param admobCount
     */
    public void setAdmobCount(int admobCount) {
        this.admobCount = admobCount;
    }

    /**
     * 修改加载更多状态
     */
    public void changeAddMoreStatus(int status) {
        this.status_add_more = status;
        notifyDataSetChanged();
    }

    /***
     * 根据viewType获取position
     *
     * @param viewType
     * @return
     */
    private int getHeaderPositionByViewType(int viewType) {
        int size = mHeaderViewInfos.size();
        for (int i = 0; i < size; i++) {
            if (viewType == mHeaderViewInfos.get(i).viewType) {
                return i;
            }
        }
        return -1;
    }

    /***
     * 判断当前类型是否为头部
     *
     * @param viewType
     * @return
     */
    private boolean isHeader(int viewType) {
        for (FixedViewInfo mInfo : mHeaderViewInfos) {
            if (mInfo.viewType == viewType) {
                return true;
            }
        }
        return false;
    }

    /***
     * 判断是否为头部位置
     *
     * @param position
     * @return
     */
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViewInfos.size();
    }

    /***
     * 根据viewType是否为footerView
     *
     * @param viewType
     * @return
     */
    private boolean isFooter(int viewType) {
        if (viewType == VIEW_TYPE_FOOTER) {
            return true;
        }
        return false;
    }

    /***
     * 判断是否为加载更多view类型
     *
     * @param position
     * @return
     */
    private boolean isFooterPosition(int position) {
        if (position >= (getItemCount() - 1)) {
            return true;
        }
        return false;
    }

    /***
     * 设置尾部view
     *
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /***
     * 存储头部和尾部的信息
     */
    public class FixedViewInfo {

        public View view;
        public int viewType;

        public FixedViewInfo(View view, int viewType) {
            this.view = view;
            this.viewType = viewType;
        }
    }

}
