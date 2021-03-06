package com.vladstarikov.openweather.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.interfaces.OnItemSelectedListener;
import com.vladstarikov.openweather.weather.ForecastFormatter;
import com.vladstarikov.openweather.weather.ForecastLoader;
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
        ForecastFormatter forecastFormatter = new ForecastFormatter(forecasts.get(position));
        holder.textViewDateTime.setText(String.format("%s %s", forecastFormatter.getDate(), forecastFormatter.getTime()));
        holder.textViewTemp.setText(forecastFormatter.getTemp());
        holder.textViewTempMinMax.setText(forecastFormatter.getTempMinMax());
        holder.textViewDescription.setText(forecastFormatter.getDescription());
        Picasso.with(holder.imageView.getContext()).load(forecastFormatter.getIMGURL()).into(holder.imageView);
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
