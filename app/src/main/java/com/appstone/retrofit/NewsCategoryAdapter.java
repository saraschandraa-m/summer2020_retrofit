package com.appstone.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryHolder> {

    private Context context;
    private String[] newsCategories;
    private NewsCategoryClickListener listener;
    private int selectedPosition = -1;

    public void setListener(NewsCategoryClickListener listener) {
        this.listener = listener;
    }

    public NewsCategoryAdapter(Context context) {
        this.context = context;
        this.newsCategories = context.getResources().getStringArray(R.array.news_categories);
    }

    @NonNull
    @Override
    public NewsCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsCategoryHolder(LayoutInflater.from(context).inflate(R.layout.cell_news_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCategoryHolder holder, int position) {
        String newsCat = newsCategories[position];

        holder.mTvNewsCategory.setText(newsCat);

        holder.mRlNewsCategory.setOnClickListener(view -> {
            if (listener != null) {
                listener.onNewsCategoryClicked(newsCat);
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        if (position == selectedPosition) {
            holder.mRlNewsCategory.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bg_crect_selected, null));
            holder.mTvNewsCategory.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorWhite, null));
        } else {
            holder.mRlNewsCategory.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.bg_crect_unselected, null));
            holder.mTvNewsCategory.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.colorBlack, null));
        }
    }

    @Override
    public int getItemCount() {
        return newsCategories != null ? newsCategories.length : 0;
    }

    class NewsCategoryHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mRlNewsCategory;
        private TextView mTvNewsCategory;

        public NewsCategoryHolder(@NonNull View itemView) {
            super(itemView);

            mRlNewsCategory = itemView.findViewById(R.id.rl_news_category);
            mTvNewsCategory = itemView.findViewById(R.id.tv_news_category);
        }
    }

    public interface NewsCategoryClickListener {
        void onNewsCategoryClicked(String category);
    }
}
