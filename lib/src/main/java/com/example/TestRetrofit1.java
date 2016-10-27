package com.example;

import com.example.bean.Repo;
import com.example.retrofit.APIService;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * https://inthecheesefactory.com/blog/retrofit-2.0/en
 * Created by wuxiaojun on 16-8-29.
 */
public class TestRetrofit1 {
    public static final String API_URL = "https://api.github.com";


    public static void main(String[] args) throws IOException {

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = mRetrofit.create(APIService.class);

        Call<List<Repo>> repoCall = service.loadRepo("square","retrofit");
        /*
        下面表示同步请求，需要写到一个线程中
        List<Repo> mRepo = repoCall.execute().body();
        for (int i = 0; i < mRepo.size(); i++) {
            System.out.println(""+mRepo.get(i).login);
        }*/
        /***
         * 下边为异步请求
         */
        repoCall.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    List<Repo> mRepo = response.body();
                    if(mRepo != null && !mRepo.isEmpty()){
                        for (int i = 0; i < mRepo.size(); i++) {
                            System.out.println(""+mRepo.get(i).login);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });
    }
}
