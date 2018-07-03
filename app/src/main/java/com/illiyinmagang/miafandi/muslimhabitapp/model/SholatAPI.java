package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyJSONListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by user on 06/06/2018.
 */

public class SholatAPI {
    private String URL;
    public ArrayList<SholatWajib> sholats = new ArrayList();
    public ArrayList<SholatWajib> sholatA = new ArrayList();
    private Realm realm;
    private RealmResults<SholatWajib> sholatWajibs;

    public SholatAPI(String kota, Context context) {
        Log.e("kota", kota);
        if (kota == "") {
            this.URL = "http://muslimsalat.com/malang/yearly.json?key=api_key";
        } else {
            this.URL = "http://muslimsalat.com/" + kota + "/yearly.json?key=api_key";
        }
        this.realm = Realm.getDefaultInstance();
    }

    public SholatAPI() {
        this.realm = Realm.getDefaultInstance();
    }

    public SholatWajib getShalatofDay(int i) {
        return this.sholatA.get(i);
    }

    public void setJadwalSholat1Year() {
        AndroidNetworking.get(this.URL)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject jadwal = items.getJSONObject(i);

                                final SholatWajib sholatWajib = new SholatWajib(
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
                                ).setTanggal(jadwal.getString("date_for"));

//                                    Number currentIdNum = realm.where(SholatWajib.class).max("id");
//                                    int nextId;
//                                    if (currentIdNum == null) {
//                                        nextId = 1;
//                                    } else {
//                                        nextId = currentIdNum.intValue() + 1;
//                                    }
//                                    sholatWajib.setId(nextId);
//                                    realm.copyToRealm(sholatWajib);

                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Number currentIdNum = realm.where(SholatWajib.class).max("id");
                                        int nextId;
                                        if (currentIdNum == null) {
                                            nextId = 1;
                                        } else {
                                            nextId = currentIdNum.intValue() + 1;
                                        }
                                        sholatWajib.setId(nextId);
                                        realm.copyToRealm(sholatWajib);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("error_nao", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error_nao", anError.getErrorDetail());
                    }

                });
    }

    public ArrayList<SholatWajib> getDataShalat() {
        sholatWajibs = realm.where(SholatWajib.class).findAll();
        if (sholatWajibs.size() > 0) {
            sholatA = new ArrayList(sholatWajibs);
        } else {
            return this.sholatA;
        }
        return this.sholatA;
    }

    private void showLog(String s) {
        Log.d("naon", s);

    }

    public int getDataPosistionByDate(String tgl) {
        int a = 0;
        sholatWajibs = realm.where(SholatWajib.class).findAll();
        sholatA = new ArrayList(sholatWajibs);

        Log.e("lol1", sholatA.get(2).getTanggalLengkap()+ ","+tgl);
        while (a < sholatA.size() &&
                !sholatA.get(a).getTanggalLengkap().equals(tgl)
                ) {
            a++;
        }
        return a;
    }

}
