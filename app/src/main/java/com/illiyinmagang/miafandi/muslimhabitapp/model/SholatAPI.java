package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 06/06/2018.
 */

public class SholatAPI {
    private String URL = "http://muslimsalat.com/";
    private List<SholatWajib> sholats;
    private AndroidNetworking androidNetworking;
    private Context context;

    public SholatAPI(String kota, Context context) {
        this.URL = this.URL+kota+"/yearly.json?key=api_key";
        this.sholats = new ArrayList();
        this.context = context;
    }

    public SholatWajib getShalatofDay(int i){
        return sholats.get(i);
    }

    public void setJadwalSholat1Year(){
        this.androidNetworking.initialize(context);
        Log.e("berhasil1",this.URL);
        androidNetworking.get(this.URL)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("berhasil2","berhasil2");
                try {
                    JSONArray items = response.getJSONArray("items");;
                    JSONObject jadwal;
                    for (int i = 0; i < items.length(); i++) {
                        jadwal = items.getJSONObject(i);
                        sholats.add(
                                //nambah jadwal tiap hari
                                new SholatWajib(
                                        //nambah untuk semua sholat wajib
                                        new Sholat(jadwal.getString("Subuh"),
                                                "",
                                                jadwal.getString("fajr"),
                                                0),
                                        new Sholat(jadwal.getString("Duhur"),
                                                "",
                                                jadwal.getString("dhuhr"),
                                                0),
                                        new Sholat(jadwal.getString("Ashar"),
                                                "",
                                                jadwal.getString("asr"),
                                                0),
                                        new Sholat(jadwal.getString("Maghrib"),
                                                "",
                                                jadwal.getString("maghrib"),
                                                0),
                                        new Sholat(jadwal.getString("Isya"),
                                                "",
                                                jadwal.getString("isha"),
                                                0)
                                ).setTanggal(jadwal.getString("date_for"))
                        );
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("error_nao",anError.getErrorDetail());
            }
        });


//        try {
//            JSONObject forcast = new JSONObject(URL);
//            JSONArray items = forcast.getJSONArray("items");;
//            JSONObject jadwal;
//            for (int i = 0; i < items.length(); i++) {
//                jadwal = items.getJSONObject(i);
//
//                sholats.add(
//                        //nambah jadwal tiap hari
//                        new SholatWajib(
//                                //nambah untuk semua sholat wajib
//                                new Sholat(jadwal.getString("Subuh"),
//                                        "",
//                                        jadwal.getString("fajr"),
//                                        0),
//                                new Sholat(jadwal.getString("Duhur"),
//                                        "",
//                                        jadwal.getString("dhuhr"),
//                                        0),
//                                new Sholat(jadwal.getString("Ashar"),
//                                        "",
//                                        jadwal.getString("asr"),
//                                        0),
//                                new Sholat(jadwal.getString("Maghrib"),
//                                        "",
//                                        jadwal.getString("maghrib"),
//                                        0),
//                                new Sholat(jadwal.getString("Isya"),
//                                        "",
//                                        jadwal.getString("isha"),
//                                        0)
//                        ).setTanggal(jadwal.getString("date_for"))
//                );
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
