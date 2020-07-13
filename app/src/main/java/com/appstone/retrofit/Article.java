package com.appstone.retrofit;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Article {

    public String author;
    public String title;
    public String description;
    public String url;
    @SerializedName("urlToImage")
    public String urlToImage;
    public String publishedAt;
    public String content;

    @SerializedName("source")
    public Source source;
//    public DummyClass dummyClass;


    public static Article parseJSONObject(JSONObject jsonObject) {
        Article item = new Article();

        item.author = jsonObject.optString("author");
        item.description = jsonObject.optString("description");
        item.title = jsonObject.optString("title");
        item.url = jsonObject.optString("url");
        item.urlToImage = jsonObject.optString("urlToImage");
        item.publishedAt = jsonObject.optString("publishedAt");
        item.content = jsonObject.optString("content");

        JSONObject sourceObject = jsonObject.optJSONObject("source");

//        Source source = new Source();
//        source.id = sourceObject.optString("id");
//        source.name = sourceObject.optString("name");
//        item.source = source;

        item.source = Source.parseSourceJSON(sourceObject);

        return item;
    }

}
