package com.vladstarikov.openweather.weather.realm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Starikov on 17.12.15.
 */
public class City extends RealmObject implements Serializable {

    @PrimaryKey
    @SerializedName("id")       private int id;
    @SerializedName("name")     private String name;
    @SerializedName("country")  private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
