package com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 06/06/2018.
 */

public class MyDateSelected {
    private SharedPreferences sharedPreferences;
    public final String KEY_POSITION = "POSITION";
    public final String POSITION = "MY_POSITION";

    public MyDateSelected(Context context) {
        this.sharedPreferences = context.getSharedPreferences(POSITION, Context.MODE_PRIVATE);
    }

    public void notePosition(int position){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_POSITION,position);
        editor.commit();
    }

    public int getMyPosisition(){
        return sharedPreferences.getInt(KEY_POSITION,0);
    }

}
