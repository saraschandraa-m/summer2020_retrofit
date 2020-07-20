package com.appstone.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CountryPickerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CountryFilter> countryFilters;


    public CountryPickerAdapter(@NonNull Context context, ArrayList<CountryFilter> countryFilters) {
        this.context = context;
        this.countryFilters = countryFilters;
    }


    @Override
    public int getCount() {
        return countryFilters.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cell_spinnser, parent, false);
        }
        TextView mTvDisplay = convertView.findViewById(R.id.tv_country_holder);
        String displayName = countryFilters.get(position).countryDisplayName;

        mTvDisplay.setText(displayName);
        return convertView;
    }
}
