package com.illiyinmagang.miafandi.muslimhabitapp.model;

import io.realm.RealmObject;

public class Sholat extends RealmObject{
    private String namaSholat, waktuTunggu, jamSholat, tanggal;
    private int image;

    public Sholat(String namaSholat, String waktuTunggu, String jamSholat, int image) {
        this.namaSholat = namaSholat;
        this.waktuTunggu = waktuTunggu;
        this.jamSholat = jamSholat;
        this.image = image;
    }

    public Sholat() {
    }

    public Sholat(String namaSholat, String waktuTunggu, String jamSholat, String tanggal, int image) {
        this.namaSholat = namaSholat;
        this.waktuTunggu = waktuTunggu;
        this.jamSholat = jamSholat;
        this.tanggal = tanggal;
        this.image = image;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNamaSholat() {
        return namaSholat;
    }

    public void setNamaSholat(String namaSholat) {
        this.namaSholat = namaSholat;
    }

    public String getWaktuTunggu() {
        return waktuTunggu;
    }

    public void setWaktuTunggu(String waktuTunggu) {
        this.waktuTunggu = waktuTunggu;
    }

    public String getJamSholat() {
        return jamSholat;
    }

    public void setJamSholat(String jamSholat) {
        this.jamSholat = jamSholat;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
