package com.appstone.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsCategoryAdapter.NewsCategoryClickListener {


    //?sources=google-news&apiKey=4c82d7e8131841f484c6cf169bb83ae4

    private RecyclerView mRcNews;
    private ProgressDialog progressDialog;
    private EditText mEtSearchNews;
    private NewsAdapter adapter;

    private String selectedCountry = "us";
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = findViewById(R.id.tl_home);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        String[] countryCodeArray = getResources().getStringArray(R.array.country_code);
        String[] countryDisplayArray = getResources().getStringArray(R.array.country_name);

        ArrayList<CountryFilter> countriesList = new ArrayList<>();

        for (int i = 0; i < countryDisplayArray.length; i++) {
            CountryFilter item = new CountryFilter();
            item.countryDisplayName = countryDisplayArray[i];
            item.countryDisplayCode = countryCodeArray[i];
            countriesList.add(item);
        }


        mRcNews = findViewById(R.id.rc_news);
        mRcNews.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Getting news");

        mEtSearchNews = findViewById(R.id.et_search_news);
        ImageView mIvClearSearch = findViewById(R.id.iv_clear_search);

        RecyclerView mRcNewsCategories = findViewById(R.id.rc_news_category);
        mRcNewsCategories.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));

        NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(MainActivity.this);
        newsCategoryAdapter.setListener(this);
        mRcNewsCategories.setAdapter(newsCategoryAdapter);

        Spinner mSpnCountries = findViewById(R.id.spn_countries);
        CountryPickerAdapter countryAdapter = new CountryPickerAdapter(MainActivity.this, countriesList);

        mSpnCountries.setAdapter(countryAdapter);

        mSpnCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCountry = countriesList.get(i).countryDisplayCode;
                getNewsByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        selectedCategory = "";
        getNewsByCategory("");
    }

    private void getNewsByCategory(String category) {
        progressDialog.show();
        APInterface apInterface = APIClient.getClient().create(APInterface.class);

        Map<String, Object> params = new HashMap<>();

        params.put("category", category);
        params.put("country", selectedCountry);
        params.put("apiKey", "4c82d7e8131841f484c6cf169bb83ae4");

        Call<Result> getAllNews = apInterface.getNews(params);

        getAllNews.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();

                ArrayList<Article> newsArticles = responseValue.articleList;
                progressDialog.hide();

                adapter = new NewsAdapter(MainActivity.this, newsArticles);
                mRcNews.setAdapter(adapter);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onNewsCategoryClicked(String category) {
        selectedCategory = category;
        getNewsByCategory(category);
    }
}