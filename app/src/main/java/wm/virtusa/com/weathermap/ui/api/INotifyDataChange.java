package wm.virtusa.com.weathermap.ui.api;

import java.util.Vector;

import wm.virtusa.com.weathermap.model.WeatherData;

/**
 * Created by soma on 10/11/2017.
 */

public interface INotifyDataChange {
    public void notifyDataChange(Vector<WeatherData> weatherData);
}
