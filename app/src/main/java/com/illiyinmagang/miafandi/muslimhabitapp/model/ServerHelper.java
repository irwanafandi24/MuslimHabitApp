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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by user on 09/07/2018.
 */

public class ServerHelper {
    private Context context;
    private Realm realm = Realm.getDefaultInstance();
    private ProgressDialog progressDialog;

    public ServerHelper(Context context) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
    }

    public void InserServer(final String username, final String email, final String pass, final String nama){
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
                                Toast.makeText(context,"Login Berhasil",Toast.LENGTH_SHORT).show();

                                myLoginConfig.noteIntro(new User(
                                        jsonObject.getJSONObject("msg").getString("username"),
                                        jsonObject.getJSONObject("msg").getString("email"),
                                        jsonObject.getJSONObject("msg").getString("password"),
                                        jsonObject.getJSONObject("msg").getString("nama"),
                                        jsonObject.getJSONObject("msg").getInt("id")
                                ));
                                Log.e("jsonku",jsonObject.getJSONObject("msg").getString("username")+"");
                                context.startActivity(new Intent(context, MainActivity.class));
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
        progressDialog.setMessage("Sedang merekap");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                ConfigDB.INSERTREKAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            progressDialog.dismiss();
                            Toast.makeText(context,"Rekap Berhasil ke Server",Toast.LENGTH_LONG).show();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
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
                params.put("namaIbadah",namaIbadah);
                params.put("idUser",idUser);
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

}

