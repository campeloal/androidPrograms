package com.example.alcampelo.swipeviews.model;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;

import java.util.List;

/**
 * Created by Al Campelo on 4/13/2015.
 */
public class FindLocation implements LocationListener {

    LocationManager mLocationManager;
    Context context;
    ActionBarActivity activity;

    public FindLocation(Context context, ActionBarActivity activity)
    {
        this.context = context;
        this.activity = activity;
        mLocationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        int oneHour = 36000000;

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,oneHour,
                0, this);
    }


    public void showWeather() {
        android.location.Location l = getLastKnownLocation();

        if(l!=null)
        {
            //get latitude and longitude of the location
            double lng=l.getLongitude();
            double lat=l.getLatitude();

            mLocationManager.removeUpdates(this);

            QueryWeather qw = new QueryWeather(activity,context,lat,lng);
            qw.queryWeather();
        }
        else
        {
            System.out.println("Enable the fucking provider");
        }
    }

    private android.location.Location getLastKnownLocation() {
        List<String> providers = mLocationManager.getProviders(true);
        android.location.Location bestLocation = null;
        for (String provider : providers) {
            android.location.Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        showWeather();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        showWeather();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
