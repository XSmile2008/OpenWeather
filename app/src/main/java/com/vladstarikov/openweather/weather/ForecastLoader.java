package com.vladstarikov.openweather.weather;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.vladstarikov.openweather.activities.MainActivity;
import com.vladstarikov.openweather.weather.realm.Forecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class ForecastLoader extends AsyncTask<Void, Void, Void> {

    private final static String APPID = "da9546c174d073130bb1d1caace0c6c3";
    private final static String SITE = "http://api.openweathermap.org/data/2.5/forecast";
    private final static String CITY = "Cherkasy";
    private final static String MODE = "json";
    private final static String UNITS = "metric";
    private final static String SOURCE = SITE + "?" + "mode=" + MODE + "&units=" + UNITS + "&appid=" + APPID + "&q=";
    private final static String IMG_URL = "http://openweathermap.org/img/w/";

    Context context;

    public ForecastLoader(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        inThisThread();
        return null;
    }

    public void inThisThread() {
        Log.i(MainActivity.LOG_TAG, getClass().getSimpleName() + ".inThisThread()");
        String city = PreferenceManager.getDefaultSharedPreferences(context).getString("city", "Cherkasy");
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        Realm realm = Realm.getInstance(context);//TODO: use getDefaultInstance
        try {
            urlConnection = (HttpURLConnection) (new URL(SOURCE + city)).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }

            //Configure Gson to work with Realm
            Gson gson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .create();

            //parse Json
            JsonObject forecast5d = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
            List<Forecast> forecasts = gson.fromJson(forecast5d.getAsJsonArray("list"), new TypeToken<List<Forecast>>() {}.getType());

            //write to database
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(forecasts);//Hint: Forecast must contains @PrimaryKey
            realm.commitTransaction();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            realm.close();
        }
    }
}