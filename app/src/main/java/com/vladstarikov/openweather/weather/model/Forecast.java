package com.vladstarikov.openweather.weather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vladstarikov on 20.11.15.
 */
public class Forecast implements Serializable {

    @SerializedName("dt")       public String dt;
    @SerializedName("main")     public Main main;
    @SerializedName("weather")  public Weather[] weather;
    @SerializedName("clouds") public Clouds clouds;
    @SerializedName("wind")     public Wind wind;
    @SerializedName("rain")   public Rain rain;
    //@SerializedName("sys")    public Sys sys;
    @SerializedName("dt_txt")   public String dt_txt;

    public class Main implements Serializable {
        @SerializedName("temp")         public double temp;
        @SerializedName("temp_min")     public double temp_min;
        @SerializedName("temp_max")     public double temp_max;
        @SerializedName("pressure")     public double pressure;
        @SerializedName("sea_level")    public double sea_level;
        @SerializedName("grnd_level")   public double grnd_level;
        @SerializedName("humidity")     public int humidity;
        @SerializedName("temp_kf")      public double temp_kf;
    }

    public class Clouds implements Serializable {
        @SerializedName("all")          public int all;
    }

    public class Weather implements Serializable {
        @SerializedName("id")           public int id;
        @SerializedName("main")         public String main;
        @SerializedName("description")  public String description;
        @SerializedName("icon")         public String icon;
    }

    public class Wind implements Serializable {
        @SerializedName("speed")        public double speed;
        @SerializedName("deg")          public int deg;
    }

    public class Rain implements Serializable {
        @SerializedName("3h")           public double h3;
    }

}
