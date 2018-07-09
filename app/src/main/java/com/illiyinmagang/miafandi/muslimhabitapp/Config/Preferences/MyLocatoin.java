package com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 06/06/2018.
 */

public class MyLocatoin {
    private SharedPreferences sharedPreferences;
    public final String KEY_LOCATION = "LOCATION";
    public final String LOCATION = "MY_LOCATION";

    public MyLocatoin(Context context) {
        this.sharedPreferences = context.getSharedPreferences(LOCATION, Context.MODE_PRIVATE);
    }

    public void noteMyLocation(String kota){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOCATION,kota);
        editor.commit();
    }

    public String getMynotedLocation(){
        return sharedPreferences.getString(KEY_LOCATION,"malang");
    }

}
