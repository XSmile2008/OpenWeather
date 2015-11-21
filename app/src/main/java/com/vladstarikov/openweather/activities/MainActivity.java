package com.vladstarikov.openweather.activities;

import android.content.DialogInterface;
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

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.fragments.ChooserFragment;
import com.vladstarikov.openweather.fragments.DetailsFragment;
import com.vladstarikov.openweather.wheather.model.Forecast;

public class MainActivity extends AppCompatActivity implements IChooser{

    private final String CHOOSER_FRAGMENT = "chooser";
    private final String DETAILS_FRAGMENT = "details";

    private String city = "Cherkasy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            args.putString("city", city);
            ChooserFragment chooserFragment = new ChooserFragment();
            chooserFragment.setArguments(args);
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
                ((ChooserFragment) getSupportFragmentManager().findFragmentByTag(CHOOSER_FRAGMENT)).loadForecast(city);
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
                                    ((ChooserFragment) getSupportFragmentManager().findFragmentByTag(CHOOSER_FRAGMENT)).loadForecast(city);
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
    public void choose(Forecast forecast) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.containerDetail) == null) {
            Bundle args = new Bundle();
            args.putSerializable("forecast", forecast);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(args);
            fragmentManager.beginTransaction().replace(R.id.containerChooser, detailsFragment).addToBackStack("detailFragmentBS").commit();
        } else ((DetailsFragment) fragmentManager.findFragmentByTag(DETAILS_FRAGMENT)).update(forecast);
    }

}
