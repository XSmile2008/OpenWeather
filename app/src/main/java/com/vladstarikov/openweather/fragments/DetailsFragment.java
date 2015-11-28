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
import com.vladstarikov.openweather.weather.realm.Forecast;

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
        MyDateFormatter date = new MyDateFormatter(forecast.getDateUNIX() * 1000L);
        ((TextView) view.findViewById(R.id.textViewDateTime)).setText(date.toString());
        ((TextView) view.findViewById(R.id.textViewDescription)).setText(String.format("%S%s", forecast.getWeather().get(0).getDescription().substring(0, 1), forecast.getWeather().get(0).getDescription().substring(1)));
        ((TextView) view.findViewById(R.id.textViewTemp)).setText(String.format("%.1f \u2103 ", forecast.getMain().getTemp()));
        ((TextView) view.findViewById(R.id.textViewTempMinMax)).setText(String.format("%.1f - %.1f \u2103",forecast.getMain().getTemp_min(), forecast.getMain().getTemp_max()));
        ((TextView) view.findViewById(R.id.textViewPleasure)).setText(String.format("Pleasure: %.2f hpa", forecast.getMain().getPressure()));
        ((TextView) view.findViewById(R.id.textViewHumidity)).setText(String.format("Humidity: %d %%", forecast.getMain().getHumidity()));
        if (forecast.getRain() != null) ((TextView) view.findViewById(R.id.textViewRain)).setText(String.format("Rain: %.2f", forecast.getRain().getRaininess()));
        ((TextView) view.findViewById(R.id.textViewClouds)).setText(String.format("Clouds: %d %%", forecast.getClouds().getCloudiness()));
        ((TextView) view.findViewById(R.id.textViewWind)).setText(String.format("Wind: %.2f m/s %d", forecast.getWind().getSpeed(), forecast.getWind().getDeg()));
        Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + forecast.getWeather().get(0).getIcon() + ".png").into((ImageView) view.findViewById(R.id.imageView));
    }
}
