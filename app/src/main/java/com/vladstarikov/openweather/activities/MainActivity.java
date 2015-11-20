package com.vladstarikov.openweather.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.vladstarikov.openweather.R;
import com.vladstarikov.openweather.wheather.ForecastLoader;
import com.vladstarikov.openweather.wheather.model.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String forecast = ForecastLoader.getForecast();
        GsonBuilder gsonBuilder = new GsonBuilder();
        //Gson gson = new Gson();
        //gson.fromJson();
        try {
            //forecast = JSONObject.quote(forecast);
            JSONObject object = new JSONObject(forecast);
            JSONArray list = object.getJSONArray("list");
            Forecast forecast1 = new Gson().fromJson((JsonElement) list.get(0), Forecast.class);
            forecast1.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(forecast);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
