package com.vladstarikov.openweather.wheather;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vladstarikov.openweather.wheather.model.Forecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vladstarikov on 19.11.15.
 */
public class ForecastLoader {

    private final static String APPID = "da9546c174d073130bb1d1caace0c6c3";
    private final static String SITE = "http://api.openweathermap.org/data/2.5/forecast";
    private final static String CITY = "Cherkasy";
    private final static String MODE = "json";
    private final static String UNITS = "metric";
    private final static String SOURCE = SITE + "?" + "mode=" + MODE + "&units=" + UNITS + "&appid=" + APPID + "&q=";

    private final static String IMG_URL = "http://openweathermap.org/img/w/";

    Context context;

    public ForecastLoader(Context context) {
        this.context = context;
    }

    public List<Forecast> getForecast() {//TODO: remove
        return getForecast(CITY);
    }

    public List<Forecast> getForecast(String city) {
        ForecastHttpClient loader = new ForecastHttpClient();
        try {
            return loader.execute(city).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ForecastHttpClient extends AsyncTask<String, Void, List<Forecast>> {
        @Override
        protected List<Forecast> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            InputStream is  = null;
            try {
                urlConnection = (HttpURLConnection) (new URL(SOURCE + params[0])).openConnection();
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
                is.close();
                urlConnection.disconnect();

                //parse JSON
                Gson gson = new Gson();
                JsonObject forecast5d = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
                List<Forecast> forecasts = new ArrayList<>();
                for (JsonElement element : forecast5d.getAsJsonArray("list")) {
                    forecasts.add(gson.fromJson(element, Forecast.class));
                }


                RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
                Realm.deleteRealm(realmConfiguration);
                Realm realm = Realm.getInstance(realmConfiguration);
                //realm.createObjectFromJson();


                return forecasts;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                try {if (is != null) is.close();} catch (IOException e) {e.printStackTrace();}
            }
            return null;
        }
    }
}
