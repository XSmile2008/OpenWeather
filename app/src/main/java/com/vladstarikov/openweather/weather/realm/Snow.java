package com.vladstarikov.openweather.weather.realm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by vladstarikov on 28.11.15.
 */
public class Snow extends RealmObject implements Serializable{
    @SerializedName("3h")           private double snowiness;

    public double getSnowiness() {
        return snowiness;
    }

    public void setSnowiness(double snowiness) {
        this.snowiness = snowiness;
    }
}
