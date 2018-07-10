package com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 06/06/2018.
 */

public class MyIntroConfig {
    private SharedPreferences sharedPreferences;
    public final String KEY = "INTRO";
    public final String POSITION = "MY_INTRO";

    public MyIntroConfig(Context context) {
        this.sharedPreferences = context.getSharedPreferences(POSITION, Context.MODE_PRIVATE);
    }

    public void noteIntro(boolean bol){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY,bol);
        editor.commit();
    }

    public boolean getMyIntro(){
        return sharedPreferences.getBoolean(KEY,false);
    }

}
