package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.IChooser;
import com.vladstarikov.openweather.adapters.ForecastsAdapter;
import com.vladstarikov.openweather.weather.realm.Forecast;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class ChooserFragment extends Fragment {

    private IChooser chooser;
    private ForecastsAdapter adapter;
    RealmResults<Forecast> results;

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
        Realm realm = Realm.getInstance((Context) chooser);
        //realm.where(Forecast.class).findAll().clear();//TODO: delete old data
        results = realm.where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        //realm.close();//TODO: find where close Realm
        if (results != null) {
            adapter = new ForecastsAdapter(chooser, results);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager((Context) chooser));
            recyclerView.setAdapter(adapter);
        } else Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();//TODO:  check in other place*/
    }

    public void update() {
        Realm realm = Realm.getInstance((Context) chooser);
        results = realm.where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        adapter.notifyDataSetChanged();
    }
}
