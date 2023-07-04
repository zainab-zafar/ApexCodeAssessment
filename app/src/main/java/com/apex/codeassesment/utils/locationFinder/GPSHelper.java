package com.apex.codeassesment.utils.locationFinder;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

@SuppressWarnings("MissingPermission")
public class GPSHelper {
    private final IGPSActivity main;
    private final LocationListener mlocListener;
    private final LocationManager mlocManager;


    public GPSHelper(IGPSActivity main) {
        this.main = main;
        mlocManager = (LocationManager) ((Activity) this.main).getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 4, mlocListener);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 4, mlocListener);
    }

    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            GPSHelper.this.main.locationChanged(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }

}