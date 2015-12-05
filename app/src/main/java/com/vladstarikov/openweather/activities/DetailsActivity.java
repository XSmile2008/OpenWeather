package com.vladstarikov.openweather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.fragments.DetailsFragment;

/**
 * Created by Starikov on 05.12.15.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(MainActivity.FORECAST_ID))
            ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetails)).update(savedInstanceState.getLong(MainActivity.FORECAST_ID));
        else ((DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentDetails)).update(getIntent().getLongExtra(MainActivity.FORECAST_ID, 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
