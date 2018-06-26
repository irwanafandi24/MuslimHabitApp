package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by user on 06/06/2018.
 */

public class SholatWajib extends RealmObject {

    @PrimaryKey
    private int id;
    private Sholat sholatsubuh;
    private Sholat sholatDuhur;
    private Sholat sholatAshar;
    private Sholat sholatMaghrib;
    private Sholat sholatIsya;
    private String tanggal;
    private String tgl;
    private String bulan;
    private String tahun;

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public SholatWajib(Sholat subuh,Sholat duhur, Sholat ashar, Sholat maghrib, Sholat isya) {
        sholatsubuh = subuh;
        sholatDuhur = duhur;
        sholatAshar = ashar;
        sholatMaghrib = maghrib;
        sholatIsya= isya;
    }

    public SholatWajib() {
    }

    public SholatWajib setTanggal(String tanggal) {
        this.tanggal = tanggal;
        char a[] = tanggal.toCharArray();
        char separator = '-';
        String tgl = "" ,bulan = "",tahun = "";
        int count = 0,i = 0;
        while (i < a.length){
            if(a[i] != separator){
                switch (count){
                    case 0:
                        tahun = tahun +a[i];
                        break;
                    case 1:
                        bulan = bulan +a[i];
                        break;
                    case 2:
                        tgl = tgl +a[i];
                        break;
                }

            }else {
                count++;
            }
        i++;
        }
        setSeparateDate(tgl,bulan,tahun);
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Sholat getSholatDuhur() {
        return sholatDuhur;
    }

    public Sholat getSholatAshar() {
        return sholatAshar;
    }

    public Sholat getSholatMaghrib() {
        return sholatMaghrib;
    }

    public Sholat getSholatIsya() {
        return sholatIsya;
    }

    public void setSeparateDate(String tgl, String bulan, String tahun){
        setTgl(tgl);
        setBulan(bulan);
        setTahun(tahun);
        Log.e("tanggal",tgl+"."+bulan+"."+tahun);
    }
    public String getTanggal() {
        return this.tgl;
    }
    public int getBulan(){
        return Integer.parseInt(this.bulan);
    };
    public String getTahun(){
        return this.tahun;
    };

    public Sholat getSholatsubuh() {
        return sholatsubuh;
    }
}
