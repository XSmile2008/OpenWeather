package com.vladstarikov.openweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.util.MyDateFormatter;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.MainActivity;
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
            MyDateFormatter date = new MyDateFormatter(forecast.getDateUNIX() * 1000L);
            holder.textViewDateTime.setText(date.toString());
            holder.textViewDescription.setText(String.format("%S%s", forecast.getWeather().get(0).getDescription().substring(0, 1), forecast.getWeather().get(0).getDescription().substring(1)));
            holder.textViewTemp.setText(String.format("%.0f \u2103 ", forecast.getMain().getTemp()));
            holder.textViewTempMinMax.setText(String.format("%.0f - %.0f \u2103", forecast.getMain().getTemp_min(), forecast.getMain().getTemp_max()));
            holder.textViewDetails.setText(String.format("Pleasure: %.2f hpa", forecast.getMain().getPressure()));
            holder.textViewDetails.append(String.format("\nHumidity: %d %%", forecast.getMain().getHumidity()));
            if (forecast.getRain() != null && forecast.getRain().getRainiest() != 0)
                holder.textViewDetails.append(String.format("\nRain: %.3f", forecast.getRain().getRainiest()));
            if (forecast.getSnow() != null && forecast.getSnow().getSnowiness() != 0)
                holder.textViewDetails.append(String.format("\nSnow: %.3f", forecast.getSnow().getSnowiness()));
            holder.textViewDetails.append(String.format("\nClouds: %d %%", forecast.getClouds().getCloudiness()));
            holder.textViewDetails.append(String.format("\nWind: %.2f m/s %d", forecast.getWind().getSpeed(), forecast.getWind().getDeg()));
            Picasso.with(getContext()).load(ForecastLoader.IMG_URL + forecast.getWeather().get(0).getIcon() + ".png").into(holder.imageView);
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
