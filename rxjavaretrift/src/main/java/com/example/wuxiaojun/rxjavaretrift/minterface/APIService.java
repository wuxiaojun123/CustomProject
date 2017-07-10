package com.example.wuxiaojun.rxjavaretrift.minterface;


import com.example.wuxiaojun.rxjavaretrift.bean.BannerResp;
import com.example.wuxiaojun.rxjavaretrift.bean.DmzjBean;
import com.example.wuxiaojun.rxjavaretrift.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public interface APIService {

    //请求banner页的数据
    @GET(Constant.PATH_BANNER)
    Observable<BannerResp> getBannerBeanList(
            @Query("packageName") String packageName,
            @Query("wzCode") String wzCode
    );


    //请求动漫之家的数据
    @GET(Constant.PATH_WORDPRESS_BASE)
    Observable<DmzjBean> getDmzjBeanList(
            @Query("json") String json,
            @Query("slug") String slug,
            @Query("page") int page,
            @Query("count") int count
    );

}
