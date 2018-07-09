package com.illiyinmagang.miafandi.muslimhabitapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 31/05/2018.
 */

public class LocationConfig {
    private Context context;
    public static double LATITUDE, LONGITUTDE;
    private Geocoder geocoder;
    private List<Address> addresses;

    public LocationConfig(Context context) {
        this.context = context;
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    public void getAddress(double lat, double longi) {
        try {
            addresses = geocoder.getFromLocation(lat, longi, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCity() {
        return addresses.get(0).getAdminArea();
    }

    public String getCountry() {
        return addresses.get(0).getCountryName();
    }

    public String getPostCode() {
        return addresses.get(0).getPostalCode();
    }

    public String getAddressComplete() {
        return addresses.get(0).getAddressLine(0);
    }

    public String getSubAdminArea() {
        return reFormat(addresses.get(0).getSubAdminArea());

    }

    public String getLocality() {
        return addresses.get(0).getLocality();
    }

    public String reFormat(String kota){
        char[] kata = kota.toCharArray();
        String tempKota = "";
        int i = 0;
        while (i < kata.length){
            if(kata[i] != ' '){
                i = i+1;
            }else{
                i++;
                break;
            }
        }

        while (i < kata.length){
            tempKota = tempKota + kata[i];
            Log.e("formatLokasi",tempKota+"-"+i);
            i++;
        }
        return tempKota;
    }
}
