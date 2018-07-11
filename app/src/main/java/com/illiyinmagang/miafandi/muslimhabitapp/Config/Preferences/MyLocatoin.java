package com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 06/06/2018.
 */

public class MyLocatoin {
    private SharedPreferences sharedPreferences;
    public final String KEY_LOCATION = "LOCATION";
    public final String KEY_CALCULATION = "CALCULATION";
    public final String KEYTEMP_LOCATION = "TEMP_LOCATION";
    public final String KEYTEMP_CALCULATION = "TEMP_CALCULATION";
    public final String LOCATION = "MY_LOCATION";
    public final String CALCULATION = "MY_CALCULATION";

    public final String TEMPLOCATION = "MYTEMP_LOCATION";

    public MyLocatoin(Context context) {
        this.sharedPreferences = context.getSharedPreferences(LOCATION, Context.MODE_PRIVATE);
        this.sharedPreferences = context.getSharedPreferences(CALCULATION, Context.MODE_PRIVATE);
    }

    public void noteMyCalculation(int metode){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_CALCULATION,metode);
        editor.commit();
    }

    public void noteMyLocation(String kota){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOCATION,kota);
        editor.commit();
    }

    public void updateMyLocation(String kota){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOCATION,kota);
        editor.putString(KEYTEMP_LOCATION,sharedPreferences.getString(KEY_LOCATION,"bandung"));
        editor.commit();
    }

    public String getMynotedLocation(){
        return sharedPreferences.getString(KEY_LOCATION,"bandung");
    }

    public int getMynotedCalculation(){
        return sharedPreferences.getInt(KEY_CALCULATION,2);
    }

    public String getMyTempnotedLocation(){
        return sharedPreferences.getString(KEYTEMP_LOCATION,"malang");
    }

}
