package wm.virtusa.com.weathermap.db;

import android.content.Context;


import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Bikash on 10/11/2017.
 */
public class DBManager {

    public final static int CITIES_DBTYPE = 0x01;
    private static final String TAG = DBManager.class.getCanonicalName() ;

    private static DBManager mInstance = null;
    private CityDAO mCityDao = null;
    Context mContext;
    private DBManager(Context aContext) {
        mContext = aContext;
        mCityDao = CityDAO.getInstance(aContext);
    }
    public static DBManager getInstance(Context aCxt) {
        if(mInstance == null) {
            mInstance = new DBManager(aCxt);
        }
        return mInstance;
    }

    public void addtoDB(int aDBtype , List aDataItems , HashMap extras) {
        switch (aDBtype) {
            case CITIES_DBTYPE:
                for(int i= 0 ; i < aDataItems.size() ; i++) {
                    String lCityInfo = (String) aDataItems.get(i);
                    if(lCityInfo!= null) {
                        if(!mCityDao.isCityAvailable(lCityInfo)) {
                            mCityDao.addCityInfo(lCityInfo);
                        }
                    }

                }

                break;
        }
    }


    public Vector getAllDatatFromDB(int aDbType , HashMap extras) {
        Vector lDaTa  = new Vector();
        switch (aDbType) {
            case  CITIES_DBTYPE :
                lDaTa = mCityDao.getCities();
                break;
        }
        return lDaTa;
    }

    public boolean deleteFromDb(int aDBType , Object aObject) {
        switch (aDBType) {
            case CITIES_DBTYPE:
                break;
        }
        return false;
    }
}
