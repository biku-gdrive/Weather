package wm.virtusa.com.weathermap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class WMDBHelper extends SQLiteOpenHelper{

	//TABLE  - Main Table - MM_item

	private static final String TAG = WMDBHelper.class.getCanonicalName();

	private static WMDBHelper mInstance = null;
	private static final String DATABASE_NAME = "wmweather.db";
	private static final int DATABASE_VERSION = 1;

	/**
	 * DB File names
	 */
	public static final String TABLE_CITIES = "city_db";
	public static final String COLUMN_CITY_NAME = "city_name";

	// Table creation sql statement
	private static final String TABLE_CITY_CREATE =
			"create table if not exists " + TABLE_CITIES +
			"(" +

					COLUMN_CITY_NAME + " text , " +
			"primary key (" + COLUMN_CITY_NAME + ") );";


	public static WMDBHelper getInstance(Context context) {
	    if (mInstance == null) {
	      mInstance = new WMDBHelper(context);
	    }
	    return mInstance;
	}
	  
	private WMDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	private WMDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "oncreate() creating table" );
		db.execSQL(TABLE_CITY_CREATE);
		Log.d(TAG, "oncreate()-> database::::: " +TABLE_CITY_CREATE + "tables created::" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
