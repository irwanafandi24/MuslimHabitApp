package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.ConfigDB;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLoginConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.RequestHandler;
import com.illiyinmagang.miafandi.muslimhabitapp.LoginActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.MainActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by user on 09/07/2018.
 */

public class ServerHelper {
    private Context context;
    private Realm realm = Realm.getDefaultInstance();
    private ProgressDialog progressDialog;

    public ServerHelper(Context context) {
        this.context = context;
    }

    public void InserServer(final String username, final String email, final String pass, final String nama){
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Waiting Register");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ConfigDB.INSERTUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("jsonku",jsonObject.getString("register"));
                            if(jsonObject.getString("register").equals("1")){
                                Toast.makeText(context,"Register Berhasil",Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, LoginActivity.class));
                                ((Activity)context).finish();
                            }else{
                                Toast.makeText(context,"Register Gagal",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("jsonku",error.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",pass);
                params.put("email",email);
                params.put("nama",nama);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void LoginUser(final String username, final String password){
        final SholatAPI sholatAPI = new SholatAPI(context);
        final MyLoginConfig myLoginConfig = new MyLoginConfig(context);

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Waiting for Login");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                ConfigDB.LOGINTUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("jsonku",jsonObject.getBoolean("error")+"");
                            if(jsonObject.getBoolean("error") == false){
                                myLoginConfig.noteIntro(new User(
                                        jsonObject.getJSONObject("msg").getString("username"),
                                        jsonObject.getJSONObject("msg").getString("email"),
                                        jsonObject.getJSONObject("msg").getString("password"),
                                        jsonObject.getJSONObject("msg").getString("nama"),
                                        jsonObject.getJSONObject("msg").getInt("id_user")
                                ));
                                Log.e("jsonku",jsonObject.getJSONObject("msg").getString("username")+"");
                                context.startActivity(new Intent(context, MainActivity.class));

                                Toast.makeText(context,"Login Berhasil"+jsonObject.getJSONObject("msg").getString("username")+jsonObject.getJSONObject("msg").getInt("id_user"),Toast.LENGTH_SHORT).show();
                                ((Activity)context).finish();
                            }else{
                                Toast.makeText(context,"Login Gagal",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("jsonku",error.getMessage());
                progressDialog.dismiss();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void InsertRekap(final String idUser, final String namaIbadah, final String waktu, final String tempat, final String jenis, final String badiyah, final String qobliyah){
        progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("Sedang merekap");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConfigDB.INSERTREKAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("LOL","LOL"+jsonObject.getBoolean("hasilRekap"));
                            if(jsonObject.getBoolean("hasilRekap")){
                                Toast.makeText(context,"Rekap Berhasil ke Server",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context,"Rekap Gagal ke Server",Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.e("LOL","LOL"+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("LOL","LOL"+error.getMessage());
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();

                        Log.d("LOL", "Failed with error msg:\t" + error.getMessage());
                        Log.d("LOL", "Error StackTrace: \t" + error.getStackTrace());
                        // edited here
                        try {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            Log.e("LOL", new String(htmlBodyBytes), error);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        if (error.getMessage() == null){
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("waktu",waktu);
                params.put("tempat",tempat);
                params.put("jenis",jenis);
                params.put("qobliyah",qobliyah);
                params.put("badiyah",badiyah);
                params.put("namaIbadah",namaIbadah.toLowerCase());
                params.put("idUser",idUser);
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    public void SelectRekap(final int idUser, final String namaIbadah, final String tanggal){
        progressDialog = new ProgressDialog(context);
//        final boolean[] a = new boolean[1];
//        a[0] = true;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConfigDB.SELECTREKAP,
                new Response.Listener<String>() {
//                    boolean b;

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("LOL","LOLini"+jsonObject.getBoolean("hasilRekap"));
                            if(jsonObject.getBoolean("hasilRekap")){
//                                a[0] = true;
                                Log.e("LOLBenar","LOLini"+jsonObject.getBoolean("hasilRekap"));
                                realm.beginTransaction();
                                SholatWajib sholatWajib = null;
                                RealmResults<SholatWajib> sholatWajibs = realm.where(SholatWajib.class).findAll();
                                for (int i = 0; i < sholatWajibs.size(); i++) {
                                    if(sholatWajibs.get(i).getTanggalLengkap().equals(tanggal)){
                                        sholatWajib = sholatWajibs.get(i);
                                        break;
                                    }
                                }

                                switch (namaIbadah){
                                    case "Subuh":
                                        sholatWajib.getSholatsubuh().setImage(R.drawable.ibadahhijauicon);
                                        break;
                                    case "Duhur":
                                        sholatWajib.getSholatDuhur().setImage(R.drawable.ibadahhijauicon);
                                        break;
                                    case "Ashar":
                                        sholatWajib.getSholatAshar().setImage(R.drawable.ibadahhijauicon);
                                        break;
                                    case "Maghrib":
                                        sholatWajib.getSholatMaghrib().setImage(R.drawable.ibadahhijauicon);
                                        break;
                                    case "Isya":
                                        sholatWajib.getSholatIsya().setImage(R.drawable.ibadahhijauicon);
                                        break;
                                }

                                realm.commitTransaction();

                            }else{
//                                a[0] = false;
                                Log.e("LOLBenar","LOLsalah"+jsonObject.getBoolean("hasilRekap"));
                                realm.beginTransaction();
                                SholatWajib sholatWajib = null;
                                RealmResults<SholatWajib> sholatWajibs = realm.where(SholatWajib.class).findAll();
                                for (int i = 0; i < sholatWajibs.size(); i++) {
                                    if(sholatWajibs.get(i).getTanggalLengkap().equals(tanggal)){
                                        sholatWajib = sholatWajibs.get(i);
                                        break;
                                    }
                                }

                                Log.e("tangganjing",sholatWajib.getTanggalLengkap().equals(tanggal)+"");
//                                SholatWajib sholatWajib = realm.where(SholatWajib.class).equalTo("tanggal",tanggal).findFirst();

                                switch (namaIbadah){
                                    case "Subuh":
                                        sholatWajib.getSholatsubuh().setImage(R.drawable.ibadahicon);
                                        break;
                                    case "Duhur":
                                        sholatWajib.getSholatDuhur().setImage(R.drawable.ibadahicon);
                                        break;
                                    case "Ashar":
                                        sholatWajib.getSholatAshar().setImage(R.drawable.ibadahicon);
                                        break;
                                    case "Maghrib":
                                        sholatWajib.getSholatMaghrib().setImage(R.drawable.ibadahicon);
                                        break;
                                    case "Isya":
                                        sholatWajib.getSholatIsya().setImage(R.drawable.ibadahicon);
                                        break;
                                }

                                realm.commitTransaction();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.e("LOL","LOL"+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("LOL","LOL"+error.getMessage());
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();

                        Log.d("LOL", "Failed with error msg:\t" + error.getMessage());
                        Log.d("LOL", "Error StackTrace: \t" + error.getStackTrace());
                        // edited here
                        try {
                            byte[] htmlBodyBytes = error.networkResponse.data;
                            Log.e("LOL", new String(htmlBodyBytes), error);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        if (error.getMessage() == null){
                            Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tanggal",tanggal);
                params.put("namaIbadah",namaIbadah.toLowerCase());
                params.put("idUser",idUser+"");
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}

