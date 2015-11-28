package com.vladstarikov.openweather.wheather.model;

import java.io.Serializable;

/**
 * Created by vladstarikov on 28.11.15.
 */
public class RealmForecast implements Serializable{
    private String dt;
    private Main main;
    private Weather[] weather;
    private Clouds clouds;
    private Wind wind;
    private Rain rain;
    //private Sys sys;
    private String dt_txt;

    public class Main implements Serializable {
        private double temp;
        private double temp_min;
        private double temp_max;
        private double pressure;
        private double sea_level;
        private double grnd_level;
        private int humidity;
        private double temp_kf;
    }

    public class Clouds implements Serializable {
        private int all;
    }

    public class Weather implements Serializable {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    public class Wind implements Serializable {
        private double speed;
        private int deg;
    }

    public class Rain implements Serializable {
        private double h3;
    }
}
