package com.vladstarikov.openweather.wheather.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class Weather {
    @SerializedName("id") public int id;
    @SerializedName("main") public String main;
    @SerializedName("description") public String description;
    @SerializedName("icon") public String icon;
}