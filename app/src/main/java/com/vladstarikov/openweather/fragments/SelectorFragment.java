package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.interfaces.OnItemSelectedListener;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.adapters.ForecastsAdapter;
import com.vladstarikov.openweather.weather.realm.Forecast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class SelectorFragment extends Fragment {

    private OnItemSelectedListener<Long> chooser;
    private ForecastsAdapter adapter;
    private Realm realm;
    private RealmResults<Forecast> results;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        chooser  = (OnItemSelectedListener<Long>) context;
        realm = Realm.getInstance((Context) chooser);
        Log.i(MainActivity.LOG_TAG, getTag() + ".onAttach()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, getTag() + ".onCreateView()");
        return inflater.inflate(R.layout.fragment_chooser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        results = realm.where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        if (results != null) {
            adapter = new ForecastsAdapter(chooser, results);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager((Context) chooser));
            recyclerView.setAdapter(adapter);
        } else Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();
        Log.i(MainActivity.LOG_TAG, getTag() + ".onViewCreated()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        Log.i(MainActivity.LOG_TAG, getTag() + ".onDestroy()");
    }

    public void refresh() {
        results = realm.where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        adapter.notifyDataSetChanged();
    }
}
