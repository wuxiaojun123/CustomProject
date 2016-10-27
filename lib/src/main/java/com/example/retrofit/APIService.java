package com.example.retrofit;

import com.example.bean.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by wuxiaojun on 16-8-29.
 */
public interface APIService {

    @GET("/repos/{square}/{retrofit}/contributors")
    Call<List<Repo>> loadRepo(
            @Path("square")String square,
            @Path("retrofit")String retrofit
    );

}
