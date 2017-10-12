package wm.virtusa.com.weathermap.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.Vector;

/**
 * Created by Bikash on 10/11/2017.
 */
public class CityDAO {
    private static final String TAG = CityDAO.class.getCanonicalName();
    private static CityDAO mSelf = null;
    private WMDBHelper dbHelper;
    private SQLiteDatabase database;
    private Context mContext;
    private CityDAO(Context aContext) {
        mContext = aContext;
        dbHelper = WMDBHelper.getInstance(aContext);
        database = dbHelper.getWritableDatabase();
    }

    public static CityDAO getInstance(Context aCxt){
        if(mSelf == null) {
            mSelf = new CityDAO(aCxt);
        }
        return mSelf;
    }

    public Vector<String> getCities() {
        Vector <String> lCitiesData = new Vector<>();
        Cursor citiesCursor = database.query(WMDBHelper.TABLE_CITIES, null, null, null, null, null, null);
        citiesCursor.moveToFirst();
        while (!citiesCursor.isAfterLast())
        {
            lCitiesData.add(citiesCursor.getString(citiesCursor.getColumnIndexOrThrow(WMDBHelper.COLUMN_CITY_NAME)));
            citiesCursor.moveToNext();
        }

        return  lCitiesData;
    }

    //Kept for demonstration
   /* public boolean addStateInfo(States aStates , int aCountryId)  {
        boolean lContactAdded = false;
        long lStateInfoCount = database.insert(WMDBHelper.TABLE_STATES, null,
                getStateInfo( aStates , aCountryId));
        lContactAdded = true;
        return  lContactAdded;
    }*/
    public boolean addCityInfo(String aCity )  {
        boolean lContactAdded = false;
        long lCityInfoCount = database.insert(WMDBHelper.TABLE_CITIES, null,
                getCityInfo( aCity ));
        lContactAdded = true;
        return  lContactAdded;
    }

    public boolean isCityAvailable (String aCity) {
        boolean isExists = false;
        try {
            String selection =  WMDBHelper.COLUMN_CITY_NAME + "='" +aCity +"'";

            Cursor countryData = database.query(WMDBHelper.TABLE_CITIES, null, selection, null, null, null, null);
            countryData.moveToFirst();
            if (countryData.getCount() > 0) {
                isExists= true;
            }

            countryData.close();
        }catch(Exception ignored){

        }
        return  isExists;
    }
    private ContentValues getCityInfo ( String aCities ) {
        ContentValues values = new ContentValues();
        values.put(WMDBHelper.COLUMN_CITY_NAME, aCities);

        return  values;
    }

    private int convertBooleanToInt(boolean aValue) {
        return (aValue) ? 1 : 0;
    }
    public boolean convertIntToBoolean(int aValues) {
        return (aValues == 1) ? true : false;
    }

}
