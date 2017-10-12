package wm.virtusa.com.weathermap.gateway.utility.parser;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Vector;

import wm.virtusa.com.weathermap.gateway.api.IHttpConnection;
import wm.virtusa.com.weathermap.model.WeatherData;
import wm.virtusa.com.weathermap.ui.api.INotifyDataChange;

/**
 * Created by Bikash on 10/10/2017.
 */

public class ResponseParser {

    private static final String TAG = ResponseParser.class.getSimpleName();

    private static INotifyDataChange mDataChangeListner;
    public static void registerListner(INotifyDataChange aDataChangeListner) {
        mDataChangeListner = aDataChangeListner;
    }

    public static void parseResponse(String aResponse, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object aRequestParam) {

        Log.d(TAG , "parseResponse() - response " +aResponse);
        Gson lGson = new Gson();
        try {
            WeatherData weatherDetails = lGson.fromJson(aResponse, WeatherData.class);
            Log.d(TAG , "weather Data " + weatherDetails.getName()  + "  " +weatherDetails.getId());
            if(mDataChangeListner != null) {
                Vector<WeatherData> lData = new Vector<WeatherData>();
                lData.add(weatherDetails);
                mDataChangeListner.notifyDataChange(lData);
            }
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }
}
