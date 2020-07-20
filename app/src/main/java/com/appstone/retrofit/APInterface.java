package com.appstone.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APInterface {


    //https://www.google.com?url="value"&secondquery="value"&

//    @GET("/v2/top-headlines")
//    Call<String> getNews(@Query("sources") String sourceValue, @Query("apiKey") String apiKey);

//    @GET("/v2/top-headlines")
//    Call<Result> getNews(@Query("sources") String sourceValue, @Query("apiKey") String apiKey);

    //Call<Result> getNews(@Query("category") String category, @Query("apiKey") String apiKey);


//    @GET("/v2/everything")
//    Call<Result> getAllNews(@Query("q")String query, @Query("from") String filterFrom, @Query("sortBy") String sort, @Query("apiKey") String apiKey);

    @GET("/v2/top-headlines")
    Call<Result> getNews(@QueryMap Map<String, Object> options);

//    @POST("/v2/everything")
//    Call<Result> getAllNews(@QueryMap Map<String, Object> options);


}
