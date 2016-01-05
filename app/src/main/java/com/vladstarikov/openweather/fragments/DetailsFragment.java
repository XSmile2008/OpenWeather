package com.vladstarikov.openweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.weather.ForecastFormatter;
import com.vladstarikov.openweather.weather.ForecastLoader;
import com.vladstarikov.openweather.weather.realm.Forecast;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class DetailsFragment extends RealmFragment {

    private ForecastHolder holder;

    public static DetailsFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(MainActivity.FORECAST_ID, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (holder == null) holder = new ForecastHolder(view);
        Long forecastId = getArguments().getLong(MainActivity.FORECAST_ID);
        updateView(forecastId);
    }

    public void update(Long forecastId) {
        getArguments().remove(MainActivity.FORECAST_ID);
        getArguments().putLong(MainActivity.FORECAST_ID, forecastId);
        if (this.isVisible()) updateView(forecastId);
    }

    private void updateView(Long forecastId) {
        Forecast forecast = getRealm().where(Forecast.class).equalTo("dateUNIX", forecastId).findFirst();
        if (forecast != null) {
            ForecastFormatter forecastFormatter = new ForecastFormatter(forecast);
            holder.textViewDateTime.setText(String.format("%s %s", forecastFormatter.getDate(), forecastFormatter.getTime()));
            holder.textViewDescription.setText(forecastFormatter.getDescription());
            holder.textViewTemp.setText(forecastFormatter.getTemp());
            holder.textViewTempMinMax.setText(forecastFormatter.getTempMinMax());
            holder.textViewDetails.setText(forecastFormatter.getDetails());
            Picasso.with(getContext()).load(forecastFormatter.getIMGURL()).into(holder.imageView);
        }
    }

    private class ForecastHolder {

        TextView textViewDateTime;
        TextView textViewTemp;
        TextView textViewTempMinMax;
        TextView textViewDescription;
        TextView textViewDetails;
        ImageView imageView;

        private ForecastHolder(View itemView) {
            textViewDateTime = (TextView) itemView.findViewById(R.id.textViewDateTime);
            textViewTemp = (TextView) itemView.findViewById(R.id.textViewTemp);
            textViewTempMinMax = (TextView) itemView.findViewById(R.id.textViewTempMinMax);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewDetails = (TextView) itemView.findViewById(R.id.textViewDetails);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
