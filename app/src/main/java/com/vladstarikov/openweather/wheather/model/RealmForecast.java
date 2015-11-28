package com.vladstarikov.openweather.wheather.model;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by vladstarikov on 28.11.15.
 */
public class RealmForecast extends RealmObject implements Serializable{
    public String dt;
    public Main main;
    public Weather[] weather;
    public Clouds clouds;
    public Wind wind;
    public Rain rain;
    //public Sys sys;
    public String dt_txt;

    public class Main implements Serializable {
        public double temp;
        public double temp_min;
        public double temp_max;
        public double pressure;
        public double sea_level;
        public double grnd_level;
        public int humidity;
        public double temp_kf;
    }

    public class Clouds implements Serializable {
        public int all;
    }

    public class Weather extends RealmObject implements Serializable {
        public int id;
        public String main;
        public String description;
        public String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Wind implements Serializable {
        public double speed;
        public int deg;
    }

    public class Rain implements Serializable {
        public double h3;
    }
}
