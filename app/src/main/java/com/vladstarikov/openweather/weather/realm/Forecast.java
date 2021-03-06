package com.vladstarikov.openweather.weather.realm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vladstarikov on 28.11.15.
 */
public class Forecast extends RealmObject implements Serializable{
    @PrimaryKey
    @SerializedName("dt")       private long dateUNIX;
    @SerializedName("main")     private Main main;
    @SerializedName("weather")  private RealmList<Weather> weather;
    @SerializedName("clouds")   private Clouds clouds;
    @SerializedName("wind")     private Wind wind;
    @SerializedName("rain")     private Rain rain;
    @SerializedName("snow")     private Snow snow;
    //@SerializedName("sys")    private Sys sys;
    @SerializedName("dt_txt")   private String dateString;

    public long getDateUNIX() {
        return dateUNIX;
    }

    public void setDateUNIX(long dateUNIX) {
        this.dateUNIX = dateUNIX;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public RealmList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(RealmList<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }
}
