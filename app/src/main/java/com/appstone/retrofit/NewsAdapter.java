package com.appstone.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> implements Filterable {

    private Context context;
    private ArrayList<Article> articles;
    private ArrayList<Article> displayArticles;
    private ArrayList<Article> suggestions;
    private NewsFilter filter;

    public NewsAdapter(Context context, ArrayList<Article> articles) {
        this.articles = articles;
        this.context = context;
        this.displayArticles = (ArrayList<Article>) articles.clone();
        this.suggestions = new ArrayList<>();
        filter = new NewsFilter();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.cell_news, parent, false);
//        NewsHolder holder = new NewsHolder(view);
//        return holder;
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.cell_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Article currentArticle = displayArticles.get(position);
        if (currentArticle != null) {
            holder.mTvTitle.setText(currentArticle.title);
            holder.mTvDescription.setText(currentArticle.description);
            Glide.with(context).load(currentArticle.urlToImage).placeholder(R.drawable.ic_launcher_background).into(holder.mIvNewsImage);
        }
    }

    @Override
    public int getItemCount() {
        return displayArticles != null ? displayArticles.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class NewsHolder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mIvNewsImage;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_news_title);
            mTvDescription = itemView.findViewById(R.id.tv_news_description);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_img);
        }
    }

    public class NewsFilter extends Filter {

        //Saras
        //saras


        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            suggestions.clear();
            if (charSequence != null) {
                for (Article article : articles) {
                    if (article.title.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(article);
                    } else if (article.description.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(article);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();


            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults != null) {
                if (filterResults.count > 0) {
                    displayArticles = (ArrayList<Article>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void clearSearch() {
        displayArticles.clear();
        displayArticles = (ArrayList<Article>) articles.clone();
        notifyDataSetChanged();
    }
}
