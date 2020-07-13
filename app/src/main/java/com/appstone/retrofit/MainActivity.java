package com.appstone.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    //?sources=google-news&apiKey=4c82d7e8131841f484c6cf169bb83ae4

    private RecyclerView mRcNews;
    private ProgressDialog progressDialog;
    private EditText mEtSearchNews;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] newsCatearr = getResources().getStringArray(R.array.news_categories);

        ArrayList<String> categories = new ArrayList<String>(Arrays.asList(newsCatearr));

        mRcNews = findViewById(R.id.rc_news);
        mRcNews.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Getting news");

        mEtSearchNews = findViewById(R.id.et_search_news);
        ImageView mIvClearSearch = findViewById(R.id.iv_clear_search);

        mEtSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    mIvClearSearch.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(editable);
                } else {
                    mIvClearSearch.setVisibility(View.GONE);
                }
            }
        });

        mIvClearSearch.setOnClickListener(view -> {
            mEtSearchNews.setText("");
            adapter.clearSearch();
            mIvClearSearch.setVisibility(View.GONE);
        });

        getNews();
    }

    public void onGetNewsClicked(View view) {
        getNews();
    }

    private void getNews() {
        progressDialog.show();
        APInterface apInterface = APIClient.getClient().create(APInterface.class);

//        Call<String> getnews = apInterface.getNews("google-news", "4c82d7e8131841f484c6cf169bb83ae4");
//
//        getnews.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                String responseValue = response.body();
//                try {
//                    JSONObject responseObject = new JSONObject(responseValue);
//                    JSONArray articlesArray = responseObject.getJSONArray("articles");
//
//                    ArrayList<Article> articles = new ArrayList<>();
//                    for (int i = 0; i < articlesArray.length(); i++) {
//                        Article newArticle = Article.parseJSONObject(articlesArray.optJSONObject(i));
//                        articles.add(newArticle);
//                    }
//
//
//                    /** For showing image in the adapter
//                     *Glide.with(context).load(articles.urlToImage).into(holder.imageviewvarible);
//                     */
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//            }
//        });

        Call<Result> getNews = apInterface.getNews("google-news", "4c82d7e8131841f484c6cf169bb83ae4");

        getNews.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();

                ArrayList<Article> newsArticles = responseValue.articleList;
                progressDialog.hide();

                adapter = new NewsAdapter(MainActivity.this, newsArticles);
                mRcNews.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }
}