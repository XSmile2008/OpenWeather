package com.vladstarikov.openweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.adapters.ForecastsAdapter;
import com.vladstarikov.openweather.wheather.ForecastLoader;
import com.vladstarikov.openweather.wheather.model.Forecast;

import java.util.List;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class ChooserFragment extends Fragment {

    List<Forecast> forecasts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        forecasts = ForecastLoader.getForecast();
        ForecastsAdapter adapter = new ForecastsAdapter(view.getContext(), forecasts);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
