package com.illiyinmagang.miafandi.muslimhabitapp.model;

import io.realm.RealmObject;

public class Alarem extends RealmObject {
    int waktuAlaram;

    public int getWaktuAlaram() {
        return waktuAlaram;
    }

    public void setWaktuAlaram(int waktuAlaram) {
        this.waktuAlaram = waktuAlaram;
    }
}
