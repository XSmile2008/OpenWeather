package com.vladstarikov.openweather.wheather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class Forecast implements Serializable{

    @SerializedName("dt") public String dt;
    @SerializedName("dt") public Main main;
    @SerializedName("dt") public Weather[] weather;
    //@SerializedName("dt") public Clouds clound;
    @SerializedName("dt") public Wind wind;
    //@SerializedName("dt") public Rain rain;
    //@SerializedName("dt") public Sys sys;
    @SerializedName("dt") public String dt_txt;

}
