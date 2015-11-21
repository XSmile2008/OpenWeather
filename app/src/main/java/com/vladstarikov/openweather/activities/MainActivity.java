package com.vladstarikov.openweather.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.fragments.ChooserFragment;
import com.vladstarikov.openweather.fragments.DetailsFragment;
import com.vladstarikov.openweather.wheather.model.Forecast;

public class MainActivity extends AppCompatActivity implements IChooser{

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
            fragmentManager.beginTransaction().add(R.id.containerChooser, chooserFragment, "chooserFragment").commit();
        }
        if (findViewById(R.id.containerDetail) != null) {
            if (fragmentManager.getBackStackEntryCount() > 0) fragmentManager.popBackStack();
            if (fragmentManager.findFragmentByTag("detailFragment") == null) {
                DetailsFragment detailsFragment = new DetailsFragment();
                fragmentManager.beginTransaction().add(R.id.containerDetail, detailsFragment, "detailFragment").commit();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            ((ChooserFragment) getSupportFragmentManager().findFragmentByTag("chooserFragment")).loadForecast(city);
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
        } else ((DetailsFragment) fragmentManager.findFragmentByTag("detailFragment")).update(forecast);
    }
}
