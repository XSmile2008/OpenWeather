package com.vladstarikov.openweather.activities;

import com.vladstarikov.openweather.wheather.model.Forecast;

/**
 * Created by vladstarikov on 21.11.15.
 */
public interface IChooser {

    void choose(Forecast forecast);

}
