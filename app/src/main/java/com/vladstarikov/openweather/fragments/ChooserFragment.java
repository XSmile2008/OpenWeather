package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.IChooser;
import com.vladstarikov.openweather.adapters.ForecastsAdapter;
import com.vladstarikov.openweather.weather.ForecastLoader;
import com.vladstarikov.openweather.weather.model.Forecast;

import java.util.List;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class ChooserFragment extends Fragment {

    private IChooser chooser;
    private ForecastsAdapter adapter;
    private List<Forecast> forecasts;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chooser  = (IChooser) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (forecasts == null) {
            //forecasts = new ForecastLoader(view.getContext()).getForecast(getArguments().getString("city"));
            List<com.vladstarikov.openweather.weather.realm.Forecast> forecasts = new ForecastLoader(view.getContext()).getForecast(getArguments().getString("city"));
            /*if (forecasts != null) {
                adapter = new ForecastsAdapter(view.getContext(), forecasts);
                ListView listView = (ListView) view.findViewById(R.id.listView);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        chooser.choose(forecasts.get(position));
                    }
                });
            } else Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();//TODO:  check in other place*/
        }
    }

    public void loadForecast(String city) {
        /*List<Forecast> newForecasts = new ForecastLoader(getContext()).getForecast(city);
        if (newForecasts != null) {
            forecasts.clear();
            forecasts.addAll(newForecasts);
            adapter.notifyDataSetChanged();
        }*/
    }
}
