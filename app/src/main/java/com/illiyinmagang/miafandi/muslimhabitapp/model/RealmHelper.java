package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //save data
    public void saveData(final SholatWajibNotif sholatWajib){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(realm != null){
                    Log.e("Database Detail: ","Database have been created before");
                    Number currentData = realm.where(SholatWajibNotif.class).max("id");
                    int numberId;
                    if(currentData==null){
                        numberId = 1;
                    }else{
                        numberId = currentData.intValue()+1;
                    }
                    sholatWajib.setId(numberId);
                    SholatWajibNotif p = realm.copyToRealm(sholatWajib);
                }else{
                    Log.e("Database Detail: ","Database not exis");
                }
            }
        });
    }

    //se;ect all data
    public List<SholatWajibNotif> getAllSholat(){
        RealmResults<SholatWajibNotif> sholatRisalt = realm.where(SholatWajibNotif.class).findAll();
        return sholatRisalt;
    }

    //Update Data
    public void updateSholat(final int id, final String nameSholat, final Boolean switched){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SholatWajibNotif sholatWajib = realm.where(SholatWajibNotif.class).equalTo("id",id).findFirst();
                sholatWajib.setNamaSholat(nameSholat);
                sholatWajib.setSwitchSholat(switched);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.f
                Log.v("UPDATE","Success update data");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("UPDATE ERROR : ",error.getMessage());
            }
        });
    }

    public void saveAlarem(final Alarm alarm){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(realm != null){
                    Log.e("Database Detail: ","Database have been created before");
                    Number currentData = realm.where(Alarm.class).max("idAlarm");
                    int numberId;
                    if(currentData==null){
                        numberId = 1;
                    }else{
                        numberId = currentData.intValue()+1;
                    }
                    alarm.setIdAlarm(numberId);
                    Log.e("Database Detail: ","Database have been created before");
                    Alarm p = realm.copyToRealm(alarm);
                }else{
                    Log.e("Database Detail: ","Database not exis");
                }
            }
        });
    }

    public List<Alarm> getAllAlarm(){
        RealmResults<Alarm> alarmRealmResults = realm.where(Alarm.class).findAll();
        return alarmRealmResults;
    }

    public void updateAlarem(final int id, final String newTime){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Alarm alarmSholat = realm.where(Alarm.class).equalTo("idAlarm",id).findFirst();
                alarmSholat.setWaktuAlaram(newTime);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.f
                Log.v("UPDATE","Success update data - Waktu");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.e("UPDATE ERROR : ",error.getMessage());
            }
        });
    }
}
