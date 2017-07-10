package com.example.wuxiaojun.rxjavaretrift.manager.http;


import com.example.wuxiaojun.rxjavaretrift.bean.BannerResp;
import com.example.wuxiaojun.rxjavaretrift.bean.DmzjBean;
import com.example.wuxiaojun.rxjavaretrift.minterface.APIService;
import com.example.wuxiaojun.rxjavaretrift.utils.Constant;
import com.example.wuxiaojun.rxjavaretrift.utils.LogUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public class AppHttpClient {


    /***
     * 加载首页banner数据
     *
     * @param packageName
     * @param versionCode
     */
    public void requestBannerPath(String packageName, int versionCode) {
        Retrofit mRetrofit = new Retrofit
                .Builder()
                .baseUrl(Constant.PATH_BASE_BANNER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIService apiService = mRetrofit.create(APIService.class);
        apiService
                .getBannerBeanList(packageName, "gif001")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BannerResp>() {
                    @Override
                    public void call(BannerResp bannerResp) {
                        List<BannerResp.BannerBean> cons = null;
                        if(bannerResp != null){
                            List<BannerResp.BannerData> data = bannerResp.data;
                            if (data != null && !data.isEmpty()) {
                                cons = data.get(0).cons;
                            }
                        }
                        loadBannerDataFailedListener(cons, true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e("call(throwable)");
                    }
                });

        /*Call<BannerResp> bannerBeanList1 = apiService.getBannerBeanList(packageName, "gif001");
        //异步请求网络信息
        bannerBeanList1.enqueue(new Callback<BannerResp>() {
            @Override
            public void onResponse(Call<BannerResp> call, Response<BannerResp> response) {
                List<BannerResp.BannerBean> cons = null;
                if (response.isSuccessful()) {
                    List<BannerResp.BannerData> data = response.body().data;
                    if (data != null && !data.isEmpty()) {
                        cons = data.get(0).cons;
                    }
                }
                loadBannerDataFailedListener(cons, true);
            }

            @Override
            public void onFailure(Call<BannerResp> call, Throwable t) {
                t.printStackTrace();
                loadBannerDataFailedListener(null, false);
            }
        });*/
    }

    private OnLoadBannerDataListener mOnLoadBannerDataListener;

    public void setOnLoadBannerDataListener(OnLoadBannerDataListener listener) {
        this.mOnLoadBannerDataListener = listener;
    }

    private void loadBannerDataFailedListener(List<BannerResp.BannerBean> cons, boolean result) {
        if (mOnLoadBannerDataListener != null) {
            if (result)
                mOnLoadBannerDataListener.loadBannerDataSuccessListener(cons);
            else
                mOnLoadBannerDataListener.loadBannerDataFailedListener();
        }
    }

    /***
     * 加载banner数据
     */
    public interface OnLoadBannerDataListener {
        /***
         * 获取数据成功，list为返回数据
         *
         * @param list
         */
        void loadBannerDataSuccessListener(List<BannerResp.BannerBean> list);

        /***
         * 加载数据失败
         */
        void loadBannerDataFailedListener();
    }


    /***
     * 加载wordpress的数据
     */
    public void requestDmzjBeanList(String cagetory, String slug, int page, int num) {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.PATH_WORDPRESS_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIService apiService = mRetrofit.create(APIService.class);
        //update 表示最新，hot表示最热
        apiService
                .getDmzjBeanList(cagetory, slug, page, num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DmzjBean>() {
                    @Override
                    public void call(DmzjBean dmzjBean) {
                        loadDmzhDataFailedListener(dmzjBean, true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadDmzhDataFailedListener(null, false);
                    }
                });
        /*Call<DmzjBean> dmzjBeanList = apiService.getDmzjBeanList(cagetory,slug, page, num);
        dmzjBeanList.enqueue(new Callback<DmzjBean>() {
            @Override
            public void onResponse(Call<DmzjBean> call, Response<DmzjBean> response) {
                if(response.isSuccessful()){
                    loadDmzhDataFailedListener(response.body(),true);
                }
            }

            @Override
            public void onFailure(Call<DmzjBean> call, Throwable t) {
                t.printStackTrace();
                loadDmzhDataFailedListener(null, false);
            }
        });*/
    }

    private OnLoadDmzjDataListener mOnLoadDmzjDataListener;

    public void setOnLoadDmzjDataListener(OnLoadDmzjDataListener listener) {
        this.mOnLoadDmzjDataListener = listener;
    }

    private void loadDmzhDataFailedListener(DmzjBean cons, boolean result) {
        if (mOnLoadDmzjDataListener != null) {
            if (result)
                mOnLoadDmzjDataListener.loadDmzjDataSuccessListener(cons);
            else
                mOnLoadDmzjDataListener.loadDmzjDataFailedListener();
        }
    }

    public interface OnLoadDmzjDataListener {
        /***
         * 获取数据成功，list为返回数据
         *
         * @param bean
         */
        void loadDmzjDataSuccessListener(DmzjBean bean);

        /***
         * 加载数据失败
         */
        void loadDmzjDataFailedListener();
    }


}
