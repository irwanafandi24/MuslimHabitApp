package com.illiyinmagang.miafandi.muslimhabitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyIntroConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyLoginConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.introslider.IntroActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;
import com.illiyinmagang.miafandi.muslimhabitapp.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_masuk, btn_daftar;
    private LinearLayout btn_fb, btn_twitter;
    private TextInputEditText et_email, et_password;
    private MyIntroConfig myIntroConfig;
    private MyLoginConfig myLoginConfig;
    private MyLocatoin myLocatoin;
    private SholatAPI sholatAPI;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myIntroConfig = new MyIntroConfig(LoginActivity.this);

        if(!myIntroConfig.getMyIntro()){
            startActivity(new Intent(LoginActivity.this, IntroActivity.class));
        }

        realm = Realm.getDefaultInstance();
        myLoginConfig = new MyLoginConfig(LoginActivity.this);

        myLocatoin = new MyLocatoin(LoginActivity.this);
        sholatAPI = new SholatAPI(myLocatoin.getMynotedLocation(),LoginActivity.this);

        btn_daftar = (Button) findViewById(R.id.btn_register);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_password = (TextInputEditText) findViewById(R.id.et_password);

        btn_fb = (LinearLayout) findViewById(R.id.btn_fb);
        btn_twitter = (LinearLayout) findViewById(R.id.btn_tw);

        btn_masuk.setOnClickListener(this);
        btn_daftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_masuk){
            if(isLoginCorrect(et_email.getText().toString(),et_password.getText().toString())){
//            if(true){
                Log.e("login", "berhasil login");
                RealmResults results = realm.where(SholatWajib.class).findAll();
                if(results.size() == 0){
                    sholatAPI.setJadwalSholat1Year();
                    Log.e("datasholat", sholatAPI.getDataShalat().size()+"");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        }else if(v==btn_daftar){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }
    }

    public boolean isLoginCorrect(String username, String password){
        User results = new User();
        results = realm.where(User.class).equalTo("username",username)
                .equalTo("password",password).findFirst();

        Log.e("akun",results+"");
        if(results == null){
            return false;
        }else{
            myLoginConfig.noteIntro(results);
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
