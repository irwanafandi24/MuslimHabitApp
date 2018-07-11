package com.illiyinmagang.miafandi.muslimhabitapp.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

    public ServerHelper(Context context) {
        this.context = context;
    }

    public void InserServer(final String username, final String email, final String pass, final String nama){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                ConfigDB.INSERTUSER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(context,"Register Berhasil Server",Toast.LENGTH_LONG).show();
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ConfigDB.LOGINTUSER,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getBoolean("error")){
                                myLoginConfig.noteIntro(new User(
                                        response.getJSONObject("msg").getString("username"),
                                        response.getJSONObject("msg").getString("email"),
                                        response.getJSONObject("msg").getString("password"),
                                        response.getJSONObject("msg").getString("nama")
                                ));

                                progressDialog.dismiss();
                                context.startActivity(new Intent(context,MainActivity.class));
                                Toast.makeText(context,"Login Berhasil Server"+response.getJSONObject("msg").getString("username"),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context,"Login Gagal Server",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

//    public void InsertRekap(){
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.GET,
//                ConfigDB.INSERTUSER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Toast.makeText(context,"Register Berhasil Server",Toast.LENGTH_LONG).show();
//                        }catch (JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener(){
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("username",username);
//                params.put("password",pass);
//                params.put("email",email);
//                params.put("nama",nama);
//                return params;
//            }
//        };
//
//        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
//    }

//    public HashMap<String,String> getNilaiRekap(){


}

