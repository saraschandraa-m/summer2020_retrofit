package com.appstone.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APInterface {


    //https://www.google.com?url="value"&secondquery="value"&

    @GET("/v2/top-headlines")
    Call<String> getNews(@Query("sources") String sourceValue, @Query("apiKey") String apiKey);


}
