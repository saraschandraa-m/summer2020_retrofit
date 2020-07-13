package com.appstone.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result {

    @SerializedName("status")
    public String responseStatus;
    public int totalResults;

    @SerializedName("articles")
    public ArrayList<Article> articleList;
}
