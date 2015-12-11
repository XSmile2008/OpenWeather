package com.vladstarikov.openweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.MyDateFormatter;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.interfaces.OnItemSelectedListener;
import com.vladstarikov.openweather.weather.realm.Forecast;

import io.realm.RealmResults;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class ForecastsAdapter extends RecyclerView.Adapter<ForecastsAdapter.ForecastHolder> {

    private OnItemSelectedListener<Long> selector;
    private RealmResults<Forecast> forecasts;

    public ForecastsAdapter(OnItemSelectedListener<Long> selector, RealmResults<Forecast> forecasts) {
        super();
        this.selector = selector;
        this.forecasts = forecasts;
    }

    public void setForecasts(RealmResults<Forecast> forecasts) {
        this.forecasts = forecasts;
        this.notifyDataSetChanged();
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_forecast, parent, false);
        return new ForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        MyDateFormatter date = new MyDateFormatter(forecast.getDateUNIX() * 1000L);
        holder.textViewDateTime.setText(String.format("%s %s", date.getDate(), date.getTime()));
        holder.textViewTemp.setText(String.format("%.1f \u2103 ", forecast.getMain().getTemp()));
        holder.textViewTempMinMax.setText(String.format("%.1f - %.1f \u2103", forecast.getMain().getTemp_min(), forecast.getMain().getTemp_max()));
        holder.textViewDescription.setText(String.format("%S%s", forecast.getWeather().get(0).getDescription().substring(0, 1), forecast.getWeather().get(0).getDescription().substring(1)));
        Picasso.with(holder.imageView.getContext()).load("http://openweathermap.org/img/w/" + forecast.getWeather().get(0).getIcon() + ".png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public class ForecastHolder extends RecyclerView.ViewHolder {

        TextView textViewDateTime;
        TextView textViewTemp;
        TextView textViewTempMinMax;
        TextView textViewDescription;
        ImageView imageView;

        public ForecastHolder(View itemView) {
            super(itemView);
            textViewDateTime = (TextView) itemView.findViewById(R.id.textViewDateTime);
            textViewTemp = (TextView) itemView.findViewById(R.id.textViewTemp);
            textViewTempMinMax = (TextView) itemView.findViewById(R.id.textViewTempMinMax);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selector.onItemSelected(forecasts.get(getAdapterPosition()).getDateUNIX());
                }
            });
        }
    }
}
