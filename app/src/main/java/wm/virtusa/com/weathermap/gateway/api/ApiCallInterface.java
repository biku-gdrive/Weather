package wm.virtusa.com.weathermap.gateway.api;

import android.util.Log;

import com.android.volley.Request;

import java.util.HashMap;

import wm.virtusa.com.weathermap.application.WMApplication;
import wm.virtusa.com.weathermap.gateway.GatewayController;

/**
 * Created by Bikash on 10/10/2017.
 */

public class ApiCallInterface {

    private static final String TAG = ApiCallInterface.class.getSimpleName();
    private static ApiCallInterface mSelf = null;

    private ApiCallInterface() {

    }

    public static  ApiCallInterface getInstance() {
        if(mSelf  == null) {
            mSelf = new ApiCallInterface();
        }
        return mSelf;
    }

    public void getWeatherContent(String aCity) {
        Log.d(TAG,"getWeatherContent()");
        GatewayController gatewayController = GatewayController.getInstance(WMApplication.getAppContext());
        HashMap<String, String> params = new HashMap<>();
        params.put(KeyName.CITY_NAME , aCity);
        gatewayController.processNetworkRequest(IHttpConnection.IResponseObserver.RequestTypeEnum.GET_CITY_WEATHER_CONDITIONS,params, Request.Priority.IMMEDIATE);
    }
}
