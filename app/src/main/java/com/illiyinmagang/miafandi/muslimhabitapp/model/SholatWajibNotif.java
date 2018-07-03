package com.illiyinmagang.miafandi.switchrealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SholatWajibNotif extends RealmObject {
    @PrimaryKey
    private int id;
    private String namaSholat;
    private boolean switchSholat;

    public SholatWajibNotif() {
    }

    public SholatWajibNotif(String namaSholat, boolean switchSholat) {
        this.namaSholat = namaSholat;
        this.switchSholat = switchSholat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaSholat() {
        return namaSholat;
    }

    public void setNamaSholat(String namaSholat) {
        this.namaSholat = namaSholat;
    }

    public boolean isSwitchSholat() {
        return switchSholat;
    }

    public void setSwitchSholat(boolean switchSholat) {
        this.switchSholat = switchSholat;
    }
}
