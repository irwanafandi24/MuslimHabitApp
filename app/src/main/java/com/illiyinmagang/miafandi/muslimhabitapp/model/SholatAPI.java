package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by user on 06/06/2018.
 */

public class SholatAPI {
    private String URL;
    private Context context;
    public ArrayList<SholatWajib> sholats = new ArrayList();
    public ArrayList<SholatWajib> sholatA = new ArrayList();
    private Realm realm;
    private RealmResults<SholatWajib>sholatWajibs;
    private AndroidNetworking androidNetworking;

    public SholatAPI(String kota, Context context) {
        Log.e("kota", kota);
        if (kota == "") {
            this.URL = "http://muslimsalat.com/malang/yearly.json?key=api_key";
        } else {
            this.URL = "http://muslimsalat.com/" + kota + "/yearly.json?key=api_key";
        }
        this.context = context;
        this.realm = Realm.getDefaultInstance();
    }

    public SholatAPI() {
        this.realm = Realm.getDefaultInstance();
    }

    public SholatWajib getShalatofDay(int i) {
        return this.sholatA.get(i);
    }

    public void setJadwalSholat1Year() {
        final ProgressDialog progressBar = new ProgressDialog(context);
        progressBar.setMessage("Mohon Menunggu");
        progressBar.show();
//        AndroidNetworking.initialize(context);
//        progressBar.show();
//        AndroidNetworking.post(this.URL)
//                .setTag("test")
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray items = response.getJSONArray("items");
//                            for (int i = 0; i < items.length(); i++) {
//                                JSONObject jadwal = items.getJSONObject(0);
//
//                                final SholatWajib sholatWajib = new SholatWajib(
//                                        //nambah untuk semua sholat wajib
//                                        new Sholat("Subuh",
//                                                "",
//                                                jadwal.getString("fajr"),
//                                                0),
//                                        new Sholat("Duhur",
//                                                "",
//                                                jadwal.getString("dhuhr"),
//                                                0),
//                                        new Sholat("Ashar",
//                                                "",
//                                                jadwal.getString("asr"),
//                                                0),
//                                        new Sholat("Maghrib",
//                                                "",
//                                                jadwal.getString("maghrib"),
//                                                0),
//                                        new Sholat("Isya",
//                                                "",
//                                                jadwal.getString("isha"),
//                                                0)
//                                ).setTanggal(jadwal.getString("date_for"));
//
//                                realm.executeTransaction(new Realm.Transaction() {
//                                    @Override
//                                    public void execute(Realm realm) {
//                                        Number currentIdNum = realm.where(SholatWajib.class).max("id");
//                                        int nextId;
//                                        if (currentIdNum == null) {
//                                            nextId = 1;
//                                        } else {
//                                            nextId = currentIdNum.intValue() + 1;
//                                        }
//                                        sholatWajib.setId(nextId);
//                                        realm.copyToRealm(sholatWajib);
//                                    }
//                                });
//                            }
//                            progressBar.dismiss();
//                        } catch (JSONException e) {
////                            e.printStackTrace();
//                            Log.e("error_nao", e.getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
////                        anError.printStackTrace();
//                        Log.e("error_nao", anError.getErrorDetail());
//                    }
//
//                });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                this.URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray items = null;
                        try {
                            items = response.getJSONArray("items");

                            realm.beginTransaction();
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
                                Log.v("sholat",sholatWajib+"");

                                        Number currentIdNum = realm.where(SholatWajib.class).max("id");
                                        Log.v("currentID",currentIdNum+"");
                                        int nextId;
                                        if (currentIdNum == null) {
                                            nextId = 1;
                                        } else {
                                            nextId = currentIdNum.intValue() + 1;
                                        }
                                        sholatWajib.setId(nextId);
                                        realm.copyToRealm(sholatWajib);


                            }
                            realm.commitTransaction();
                            progressBar.dismiss();
                        } catch (JSONException e) {
                            Log.e("volley",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley",error.getMessage());
            }
        });

        RequestHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);
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

    public int getDataPosistionByDate(String tgl) {
        int a = 0;
        sholatWajibs = realm.where(SholatWajib.class).findAll();
        sholatA = new ArrayList(sholatWajibs);

        while (a < sholatA.size() &&
                !sholatA.get(a).getTanggalLengkap().equals(tgl)
                ) {
            a++;
        }
        return a;
    }

}