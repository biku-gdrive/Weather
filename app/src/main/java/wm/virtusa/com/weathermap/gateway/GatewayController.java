package wm.virtusa.com.weathermap.gateway;

import android.content.Context;
import android.util.Log;


import com.volleyWrapper.networkSDK.Listener.ErrorResponseListener;
import com.volleyWrapper.networkSDK.Listener.ResponseListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wm.virtusa.com.weathermap.gateway.api.IHttpConnection;
import wm.virtusa.com.weathermap.gateway.api.KeyName;
import wm.virtusa.com.weathermap.gateway.utility.parser.ResponseParser;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.volleyWrapper.networkSDK.JobQueue.PriorityJobQueue;
import com.volleyWrapper.networkSDK.Network.Constants;
import com.volleyWrapper.networkSDK.Network.GetRequest;
import com.volleyWrapper.networkSDK.Network.NetworkHandler;
import com.volleyWrapper.networkSDK.Network.PostRequest;
/**
 * Created by Bikash on 10/10/2017.
 */

public class GatewayController implements ResponseListener.Listener,ErrorResponseListener.ErrorListener{
    private static GatewayController instance = null;
    private Context mContext;
    private NetworkHandler networkHandler;
    private PriorityJobQueue priorityJobQueue;
    private IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType;
    private String TAG = GatewayController.class.getName();

    private GatewayController(Context aCxt) {
        this.mContext = aCxt;
        this.networkHandler = NetworkHandler.getInstance(mContext);
        this.priorityJobQueue = networkHandler.getJobQueue();
        Log.d(TAG, "Inside GatewayController ()");
    }
    public static GatewayController getInstance(Context mContext) {
        if (instance == null) instance = new GatewayController(mContext);
        return instance;
    }

    //Methods to process the network request

    public void processNetworkRequest(IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Object requestParams, Request.Priority priority) {
      /*  if (!NetworkUtility.hasInternetConnection(mContext)) {
            //TODO: Show Error message for network not available
            Toast.makeText(mContext, mContext.getResources().getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
            return;
        }*/
        this.mResponseType = mResponseType;
        switch (mResponseType) {
            case GET_CITY_WEATHER_CONDITIONS:
                Log.v(TAG, "LogoutRequest processNetworkRequest()-->");
                getWeather(requestParams, mResponseType, priority);
                break;
            case POST_CONTENT:
                Log.v(TAG, "LOGIN_AUTH_SERVICE processNetworkRequest()-->");
                postRequest(requestParams, mResponseType, priority);
                break;

        }
    }

    private void getWeather(Object requestParams, IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Request.Priority priority) {
        HashMap<String, String> params = (HashMap<String, String>) requestParams;
        Log.v(TAG, "getWeatherContent() Request-->");

        Log.d(TAG, "Request is about to get the Contents  = ");
        //Hard coded URL , It can be put on config file
        //String lUrl = "http://samples.openweathermap.org/data/2.5/weather?q="+ params.get(KeyName.CITY_NAME)+"&appid=a2789f0a017717b5a2032324a472943c";
        String lUrl ="http://api.openweathermap.org/data/2.5/weather?q="+ params.get(KeyName.CITY_NAME) +"&APPID=a2789f0a017717b5a2032324a472943c";


        GetRequest getSanboxdata = (GetRequest) networkHandler.getRequestObject(Constants.NetworkRequestType.GET, mResponseType, lUrl, new ResponseListener(this), new ErrorResponseListener(this), requestParams);
        Log.d(TAG, "List Data Request() ->  Url=" + lUrl);
        getSanboxdata.setPriority(priority);
        getSanboxdata.setRetryPolicy(new DefaultRetryPolicy(AppConstants.SOCKET_TIMEOUT_TIME, AppConstants.RE_TRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        priorityJobQueue.addToRequestQueue(getSanboxdata, TagConstants.TAG_CONTENT_RESPONSE_TYPE);
    }

    /**
     * Post Method added for Demonstration
     * @param requestParams
     * @param mResponseType
     * @param priority
     */
    private void postRequest(Object requestParams, IHttpConnection.IResponseObserver.RequestTypeEnum mResponseType, Request.Priority priority) {
        Log.v(TAG, "postRequestpostRequest()-->");
        if (requestParams instanceof HashMap) {
            Log.v(TAG, "postRequest postRequest()-->requestParams check");
            HashMap<String, String> params = (HashMap<String, String>) requestParams;
            //TODO write the logic to make network call
            JSONObject luserRegistrationdata = new JSONObject();
          /*  try {
                luserRegistrationdata.put(KeyName.USER_NAME, params.get(KeyName.USER_NAME));
                luserRegistrationdata.put(KeyName.FIRST_NAME, params.get(KeyName.FIRST_NAME));
                luserRegistrationdata.put(KeyName.MIDLE_NAME, params.get(KeyName.MIDLE_NAME));
                luserRegistrationdata.put(KeyName.LAST_NAME, params.get(KeyName.LAST_NAME));
                luserRegistrationdata.put(KeyName.ACCOUNT_ID, "");
                luserRegistrationdata.put(KeyName.USER_PASSWORD, params.get(KeyName.USER_PASSWORD));
                luserRegistrationdata.put(KeyName.EMAIL_ID, params.get(KeyName.EMAIL_ID));
                luserRegistrationdata.put(KeyName.MOBILE_NUMBER, params.get(KeyName.MOBILE_NUMBER));
                luserRegistrationdata.put(KeyName.USER_TYPE, params.get(KeyName.USER_TYPE));
            } catch (JSONException ex) {
                Log.e(TAG, " isUserExist --> exception on encoding userdata exc=" + ex.toString());
            }*/

            String lUrl = "http://lyrics.wikia.com/api.php?func=getSong&artist=Tom+Waits&song=new+coat+of+paint&fmt=json";
            // String lUrl = "https://user-auth-service-uat.cfapps.scus-10.test.cf.fedex.com/v1/user/auth";
            int requestType = Constants.NetworkRequestType.POST;
            PostRequest authRequest = (PostRequest) networkHandler.getRequestObject(requestType, mResponseType, lUrl, new ResponseListener(this), new ErrorResponseListener(this), requestParams);
            authRequest.addHeader("Content-Type", "application/json");
            authRequest.addHeader("X-locale", "en_US"); // As per working param
            authRequest.addHeader("X-version", "1");
            authRequest.setRequestBody(luserRegistrationdata.toString());
            authRequest.setEncodingType("utf-8");
            authRequest.setPriority(priority);
            authRequest.setRetryPolicy(new DefaultRetryPolicy(AppConstants.SOCKET_TIMEOUT_TIME, AppConstants.RE_TRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            priorityJobQueue.addToRequestQueue(authRequest, TagConstants.TAG_POST_RESPONSE_TYPE);
        }
    }


    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onResponseHeaders(Map<String, String> headers, Object requestTAG) {

    }

    @Override
    public void onResponseObject(NetworkResponse response, Response<String> responseObject, Object requestTAG, Object requestParams) {

        Log.v(TAG, "Isemail onResponseObject()-->pre");
        Log.v("LogoutRequest", "onResponseObject()-->pre");
        Log.v("IsUser", "onResponseObject()-->pre");
        IHttpConnection.IResponseObserver.RequestTypeEnum mResponseTypeFromRequest = (IHttpConnection.IResponseObserver.RequestTypeEnum) requestTAG;
        //Reset if response received
        responseReceived(response.statusCode, responseObject.result, mResponseTypeFromRequest, requestParams);
        Log.v(TAG, "Isemail  onResponseObject()-->post");
        Log.v(TAG, "LogoutRequest onResponseObject()-->post");
        Log.v(TAG, "IsUser onResponseObject()-->post");
    }    // response definition methods

    private void responseReceived(int status, String body, IHttpConnection.IResponseObserver.RequestTypeEnum aRespType, Object requestParams) {

        Log.d(TAG, "response received resType is " + aRespType + " status " + status + " body message received =" + body);
        Log.d(TAG, "response received resType is " + aRespType + " status " + status + " body message received =" + body);
        switch (aRespType) {
            case GET_CITY_WEATHER_CONDITIONS:
                switch (status) {
                    case ResponseCodeConstants.FAILURE_CONNECTION:
                       Log.d(TAG, "GET_CITY_WEATHER_CONDITIONS responseReceived()-->FAILURE_CONNECTION");
//                        break;
                    case ResponseCodeConstants.INTERNAL_SERVER_ERROR:
                    case ResponseCodeConstants.FORBIDDEN:
                        Log.d(TAG, "GET_CITY_WEATHER_CONDITIONS responseReceived()-->INTERNAL_SERVER_ERROR");
//                        break;
                    case ResponseCodeConstants.UNAUTHORIZED:
                        Log.d(TAG, "AuthRequestCheck responseReceived()-->UNAUTHORIZED");
//                        break;
                    case ResponseCodeConstants.SUCCESS_OK:
                    case ResponseCodeConstants.NOT_MODIFIED:
                        ResponseParser.parseResponse(body,aRespType, requestParams);
                        break;
                    default:
                        Log.d(TAG, "AuthRequestCheck responseReceived()-->default");
                        break;
                }

                break;
            case POST_CONTENT:
                break;
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onErrorResponse(VolleyError error, Object requestTag) {

    }

    @Override
    public void onErrorResponse(VolleyError error, NetworkResponse response, Object requestTAG, Object requestParams) {
        IHttpConnection.IResponseObserver.RequestTypeEnum mResponseTypeFromRequest = (IHttpConnection.IResponseObserver.RequestTypeEnum) requestTAG;
        try {

            if (response == null || (response != null && (Integer) response.statusCode == null)) {
                responseReceived(-1, null, mResponseTypeFromRequest, requestParams);
            } else {
                responseReceived(response.statusCode, null, mResponseTypeFromRequest, requestParams);
            }
        } catch (Exception ex) {
            responseReceived(-1, null, mResponseTypeFromRequest, requestParams);
            ex.printStackTrace();
            Log.e(TAG, "error==" + ex.toString());
        }
    }
}
