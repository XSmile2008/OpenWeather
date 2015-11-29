package com.vladstarikov.openweather.weather.realm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by vladstarikov on 28.11.15.
 */
public class Rain extends RealmObject implements Serializable{
    @SerializedName("3h")           private double raininess;

    public double getRaininess() {
        return raininess;
    }

    public void setRaininess(double raininess) {
        this.raininess = raininess;
    }
}
