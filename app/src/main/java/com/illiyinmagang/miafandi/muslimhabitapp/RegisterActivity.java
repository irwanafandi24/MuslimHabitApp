package com.illiyinmagang.miafandi.muslimhabitapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.illiyinmagang.miafandi.muslimhabitapp.model.User;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class RegisterActivity extends AppCompatActivity {
    private Button btn_registrasi;
    private TextInputEditText etEmail, etUsername, etPassword, etNama, etRepasword;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        realm = Realm.getDefaultInstance();

        etEmail = (TextInputEditText) findViewById(R.id.et_email_reg);
        etPassword = (TextInputEditText) findViewById(R.id.et_password);
        etRepasword = (TextInputEditText) findViewById(R.id.et_retype_password);
        etUsername = (TextInputEditText) findViewById(R.id.et_username);
        etNama = (TextInputEditText) findViewById(R.id.et_nama);

        btn_registrasi = (Button) findViewById(R.id.btn_register);
        btn_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationEntry();
                if(isCorrest()){
                    addUser(etNama.getText().toString(),
                    etUsername.getText().toString(),
                            etPassword.getText().toString(),
                    etEmail.getText().toString()
                            );
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    Toast.makeText(RegisterActivity.this,"Registrasi Berhasil",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this,"Registrasi Gagal",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validationEntry() {
        String repass = etRepasword.getText().toString();
        String pass = etPassword.getText().toString();
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();

        if( nama.equals("")){
            etNama.setError("Nama Harus Diisi");
        }if(email.equals("")){
            etEmail.setError("E-mail Harus Diisi");
        }if(username.equals("")){
            etUsername.setError("Username Harus Diisi");
        }if(!pass.equals(repass)){
            etPassword.setError("Password Harus Sama");
        }
    }

    private boolean isCorrest(){
        if(!etNama.getText().toString().equals("") && !etUsername.getText().toString().equals("") && !etEmail.getText().toString().equals("")){
            return true;
        }else{
            return false;
        }
    }

    public boolean addUser(String nama, String username, String pass, String email){
        realm.beginTransaction();
        try {
            User u = new User(username,email,pass,nama);
            u.setId(generateId());

            realm.copyToRealm(u);
            realm.commitTransaction();
        }catch (Exception exception){
            return false;
        }
        return true;
    }

    public int generateId(){
        Number currentIdNum = realm.where(User.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
