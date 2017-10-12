package wm.virtusa.com.weathermap.ui.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Vector;

import wm.virtusa.com.weathermap.R;
import wm.virtusa.com.weathermap.model.WeatherData;
import wm.virtusa.com.weathermap.application.AppConstant;


/**
 * Created by Bikash on 10/11/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private static final String TAG = WeatherAdapter.class.getSimpleName();
    private Context mContext;
    private Vector<WeatherData> mAdapterItemList = new Vector<WeatherData>();
    private View mView;

    public WeatherAdapter(Activity mContext, Vector<WeatherData>aAdapterItemList) {
        this.mContext = mContext;
        this.mAdapterItemList = aAdapterItemList;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.weather_rcylr_item, parent, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Log.d(TAG , "onBindViewHolder" );
        if (holder!=null && holder instanceof WeatherViewHolder ) {
           /* if (position == mAdapterItemList.size()) {
                holder.mItemLayout.setVisibility(View.GONE);
            }*/
            try
            {
                WeatherData.Coordinates coordinates = mAdapterItemList.get(position).getCoord();
                WeatherData.Weather[] weather = mAdapterItemList.get(position).getWeather();
                WeatherData.Main main = mAdapterItemList.get(position).getMain();

                holder.mCityNameTextView.setText(mAdapterItemList.get(position).getName());
                holder.mLatLong.setText("Lat "+coordinates.getLat() + ",Long:" + coordinates.getLon() );
                holder.ETAtextView.setText(weather[0].getDescription());
                holder.mShipperLogoTxt.setText("Temp:"+main.getTemp());
                if(weather !=null) {
                    String icon = AppConstant.ICON_URI + weather[0].getIcon() + ".png";
                    Glide.with(mContext).load(icon)
                            .thumbnail(0.5f)
                            .crossFade()
                            .fitCenter()
                            .override(1000, 1000)
                            .placeholder(R.drawable.icon_icon_product_place_holder)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.mWeatherImageView);
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
                holder.mWeatherImageView.setImageResource(R.drawable.icon_icon_product_place_holder);
            }
        }

    }
    @Override
    public void onViewAttachedToWindow(WeatherViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }
    @Override
    public int getItemCount() {
        if (mAdapterItemList.size()!=0)
            return mAdapterItemList.size() + 1;
        else
            return 0;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void updateAdapterList(Vector<WeatherData> aRecyclerItemList) {

//        for(WeatherData weatherData : aRecyclerItemList) {
//            this.mAdapterItemList.add(weatherData);
//        }

        this.mAdapterItemList.addAll(0,aRecyclerItemList);
//        this.mAdapterItemList = aRecyclerItemList;
        this.notifyDataSetChanged();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        private ImageView mWeatherImageView;
        private TextView mShipperLogoTxt;
        private TextView mCityNameTextView;
        private RelativeLayout mItemListView;
        private RelativeLayout mItemLayout;
        private TextView ETAtextView;
        private TextView mLatLong ;

        private View itemViewWM;


        public WeatherViewHolder(View itemView) {
            super(itemView);

            itemViewWM = itemView;
            itemView.setClickable(true);
            mWeatherImageView = (ImageView) itemView.findViewById(R.id.weather_imgView);
            mLatLong = (TextView) itemView.findViewById(R.id.latlong);
            mShipperLogoTxt = (TextView) itemView.findViewById(R.id.climatetext);
            mCityNameTextView = (TextView) itemView.findViewById(R.id.citi_name);
            ETAtextView = (TextView) itemView.findViewById(R.id.weatherforcast);
            mItemLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
