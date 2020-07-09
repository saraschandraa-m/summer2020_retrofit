package com.appstone.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    //?sources=google-news&apiKey=4c82d7e8131841f484c6cf169bb83ae4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGetNewsClicked(View view) {
        APInterface apInterface = APIClient.getClient().create(APInterface.class);

        Call<String> getnews = apInterface.getNews("google-news", "4c82d7e8131841f484c6cf169bb83ae4");

        getnews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String responseValue = response.body();
                try {
                    JSONObject responseObject = new JSONObject(responseValue);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    ArrayList<Article> articles = new ArrayList<>();
                    for (int i = 0; i < articlesArray.length(); i++) {
                        Article newArticle = Article.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticle);
                    }


                    /** For showing image in the adapter
                     *Glide.with(context).load(articles.urlToImage).into(holder.imageviewvarible);
                     */
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}