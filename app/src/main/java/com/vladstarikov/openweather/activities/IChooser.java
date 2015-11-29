package com.vladstarikov.openweather.activities;

import com.vladstarikov.openweather.weather.realm.Forecast;

/**
 * Created by vladstarikov on 21.11.15.
 */
public interface IChooser {

    void choose(Forecast forecast);

}
