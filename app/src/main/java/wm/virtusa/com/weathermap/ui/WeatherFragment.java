package wm.virtusa.com.weathermap.ui;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import wm.virtusa.com.weathermap.R;
import wm.virtusa.com.weathermap.db.DBManager;
import wm.virtusa.com.weathermap.gateway.api.ApiCallInterface;
import wm.virtusa.com.weathermap.gateway.utility.parser.ResponseParser;
import wm.virtusa.com.weathermap.model.WeatherData;
import wm.virtusa.com.weathermap.ui.adapter.WeatherAdapter;
import wm.virtusa.com.weathermap.ui.api.INotifyDataChange;
import wm.virtusa.com.weathermap.ui.widget.WMLinearLayoutManager;

/**
 * Created by Bikash on 10/11/2017.
 */

public class WeatherFragment extends Fragment implements View.OnTouchListener,View.OnKeyListener,
        TextView.OnEditorActionListener,Dialog.OnClickListener,INotifyDataChange{

    private static final String TAG = WeatherFragment.class.getSimpleName();
    private RecyclerView mRecyclerViewScroll;
    private EditText cityEditText;
    private ImageButton citySearchButton;
    private LinearLayout searchBar;
    private String searchCity;
    private Vector<WeatherData> mRecyclerItemList = new Vector<>();
    private WeatherAdapter mWeatherAdapter;

    private RelativeLayout welcomeHolder;
    private WMLinearLayoutManager mWMLinearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ResponseParser.registerListner(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.weather_view, container, false);
        prepareView(mView);
        prepareRecyclerView();
        Vector<String> cities = DBManager.getInstance(getContext()).getAllDatatFromDB(DBManager.CITIES_DBTYPE,null);
        for(String city : cities) {
            ApiCallInterface.getInstance().getWeatherContent(city);
        }
        return mView;
    }

    private void prepareRecyclerView() {
        mWeatherAdapter = new WeatherAdapter(getActivity(), mRecyclerItemList);

        WMLinearLayoutManager wmLayout = new WMLinearLayoutManager(getContext());
        mRecyclerViewScroll.setLayoutManager(wmLayout);

        mRecyclerViewScroll.setAdapter(mWeatherAdapter);
        mRecyclerViewScroll.setNestedScrollingEnabled(true);
        mRecyclerViewScroll.setHasFixedSize(true);
    }

    private void prepareView(View mView) {

        cityEditText = (EditText)mView.findViewById(R.id.searchcityinput);
        cityEditText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Univers-Light.otf"));
        citySearchButton = (ImageButton)mView.findViewById(R.id.search_btn);

        citySearchButton.setOnClickListener(null);

        cityEditText.setOnKeyListener(this);
        cityEditText.setOnEditorActionListener(this);

        mRecyclerViewScroll = (RecyclerView) mView.findViewById(R.id.recycler_view);
        searchBar = (LinearLayout)mView.findViewById(R.id.searchbar);

        welcomeHolder = (RelativeLayout)mView.findViewById(R.id.welcome_holder);

        mWMLinearLayoutManager = new WMLinearLayoutManager(getActivity());
        mWMLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if ((event.getAction() == KeyEvent.ACTION_DOWN) && ((keyCode == KeyEvent.KEYCODE_ENTER) ||
                keyCode == EditorInfo.IME_ACTION_DONE ||
                keyCode == EditorInfo.IME_ACTION_GO || keyCode == EditorInfo.IME_ACTION_SEARCH)) {
//                    Toast.makeText(getActivity(), trackingNumberEditText.getText(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "In Keyboard Pressed");
            searchCity = cityEditText.getText().toString();
            ArrayList<String> cities = new ArrayList<String>();
            cities.add(searchCity);
            DBManager.getInstance(getContext()).addtoDB(DBManager.CITIES_DBTYPE,cities ,null);
            ApiCallInterface.getInstance().getWeatherContent(searchCity);
            resetSearchBar();
            hideKeyboard();
            Log.d(TAG, "In Keyboard Pressed");
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Log.d(TAG, "In Keyboard Pressed");
            searchCity = cityEditText.getText().toString();
            ArrayList<String> cities = new ArrayList<String>();
            cities.add(searchCity);
            DBManager.getInstance(getContext()).addtoDB(DBManager.CITIES_DBTYPE,cities ,null);
            ApiCallInterface.getInstance().getWeatherContent(searchCity);
            resetSearchBar();
            hideKeyboard();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    /**
     * void resetSearchBar()
     * Return type: void
     * This method erases all the characters in the input field makes it ready for next input.
     */

    private void resetSearchBar() {
        cityEditText.clearFocus();
        cityEditText.setText("");
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void notifyDataChange(final Vector<WeatherData> weatherData) {

        getActivity().runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {

                mRecyclerItemList = weatherData;
                mWeatherAdapter.updateAdapterList(mRecyclerItemList );
                mWeatherAdapter.notifyDataSetChanged();
            }

        }));
    }
}
