package wm.virtusa.com.weathermap.ui;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import wm.virtusa.com.weathermap.R;
import wm.virtusa.com.weathermap.gateway.api.ApiCallInterface;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static Context mCxt ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWeatherFragment();
        mCxt = getApplicationContext();
    }

    private void loadWeatherFragment()
    {
        WeatherFragment weatherFragment= new WeatherFragment();
        String tag = "WeatherFragment";
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.container_weather_activity, weatherFragment, tag).commit();
        Log.d(TAG, "loadWeatherFragment() -> All Layout loaded ");
    }


    public static Context getmCxt() {
        return mCxt;
    }

    public void setmCxt(Context mCxt) {
        this.mCxt = mCxt;
    }
}
