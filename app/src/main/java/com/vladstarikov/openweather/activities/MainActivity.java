package com.vladstarikov.openweather.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.vladstarikov.openweather.weather.realm.Forecast;

public class MainActivity extends AppCompatActivity implements IChooser{

    private final String CHOOSER_FRAGMENT = "chooser";
    private final String DETAILS_FRAGMENT = "details";

    private String city = "Cherkasy";
    private Forecast selectedForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            refreshForecasts();
            ChooserFragment chooserFragment = new ChooserFragment();
            fragmentManager.beginTransaction().add(R.id.containerChooser, chooserFragment, CHOOSER_FRAGMENT).commit();
        }
        if (findViewById(R.id.containerDetail) != null) {
            if (fragmentManager.getBackStackEntryCount() > 0) fragmentManager.popBackStack();
            if (fragmentManager.findFragmentByTag(DETAILS_FRAGMENT) == null) {
                DetailsFragment detailsFragment = new DetailsFragment();
                fragmentManager.beginTransaction().add(R.id.containerDetail, detailsFragment, DETAILS_FRAGMENT).commit();
            }
        }
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
        outState.putSerializable("selectedForecast", selectedForecast);
    }



    @Override
    public void choose(Forecast forecast) {
        selectedForecast = forecast;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.containerDetail) == null) {
            Bundle args = new Bundle();
            args.putSerializable("forecast", forecast);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.containerChooser, detailsFragment).addToBackStack("detailFragmentBS").commit();
        } else ((DetailsFragment) fragmentManager.findFragmentByTag(DETAILS_FRAGMENT)).update(forecast);
    }

    private void refreshForecasts() {
        //RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext()).build();//TODO:
        //Realm.getInstance(this).close();
        //Realm.deleteRealm(realmConfiguration);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) new ForecastLoader(this).loadForecasts(city);
        else Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
    }

}
