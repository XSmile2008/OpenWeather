package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.interfaces.OnItemSelectedListener;
import com.vladstarikov.openweather.adapters.ForecastsAdapter;
import com.vladstarikov.openweather.weather.realm.Forecast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class SelectorFragment extends RealmFragment {

    private OnItemSelectedListener<Long> selector;
    private ForecastsAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selector = (OnItemSelectedListener<Long>) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selector, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RealmResults<Forecast> results = getRealm().where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        if (results != null) {
            adapter = new ForecastsAdapter(selector, results);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager((Context) selector));
            recyclerView.setAdapter(adapter);
        } else Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
    }

    public void refresh() {
        RealmResults<Forecast> results = getRealm().where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();//TODO: use this in onViewCreated
        if (results != null) adapter.setForecasts(results);
    }
}
