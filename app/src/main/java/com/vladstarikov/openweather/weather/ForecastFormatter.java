package com.vladstarikov.openweather.weather;

import com.vladstarikov.openweather.weather.realm.Forecast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vladstarikov on 05.01.16.
 */
public class ForecastFormatter {

    static SimpleDateFormat myFormatDate = new SimpleDateFormat("E MMM dd", Locale.getDefault());
    static SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

    Forecast forecast;

    public ForecastFormatter(Forecast forecast) {
        this.forecast = forecast;
    }

    public String getDate() {
        return myFormatDate.format(new Date(forecast.getDateUNIX() * 1000L));
    }

    public String getTime() {
        return myFormatTime.format(new Date(forecast.getDateUNIX() * 1000L));
    }

    public String getTemp() {
        return String.format("%.0f \u2103 ", forecast.getMain().getTemp());
    }

    public String getTempMinMax() {
        return String.format("%.0f - %.0f \u2103", forecast.getMain().getTemp_min(), forecast.getMain().getTemp_max());
    }

    public String getDescription() {
        String description = forecast.getWeather().get(0).getDescription();
        return String.format("%S%s", description.substring(0, 1), description.substring(1));
    }

    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Pleasure: %.2f hpa", forecast.getMain().getPressure()));
        builder.append(String.format("\nHumidity: %d %%", forecast.getMain().getHumidity()));
        if (forecast.getRain() != null && forecast.getRain().getRainiest() != 0)
            builder.append(String.format("\nRain: %.3f", forecast.getRain().getRainiest()));
        if (forecast.getSnow() != null && forecast.getSnow().getSnowiness() != 0)
            builder.append(String.format("\nSnow: %.3f", forecast.getSnow().getSnowiness()));
        builder.append(String.format("\nClouds: %d %%", forecast.getClouds().getCloudiness()));
        builder.append(String.format("\nWind: %.2f m/s %d", forecast.getWind().getSpeed(), forecast.getWind().getDeg()));
        return builder.toString();
    }

    public String getIMGURL() {
        return ForecastLoader.IMG_URL + forecast.getWeather().get(0).getIcon() + ".png";
    }


}
