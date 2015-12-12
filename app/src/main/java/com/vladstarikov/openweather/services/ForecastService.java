package com.vladstarikov.openweather.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.weather.ForecastLoader;
import com.vladstarikov.openweather.weather.realm.Forecast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

/**
 * Created by Starikov on 11.12.15.
 */
public class ForecastService extends Service {

    Context context = this;//TODO

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i(MainActivity.LOG_TAG, this.getClass().getName());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new ForecastLoader(context).loadForecast();
                Realm realm = Realm.getInstance(context);
                Forecast forecast = realm.where(Forecast.class).greaterThan("dateUNIX", System.currentTimeMillis() / 1000L).findFirst();
                if (forecast != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(String.format("Pleasure: %.2f hpa", forecast.getMain().getPressure()));
                    builder.append(String.format("\nHumidity: %d %%", forecast.getMain().getHumidity()));
                    if (forecast.getRain() != null && forecast.getRain().getRainiest() != 0)
                        builder.append(String.format("\nRain: %.3f", forecast.getRain().getRainiest()));
                    if (forecast.getSnow() != null && forecast.getSnow().getSnowiness() != 0)
                        builder.append(String.format("\nSnow: %.3f", forecast.getSnow().getSnowiness()));
                    builder.append(String.format("\nClouds: %d %%", forecast.getClouds().getCloudiness()));
                    builder.append(String.format("\nWind: %.2f m/s %d", forecast.getWind().getSpeed(), forecast.getWind().getDeg()));

                    Bitmap largeIcon = null;
                    try {
                        largeIcon = Picasso.with(context).load("http://openweathermap.org/img/w/" + forecast.getWeather().get(0).getIcon() + ".png").get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntentActivity = PendingIntent.getActivity(context, 117, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    PendingIntent pendingIntentUpdate = PendingIntent.getBroadcast(context, 117, new Intent("com.vladstarikov.openweather.FORECAST_UPDATE"), PendingIntent.FLAG_CANCEL_CURRENT);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                            .setContentIntent(pendingIntentActivity)
                            .addAction(R.mipmap.ic_launcher, "Update", pendingIntentUpdate)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(largeIcon)
                            .setContentTitle(String.format("%S%s", forecast.getWeather().get(0).getDescription().substring(0, 1), forecast.getWeather().get(0).getDescription().substring(1)))
                            .setContentText(String.format("%.1f \u2103 ", forecast.getMain().getTemp()))
                            .setSubText(builder.toString())
                            .setPriority(NotificationCompat.PRIORITY_MIN);
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notificationBuilder.build());
                }
                realm.close();
            }
        }, 0L, 60000);
        return START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

}
