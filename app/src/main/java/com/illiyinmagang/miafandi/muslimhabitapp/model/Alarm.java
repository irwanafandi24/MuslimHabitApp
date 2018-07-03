package com.illiyinmagang.miafandi.muslimhabitapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {
    @PrimaryKey
    int idAlarm;

    public Alarm() {
    }

    String waktuAlaram;



    public Alarm(String waktuAlaram) {
        this.waktuAlaram = waktuAlaram;
    }

    public String getWaktuAlaram() {
        return waktuAlaram;
    }

    public void setWaktuAlaram(String waktuAlaram) {
        this.waktuAlaram = waktuAlaram;
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int idAlarem) {
        this.idAlarm = idAlarem;
    }
}
