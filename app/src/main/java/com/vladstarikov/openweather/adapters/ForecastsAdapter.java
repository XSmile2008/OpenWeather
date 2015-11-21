package com.vladstarikov.openweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.MyDateFormatter;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.wheather.model.Forecast;

import java.util.List;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class ForecastsAdapter extends BaseAdapter {

    private Context context;
    private List<Forecast> forecasts;

    public ForecastsAdapter(Context context, List<Forecast> forecasts) {
        this.context = context;
        this.forecasts = forecasts;
    }

    @Override
    public int getCount() {
        return forecasts.size();
    }

    @Override
    public Object getItem(int position) {
        return forecasts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Forecast forecast = forecasts.get(position);

        MyDateFormatter date = new MyDateFormatter(forecast.dt_txt);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_forecast, parent, false);
        ((TextView) convertView.findViewById(R.id.textViewDateTime)).setText(date.toString());
        ((TextView) convertView.findViewById(R.id.textViewTemp)).setText(String.format("%.1f \u2103 ", forecast.main.temp));
        ((TextView) convertView.findViewById(R.id.textViewTempMinMax)).setText(String.format("%.1f - %.1f \u2103",forecast.main.temp_min, forecast.main.temp_max));
        ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(String.format("%S%s", forecast.weather[0].description.substring(0, 1), forecast.weather[0].description.substring(1)));
        Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into((ImageView) convertView.findViewById(R.id.imageView));
        return convertView;
    }
}
