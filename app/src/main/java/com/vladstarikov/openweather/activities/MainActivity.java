package com.vladstarikov.openweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.fragments.SelectorFragment;
import com.vladstarikov.openweather.fragments.DetailsFragment;
import com.vladstarikov.openweather.interfaces.OnItemSelectedListener;
import com.vladstarikov.openweather.weather.ForecastLoader;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener<Long> {

    public static final String LOG_TAG = "neko";
    public static final String FORECAST_ID = "forecastId";

    private FragmentManager fragmentManager;
    private ActionBar actionBar;

    private String city = "Cherkasy";
    private Long selectedForecastId = Long.MIN_VALUE;//TODO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            refreshForecasts();
            SelectorFragment selectorFragment = new SelectorFragment();
            fragmentManager.beginTransaction().add(R.id.containerSelector, selectorFragment).commit();
        } else if (savedInstanceState.containsKey(FORECAST_ID)) {
            selectedForecastId = savedInstanceState.getLong(FORECAST_ID);
        }

        if (findViewById(R.id.containerDetail) != null) {//if Tablet mode
            if (fragmentManager.getBackStackEntryCount() > 0) onBackPressed();
            if (fragmentManager.findFragmentById(R.id.containerDetail) == null) {
                fragmentManager.beginTransaction().add(R.id.containerDetail, DetailsFragment.newInstance(selectedForecastId)).commit();
            } else onItemSelected(selectedForecastId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
            case R.id.action_refresh:
                refreshForecasts();
                return true;
            case R.id.action_set_city:
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.dialog_city, null, false);
                final EditText editText = (EditText) promptView.findViewById(R.id.editText);
                editText.setHint(city);

                new AlertDialog.Builder(MainActivity.this)
                        .setView(promptView)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (editText.getText().toString().length() > 2) {
                                    city = editText.getText().toString();
                                    city = String.format("%S%s", city.substring(0, 1), city.substring(1));
                                    refreshForecasts();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onSaveInstanceState(new Bundle());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FORECAST_ID, selectedForecastId);
    }

    @Override
    public void onItemSelected(Long forecastId) {
        Log.i(LOG_TAG, "onItemSelected" + forecastId.toString());
        selectedForecastId = forecastId;
        if (findViewById(R.id.containerDetail) == null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(null);
            DetailsFragment detailsFragment = DetailsFragment.newInstance(selectedForecastId);
            fragmentManager.beginTransaction().replace(R.id.containerSelector, detailsFragment, "detailsBS").addToBackStack("detailsBS").commit();
        } else ((DetailsFragment) fragmentManager.findFragmentById(R.id.containerDetail)).update(selectedForecastId);
    }

    private void refreshForecasts() {
        //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext()).build();//TODO: catch exception when change realm and delete database
        //Realm.getInstance(this).close();
        //Realm.deleteRealm(realmConfiguration);
        Log.i(LOG_TAG, "onRefreshForecasts");
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) new ForecastLoader(this).loadForecasts(city);
        else Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();

        SelectorFragment selectorFragment = (SelectorFragment) fragmentManager.findFragmentById(R.id.containerSelector);
        if (selectorFragment != null) selectorFragment.refresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setIcon(R.mipmap.ic_launcher);
    }
}
