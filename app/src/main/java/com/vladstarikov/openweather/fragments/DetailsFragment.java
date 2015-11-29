package com.vladstarikov.openweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.MyDateFormatter;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.weather.realm.Forecast;

import io.realm.Realm;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class DetailsFragment extends Fragment {

    private ForecastHolder holder;
    private Realm realm;
    private Long forecastId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(MainActivity.LOG_TAG, "DetailsFragment.CreateView()");
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (holder == null) holder = new ForecastHolder(getView());
        realm = Realm.getInstance(getContext());
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(MainActivity.FORECAST_ID)) {
            forecastId = bundle.getLong(MainActivity.FORECAST_ID);
        }
        if (forecastId != null) {
            updateView();
        }
        Log.i(MainActivity.LOG_TAG, "DetailsFragment.onViewCreated()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
        Log.i(MainActivity.LOG_TAG, "DetailsFragment.onDestroy()");
    }

    public void update(Long forecastId) {
        this.forecastId = forecastId;
        if (this.isVisible()) updateView();
    }

    public void updateView() {
        Forecast forecast = realm.where(Forecast.class).equalTo("dateUNIX", this.forecastId).findFirst();
        MyDateFormatter date = new MyDateFormatter(forecast.getDateUNIX() * 1000L);
        holder.textViewDateTime.setText(date.toString());
        holder.textViewDescription.setText(String.format("%S%s", forecast.getWeather().get(0).getDescription().substring(0, 1), forecast.getWeather().get(0).getDescription().substring(1)));
        holder.textViewTemp.setText(String.format("%.1f \u2103 ", forecast.getMain().getTemp()));
        holder.textViewTempMinMax.setText(String.format("%.1f - %.1f \u2103",forecast.getMain().getTemp_min(), forecast.getMain().getTemp_max()));
        holder.textViewPleasure.setText(String.format("Pleasure: %.2f hpa", forecast.getMain().getPressure()));
        holder.textViewHumidity.setText(String.format("Humidity: %d %%", forecast.getMain().getHumidity()));
        if (forecast.getRain() != null) holder.textViewRain.setText(String.format("Rain: %.2f", forecast.getRain().getRaininess()));
        holder.textViewClouds.setText(String.format("Clouds: %d %%", forecast.getClouds().getCloudiness()));
        holder.textViewWind.setText(String.format("Wind: %.2f m/s %d", forecast.getWind().getSpeed(), forecast.getWind().getDeg()));
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + forecast.getWeather().get(0).getIcon() + ".png").into(holder.imageView);
    }

    private class ForecastHolder {

        TextView textViewDateTime;
        TextView textViewTemp;
        TextView textViewTempMinMax;
        TextView textViewDescription;
        TextView textViewPleasure;
        TextView textViewHumidity;
        TextView textViewRain;
        TextView textViewClouds;
        TextView textViewWind;
        ImageView imageView;

        private ForecastHolder(View itemView) {
            textViewDateTime = (TextView) itemView.findViewById(R.id.textViewDateTime);
            textViewTemp = (TextView) itemView.findViewById(R.id.textViewTemp);
            textViewTempMinMax = (TextView) itemView.findViewById(R.id.textViewTempMinMax);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            textViewPleasure = (TextView) itemView.findViewById(R.id.textViewPleasure);
            textViewHumidity = (TextView) itemView.findViewById(R.id.textViewHumidity);
            textViewRain = (TextView) itemView.findViewById(R.id.textViewRain);
            textViewClouds = (TextView) itemView.findViewById(R.id.textViewClouds);
            textViewWind = (TextView) itemView.findViewById(R.id.textViewWind);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
