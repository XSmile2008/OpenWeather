package com.vladstarikov.openweather.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.weather.ForecastFormatter;
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

    public static int NOTIFICATION_ID = 117;
    public static int REQUEST_REFRESH = 0;
    public static int REQUEST_UPDATE = 1;

    private Context context = this;//TODO
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(MainActivity.LOG_TAG, this.getClass().getSimpleName() + "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i(MainActivity.LOG_TAG, this.getClass().getSimpleName() + ".onStartCommand");

        new ForecastLoader(getApplicationContext()) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                sendBroadcast(new Intent("com.vladstarikov.openweather.FORECAST_UPDATE"));
            }
        }.execute();

        if (timer != null ) {
            timer.cancel();
            timer.purge();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new ForecastLoader(context).inThisThread();
                Realm realm = Realm.getDefaultInstance();
                Forecast forecast = realm
                        .where(Forecast.class)
                        .greaterThan("dateUNIX", System.currentTimeMillis() / 1000L)
                        .findFirst();

                if (forecast != null) {
                    ForecastFormatter forecastFormatter = new ForecastFormatter(forecast);

                    Bitmap largeIcon = null;
                    try {
                        largeIcon = Picasso.with(context).load(forecastFormatter.getIMGURL()).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pendingIntentActivity = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent pendingIntentUpdate = PendingIntent.getBroadcast(context, 0, new Intent("com.vladstarikov.openweather.FORECAST_UPDATE"), PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                            .setContentIntent(pendingIntentActivity)
                            .addAction(android.R.drawable.stat_notify_sync_noanim, "Update", pendingIntentUpdate)
                            .setSmallIcon(R.drawable.ic_stat_ic_launcher)
                            .setLargeIcon(largeIcon)
                            .setContentTitle(forecastFormatter.getDescription())
                            .setContentText(forecastFormatter.getTemp())
                            .setSubText(forecastFormatter.getDetails())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notificationBuilder.build());
                }
                realm.close();
            }
        }, 60 * 1000, 60 * 1000);
        return START_STICKY;//super.onStartCommand(intent, flags, startId);
    }

}
