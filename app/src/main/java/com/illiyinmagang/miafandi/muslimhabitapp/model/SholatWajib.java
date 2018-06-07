package com.illiyinmagang.miafandi.muslimhabitapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 06/06/2018.
 */

public class SholatWajib {
    private List<Sholat> sholatWajib;
    private String tanggal;

    public SholatWajib(Sholat subuh,Sholat duhur, Sholat ashar, Sholat maghrib, Sholat isya) {
        sholatWajib = new ArrayList();
        sholatWajib.add(subuh);
        sholatWajib.add(duhur);
        sholatWajib.add(ashar);
        sholatWajib.add(maghrib);
        sholatWajib.add(isya);
    }

    public Sholat getSholat(int i){
        return sholatWajib.get(i);
    }

    public SholatWajib setTanggal(String tanggal) {
        this.tanggal = tanggal;
        return this;
    }
}
