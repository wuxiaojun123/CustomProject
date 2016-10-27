package com.example.retrofit;

import com.example.bean.MovieRespBase;
import com.example.bean.subjectsResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wuxiaojun on 16-8-29.
 */
public interface MovieService {

    @GET("/v2/movie/top250")
    Call<MovieRespBase> loadMovieData(
            @Query("start")int start,
            @Query("count")int count
    );

}
