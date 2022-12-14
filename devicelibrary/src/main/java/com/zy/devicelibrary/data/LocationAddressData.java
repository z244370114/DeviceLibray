package com.zy.devicelibrary.data;

import android.text.TextUtils;

import com.zy.devicelibrary.utils.FileUtils;
import com.zy.devicelibrary.utils.LocationManagerUtils;

public class LocationAddressData {

    String longitude = "";
    String latitude = "";
    String address_details = "";
    String city = "";
    String provice = "";


    public LocationAddressData() {
        LocationManagerUtils locationManagerUtils = new LocationManagerUtils();
        this.latitude = locationManagerUtils.latitude;
        this.longitude = locationManagerUtils.longitude;
        this.address_details = locationManagerUtils.address_details;
        this.city = locationManagerUtils.city;
        this.provice = locationManagerUtils.provice;
    }


    public boolean locationIsNull() {
        if (TextUtils.isEmpty(longitude) && TextUtils.isEmpty(longitude)) {
            return true;
        } else {
            return false;
        }
    }

}
