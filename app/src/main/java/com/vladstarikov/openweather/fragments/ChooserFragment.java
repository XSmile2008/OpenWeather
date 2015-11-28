package com.vladstarikov.openweather.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
        RealmResults<Forecast> results = realm.where(Forecast.class).greaterThan("dateUNIX", new Date().getTime()/1000L).findAll();
        //realm.close();//TODO: find where close Realm
        if (results != null) {
            adapter = new ForecastsAdapter(view.getContext(), results);
            ListView listView = (ListView) view.findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    chooser.choose((Forecast) adapter.getItem(position));
                }
            });
        } else Toast.makeText(getContext(), "Can't connect to server", Toast.LENGTH_SHORT).show();//TODO:  check in other place*/
    }
}
