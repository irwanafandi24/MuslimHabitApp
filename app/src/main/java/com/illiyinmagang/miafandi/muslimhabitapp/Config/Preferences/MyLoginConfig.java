package com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.illiyinmagang.miafandi.muslimhabitapp.model.User;

/**
 * Created by user on 06/06/2018.
 */

public class MyLoginConfig {
    private SharedPreferences sharedPreferences;
    public final String KEY_USERNAME = "USERNAME";
    public final String KEY_NAMA = "NAMA";
    public final String KEY_EMAIL = "EMAIL";
    public final String KEY_PASS = "PASS";
    public final String KEY_AUTH = "AUTH";
    public final String KEY_ID = "ID";

    public final String POSITION = "MY_SESSION";
    private SharedPreferences.Editor editor;
    public MyLoginConfig(Context context) {
        this.sharedPreferences = context.getSharedPreferences(POSITION, Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void noteIntro(User user){
        editor.putInt(KEY_ID,user.getId());
        editor.putString(KEY_USERNAME,user.getUsername());
        editor.putString(KEY_PASS,user.getPassword());
        editor.putString(KEY_EMAIL,user.getEmail());
        editor.putString(KEY_NAMA,user.getNama());
        editor.putBoolean(KEY_AUTH,true);
        editor.commit();
    }

    public void noteIntro(String user){
        editor.putString(KEY_USERNAME,user);
        editor.putBoolean(KEY_AUTH,true);
        editor.commit();
    }

    public boolean isLogedIn(){
        return sharedPreferences.getBoolean(KEY_AUTH,false);
    }

    public void deleteSession(){
        editor.clear();
        editor.commit();
    }

    public String getDataString(String key){
        return sharedPreferences.getString(key,"");
    }

    public Integer getDataID(String key){
        return sharedPreferences.getInt(key,0);
    }
}
