package com.illiyinmagang.miafandi.muslimhabitapp.Config;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by user on 07/06/2018.
 */

public class MyJSONListener implements JSONObjectRequestListener {

    public ArrayList<SholatWajib> sholatWajibs = new ArrayList();

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray items = response.getJSONArray("items");;
            JSONObject jadwal = null;
            for (int i = 0; i < items.length(); i++) {
                jadwal = items.getJSONObject(i);
                this.sholatWajibs.add(
                        //nambah jadwal tiap hari
                        new SholatWajib(
                                //nambah untuk semua sholat wajib
                                new Sholat("Subuh",
                                        "",
                                        jadwal.getString("fajr"),
                                        0),
                                new Sholat("Duhur",
                                        "",
                                        jadwal.getString("dhuhr"),
                                        0),
                                new Sholat("Ashar",
                                        "",
                                        jadwal.getString("asr"),
                                        0),
                                new Sholat("Maghrib",
                                        "",
                                        jadwal.getString("maghrib"),
                                        0),
                                new Sholat("Isya",
                                        "",
                                        jadwal.getString("isha"),
                                        0)
                        )
                                .setTanggal(jadwal.getString("date_for"))
                );
//                        Log.e("pop",sholats.get(i).getSholat(0).getJamSholat()+"");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(ANError anError) {

    }

    public ArrayList<SholatWajib> getSholatWajibs() {
        return this.sholatWajibs;
    }
}
