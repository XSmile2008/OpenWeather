package com.vladstarikov.openweather.weather.realm;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Starikov on 17.12.15.
 */
public class City implements Serializable {

    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
