package com.vladstarikov.openweather.application;

import android.app.Application;
import android.content.Intent;

import com.vladstarikov.openweather.services.ForecastService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Starikov on 16.12.15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
        startService(new Intent(this, ForecastService.class));
    }

}
