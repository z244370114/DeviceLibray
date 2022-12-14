package com.zy.devicelibrary.utils;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.zy.devicelibrary.UtilsApp;

import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class LocationManagerUtils {


    LocationManager locationManager;
    public String longitude = "";
    public String latitude = "";
    public String address_details = "";
    public String city = "";
    public String provice = "";

    public LocationManagerUtils() {
        getNowLocation();
    }

    public void getNowLocation() {
        locationManager = (LocationManager) UtilsApp.getApp().getSystemService(LOCATION_SERVICE);
        Location location = getLastKnownLocation(locationManager);
        if (location != null) {
            longitude = String.valueOf(location.getLongitude());
            latitude = String.valueOf(location.getLatitude());
            Geocoder geocoder = new Geocoder(UtilsApp.getApp(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    provice = address.getAdminArea();
                    city = address.getLocality();
                    address_details = address.getAddressLine(0);
                }
            } catch (Exception e) {
                //可能会报异常，可能是因为手机不支持Google定位导致
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLastKnownLocation(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
            if (lastKnownLocation == null) {
                continue;
            }
            if (bestLocation == null || lastKnownLocation.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = lastKnownLocation;
            }
        }
        return bestLocation;
    }
}
