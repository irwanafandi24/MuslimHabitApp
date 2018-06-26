package com.illiyinmagang.miafandi.muslimhabitapp.Config;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by user on 20/06/2018.
 */

public class ConfigDBLocal extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name("MuslimHabit.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
