package com.vladstarikov.openweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.vladstarikov.openweather.fragments.ChooserFragment;
import com.vladstarikov.openweather.fragments.DetailsFragment;
import com.vladstarikov.openweather.weather.ForecastLoader;

public class MainActivity extends AppCompatActivity implements IChooser<Long>{

    public static final String LOG_TAG = "neko";
    public static final String FORECAST_ID = "forecastId";

    FragmentManager fragmentManager;

    private String city = "Cherkasy";
    private Long selectedForecastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) refreshForecasts();
        else if (savedInstanceState.containsKey(FORECAST_ID)) selectedForecastId = savedInstanceState.getLong(FORECAST_ID);

        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetails);
        if (detailsFragment != null) detailsFragment.update(selectedForecastId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                refreshForecasts();
                return true;
            case R.id.action_set_city:
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.dialog_city, null, false);
                final EditText editText = (EditText) promptView.findViewById(R.id.editText);
                editText.setHint(city);

                AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
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
                        }).create();
                alert.show();
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
    public void choose(Long forecastId) {
        Log.i(LOG_TAG, "onChoose" + forecastId.toString());
        selectedForecastId = forecastId;
        DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetails);
        if (detailsFragment == null) startActivity(new Intent(this, DetailsActivity.class).putExtra(FORECAST_ID, selectedForecastId));
        else detailsFragment.update(selectedForecastId);
    }

    private void refreshForecasts() {
        //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext()).build();//TODO:
        //Realm.getInstance(this).close();
        //Realm.deleteRealm(realmConfiguration);
        Log.i(LOG_TAG, "onRefreshForecasts");
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) new ForecastLoader(this).loadForecasts(city);
        else Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();

        ChooserFragment chooserFragment = (ChooserFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentChooser);
        if (chooserFragment != null) chooserFragment.refresh();
    }

}
