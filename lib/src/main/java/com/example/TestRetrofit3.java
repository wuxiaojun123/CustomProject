package com.example;

import com.example.bean.MovieRespBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wuxiaojun on 16-11-9.
 */
public class TestRetrofit3 {

    //banner接口  list?packageName=com.idotools.browser&wzCode=1
    public static final String PATH_BANNER = "server/dacon/list";
    //动漫之家的数据接口 tagGroup.php?tag=4&order=hot&page=1&num=15
    public static final String PATH_DMZJ = "tagGroup/tagGroup.php";
    //base url
    //public static final String PATH_BASE = "http://192.168.2.59:3006/";

    public static void main(String[] args) {

        AppHttpClient client = new AppHttpClient();
        client.requestBannerPath("com.idotools.browser", 1);
//        client.requestDmzjBeanList();
    }

    public static class AppHttpClient {

        private Retrofit mRetrofit;
        private APIService mApiService;

        public AppHttpClient() {
            retrofit();
        }

        public Retrofit retrofit() {
            if (mRetrofit == null) {

                mRetrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.2.59:3006/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            if (mApiService == null) {
                mApiService = mRetrofit.create(APIService.class);
            }
            return mRetrofit;
        }

        public List<BannerBean> requestBannerPath(String packageName, int versionCode) {
            List<BannerBean> list = null;

            Call<BannerResp> bannerBeanList = mApiService.getBannerBeanList(packageName, versionCode);
            //System.out.println("结果是"+response.body().toString());
            bannerBeanList.enqueue(new Callback<BannerResp>() {
                @Override
                public void onResponse(Call<BannerResp> call, Response<BannerResp> response) {

                    System.out.println("结果是"+ response.body().data.size());
                }

                @Override
                public void onFailure(Call<BannerResp> call, Throwable t) {
                    t.printStackTrace();
                    System.out.println(t.toString());
                }
            });

            return list;
        }

        public List requestDmzjBeanList() {
            List<DmzjBean> list = null;
            mRetrofit = new Retrofit.Builder().baseUrl("http://open.dmzj.com/").addConverterFactory(GsonConverterFactory.create())
                    .build();
            mApiService = mRetrofit.create(APIService.class);
            //?tag=4&order=update&num=15
            Call<List<DmzjBean>> dmzjBeanList = mApiService.getDmzjBeanList(4,"update",15,1);
            dmzjBeanList.enqueue(new Callback<List<DmzjBean>>() {
                @Override
                public void onResponse(Call<List<DmzjBean>> call, Response<List<DmzjBean>> response) {
                    //请求成功
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            System.out.println("名称是:" + response.body().get(i).title);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<DmzjBean>> call, Throwable t) {

                }
            });

            return list;
        }

    }

    public interface APIService {

        //请求banner页的数据
        @GET(PATH_BANNER)
        Call<BannerResp> getBannerBeanList(
                @Query("packageName") String packageName,
                @Query("wzCode") int wzCode
        );


        //请求动漫之家的数据 ?tag=4&order=update&num=15
        @GET(PATH_DMZJ)
        Call<List<DmzjBean>> getDmzjBeanList(
                @Query("tag") int tag,
                @Query("order") String order,
                @Query("num") int num,
                @Query("page") int page
        );

    }

    public class BannerBean {

        public String openType;//打开类型
        public String h5url;
        public String adType;//图片类型
        public String iconPath;
        public int isgif;//是否为动态图

        public BannerBean() {
        }

        public BannerBean(String openType, String h5url, String adType, String iconPath, int isgif) {
            this.openType = openType;
            this.h5url = h5url;
            this.adType = adType;
            this.iconPath = iconPath;
            this.isgif = isgif;
        }

    }

    public class BannerData{
        public String name;
        public String code;
        public String adType;
        public List<BannerBean> cons;

        public BannerData(String name,String code,String adType,List<BannerBean> cons){
            this.name = name;
            this.code = code;
            this.adType = adType;
            this.cons = cons;
        }
    }

    public class BannerResp{
        public List<BannerData> data;
    }


    public class DmzjBean {

        public String id;
        public String title;
        public String cover;
        public String description;
        public String url;
        public String tags;

        public DmzjBean() {
        }

        public DmzjBean(String id, String title, String cover, String description, String url, String tags) {
            this.id = id;
            this.title = title;
            this.cover = cover;
            this.description = description;
            this.url = url;
            this.tags = tags;
        }

    }

}
