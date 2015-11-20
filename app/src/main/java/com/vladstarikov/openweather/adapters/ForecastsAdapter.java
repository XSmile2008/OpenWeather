package com.vladstarikov.openweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.wheather.model.Forecast;

import java.util.List;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class ForecastsAdapter extends BaseAdapter {

    Context context;
    List<Forecast> forecasts;

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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_forecast, parent, false);
        ((TextView) convertView.findViewById(R.id.textViewDate)).setText(forecast.dt_txt.substring(0, forecast.dt_txt.indexOf(" ")));
        ((TextView) convertView.findViewById(R.id.textViewTime)).setText(forecast.dt_txt.substring(forecast.dt_txt.indexOf(" ") + 1, forecast.dt_txt.length()));
        ((TextView) convertView.findViewById(R.id.textViewTemp)).setText(String.valueOf(forecast.main.temp));
        ((TextView) convertView.findViewById(R.id.textViewDescription)).setText(forecast.weather[0].description);
        Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.weather[0].icon + ".png").into((ImageView) convertView.findViewById(R.id.imageView));
        return convertView;
    }
}
