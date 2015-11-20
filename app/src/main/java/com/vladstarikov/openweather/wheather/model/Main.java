package com.vladstarikov.openweather.wheather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class Main {
    @SerializedName("temp") public double temp;
    @SerializedName("temp_min") public double temp_min;
    @SerializedName("temp_max") public double temp_max;
    @SerializedName("pressure") public double pressure;
    @SerializedName("sea_level") public double sea_level;
    @SerializedName("grnd_level") public double grnd_level;
    @SerializedName("humidity") public double humidity;
    @SerializedName("temp_kf") public double temp_kf;
}