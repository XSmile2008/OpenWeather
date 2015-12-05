package com.vladstarikov.openweather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

        if (savedInstanceState == null) {
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.update(getIntent().getLongExtra(MainActivity.FORECAST_ID, 0));
            getSupportFragmentManager().beginTransaction().add(R.id.containerDetails, detailsFragment, MainActivity.DETAILS_FRAGMENT).commit();
        } else if (savedInstanceState.containsKey(MainActivity.FORECAST_ID))
            ((DetailsFragment) getSupportFragmentManager().findFragmentByTag(MainActivity.DETAILS_FRAGMENT)).update(savedInstanceState.getLong(MainActivity.FORECAST_ID));
    }
}
