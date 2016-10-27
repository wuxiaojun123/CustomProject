package com.example;

import com.example.bean.MovieRespBase;
import com.example.retrofit.MovieService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wuxiaojun on 16-8-29.
 */
public class TestRetrofit2 {

    public static final String URL = "http://api.douban.com";

    public static void main(String[] args){

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService service = mRetrofit.create(MovieService.class);

        Call<MovieRespBase> movieRespBaseCall = service.loadMovieData(5,20);
        movieRespBaseCall.enqueue(new Callback<MovieRespBase>() {
            @Override
            public void onResponse(Call<MovieRespBase> call, Response<MovieRespBase> response) {
                //请求成功
                if(response.isSuccessful()){
                    MovieRespBase body = response.body();
                    if(body != null){
                        for (int i = 0; i < body.subjects.size(); i++) {
                            System.out.println("名称是:"+body.subjects.get(i).title);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieRespBase> call, Throwable t) {
            }
        });

    }

}
