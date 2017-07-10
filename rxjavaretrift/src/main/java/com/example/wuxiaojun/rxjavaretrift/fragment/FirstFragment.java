package com.example.wuxiaojun.rxjavaretrift.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;
import com.example.wuxiaojun.rxjavaretrift.adapter.DmzjRecyclerAdapter;
import com.example.wuxiaojun.rxjavaretrift.bean.BannerResp;
import com.example.wuxiaojun.rxjavaretrift.bean.DmzjBean;
import com.example.wuxiaojun.rxjavaretrift.manager.http.AppHttpClient;
import com.example.wuxiaojun.rxjavaretrift.minterface.OnItemClickListener;
import com.example.wuxiaojun.rxjavaretrift.utils.Constant;
import com.example.wuxiaojun.rxjavaretrift.utils.JudgeNetWork;
import com.example.wuxiaojun.rxjavaretrift.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-8-31.
 */
public class FirstFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private View view;
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private DmzjRecyclerAdapter mDmzjAdapter;
    private List<DmzjBean.PostsBean> mDmzjBeanList;
    private int lastVisiblePosition;
    private AppHttpClient mAppHttpClient;//网络请求业务类
    private int page = 1;//当前页
    private int totalPage;//总页数

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_first, null);
            LogUtils.e("view为null");
        } else {
            LogUtils.e("view不为null");
        }
        ButterKnife.bind(this, view);
        initView();
        initRecycler();
        initData();
        loadMore();

        return view;
    }


    private void initView() {
        swiperefreshLayout.setOnRefreshListener(this);
        swiperefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swiperefreshLayout.setRefreshing(true);
        //onRefresh();
    }

    /***
     * 添加滑动事件的监听，以便可以出现加载更多
     */
    private void loadMore() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisiblePosition + 1) == mDmzjAdapter.getItemCount()) {
                    if (JudgeNetWork.isNetAvailable(mContext)) {
                        page += 1;
                        if (page <= totalPage) {
                            loadLatestData(page, true);
                            mDmzjAdapter.changeAddMoreStatus(DmzjRecyclerAdapter.LOAD_MORE_LOADING);
                        }
                    } else {
                        mDmzjAdapter.changeAddMoreStatus(DmzjRecyclerAdapter.LOAD_MORE_COMPILE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisiblePosition = mLinearLayoutManager.findLastVisibleItemPosition();
                /*if (lastVisiblePosition > 6) {
                    id_return_to_top.setVisibility(View.VISIBLE);
                } else {
                    id_return_to_top.setVisibility(View.GONE);
                }*/
            }
        });
    }

    /**
     * 初始化recyclerView
     */
    private void initRecycler() {
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mDmzjBeanList = new ArrayList<>();
        mDmzjAdapter = new DmzjRecyclerAdapter(getActivity(), mDmzjBeanList);
        mDmzjAdapter.setFooterView(LayoutInflater.from(mContext).inflate(R.layout.layout_footer, recyclerView, false));
        recyclerView.setAdapter(mDmzjAdapter);
        recyclerView.setHasFixedSize(true);
        mDmzjAdapter.setOnItemClickListener(this);
    }

    /***
     * 加载数据
     */
    private void initData() {
        mAppHttpClient = new AppHttpClient();
        if (JudgeNetWork.isNetAvailable(mContext)) {
            swiperefreshLayout.setRefreshing(true);
            LogUtils.e("mAppHttpClient=" + mAppHttpClient);
            loadLatestData(page, false);
            loadBannerData();
        } else {
        }
    }


    /***
     * 加载banner数据
     */
    private void loadBannerData() {
        mAppHttpClient.requestBannerPath("com.idotools.browser.gp", 1);
        mAppHttpClient.setOnLoadBannerDataListener(new AppHttpClient.OnLoadBannerDataListener() {
            @Override
            public void loadBannerDataSuccessListener(List<BannerResp.BannerBean> list) {
                if (list != null && !list.isEmpty()) {
                    mDmzjAdapter.mBannerBeanList = list;
                    mDmzjAdapter.notifyDataSetChanged();
                    //将最新数据保存到file中
//                    FileUtils.saveFile(mContext, Constant.FILE_BANNER, JsonUtils.toJsonFromList(list));
                }
            }

            @Override
            public void loadBannerDataFailedListener() {
                if (mDmzjAdapter != null) {
                    mDmzjAdapter.changeAddMoreStatus(DmzjRecyclerAdapter.LOAD_MORE_COMPILE);
                }
            }
        });
    }

    /***
     * 拉取最新的动漫之家的数据
     */
    private void loadLatestData(int mpage, final boolean flag) {
        mAppHttpClient.requestDmzjBeanList("get_category_posts", "mgif", mpage, 7);
        mAppHttpClient.setOnLoadDmzjDataListener(new AppHttpClient.OnLoadDmzjDataListener() {

            @Override
            public void loadDmzjDataSuccessListener(DmzjBean bean) {
                totalPage = bean.pages;
                if (flag) {//加载更多
                    if (bean.posts.size() / 5 >= 1) {
                        bean.posts.add(5, null);
                    }
                    mDmzjAdapter.addMoreItem(bean.posts, DmzjRecyclerAdapter.LOAD_MORE_COMPILE);

                } else {//拉取最新
//                    FileUtils.saveFile(mContext, Constant.FILE_MAIN_DATA, JsonUtils.toJsonFromList(bean.posts));
                    bean.posts.add(null);
                    mDmzjAdapter.resetAdapter(bean.posts);//刷新界面数据
                    swiperefreshLayout.setRefreshing(false);//刷新完成
                }
            }

            @Override
            public void loadDmzjDataFailedListener() {
                swiperefreshLayout.setRefreshing(false);//刷新完成
                if (flag)
                    mDmzjAdapter.changeAddMoreStatus(DmzjRecyclerAdapter.LOAD_MORE_COMPILE);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (JudgeNetWork.isNetAvailable(mContext)) {

            page = 1;
            loadLatestData(page, false);
            if (mDmzjAdapter != null && mDmzjAdapter.mBannerBeanList == null) {
                loadBannerData();
            }
        } else {
            swiperefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("onPause");
        if (swiperefreshLayout.isRefreshing()) {
            swiperefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onItemClickListener(String url, String imgUrl, String title) {

    }
}
