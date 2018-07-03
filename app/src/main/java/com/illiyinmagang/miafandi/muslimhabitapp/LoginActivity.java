package com.illiyinmagang.miafandi.muslimhabitapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_masuk, btn_daftar;
    private LinearLayout btn_fb, btn_twitter;
    private TextInputEditText et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_daftar = (Button) findViewById(R.id.btn_register);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_password = (TextInputEditText) findViewById(R.id.et_password);

        btn_fb = (LinearLayout) findViewById(R.id.btn_fb);
        btn_twitter = (LinearLayout) findViewById(R.id.btn_tw);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_masuk){
            if(et_email.getText().toString().equals("admin") && et_password.getText().toString().equals("")){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        }else if(v==btn_daftar){

        }
    }
}
