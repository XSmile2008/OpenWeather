package com.vladstarikov.openweather.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.MyDateFormatter;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.wheather.model.Forecast;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("forecast")) {
            update((Forecast) bundle.getSerializable("forecast"));
        }
    }

    public void update(Forecast forecast) {
        View view = getView();
        MyDateFormatter date = new MyDateFormatter(forecast.dt_txt);
        ((TextView) view.findViewById(R.id.textViewDateTime)).setText(date.toString());
        ((TextView) view.findViewById(R.id.textViewDescription)).setText(String.format("%S%s", forecast.weather[0].description.substring(0, 1), forecast.weather[0].description.substring(1)));
        ((TextView) view.findViewById(R.id.textViewTemp)).setText(String.format("%.1f \u2103 ", forecast.main.temp));
        ((TextView) view.findViewById(R.id.textViewTempMinMax)).setText(String.format("%.1f - %.1f \u2103",forecast.main.temp_min, forecast.main.temp_max));
        ((TextView) view.findViewById(R.id.textViewPleasure)).setText(String.format("Pleasure: %.2f hpa", forecast.main.pressure));
        ((TextView) view.findViewById(R.id.textViewHumidity)).setText(String.format("Humidity: %d %%", forecast.main.humidity));
        if (forecast.rain != null) ((TextView) view.findViewById(R.id.textViewRain)).setText(String.format("Rain: %.2f", forecast.rain.h3));
        ((TextView) view.findViewById(R.id.textViewClouds)).setText(String.format("Clouds: %d %%", forecast.clouds.all));
        ((TextView) view.findViewById(R.id.textViewWind)).setText(String.format("Wind: %.2f m/s %d", forecast.wind.speed, forecast.wind.deg));
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into((ImageView) view.findViewById(R.id.imageView));
    }
}
