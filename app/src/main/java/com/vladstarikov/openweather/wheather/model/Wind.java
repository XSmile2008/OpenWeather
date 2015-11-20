package com.vladstarikov.openweather.wheather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class Wind {
    @SerializedName("speed") public double speed;
    @SerializedName("deg") public double deg;
}
