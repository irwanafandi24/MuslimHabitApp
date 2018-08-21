package com.illiyinmagang.miafandi.muslimhabitapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyIntroConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLoginConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.introslider.IntroActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.model.ServerHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;
import com.illiyinmagang.miafandi.muslimhabitapp.model.User;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_masuk, btn_daftar;
    private LoginButton btn_fb;
    private TwitterLoginButton btn_twitter;
    private TextInputEditText et_email, et_password;
    private MyIntroConfig myIntroConfig;
    private MyLoginConfig myLoginConfig;
    private MyLocatoin myLocatoin;
    private SholatAPI sholatAPI;
    private Realm realm;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configTwitterLogin();
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        myIntroConfig = new MyIntroConfig(LoginActivity.this);
        if(!myIntroConfig.getMyIntro()){
            startActivity(new Intent(LoginActivity.this, IntroActivity.class));
        }

        realm = Realm.getDefaultInstance();
        myLoginConfig = new MyLoginConfig(LoginActivity.this);
        myLocatoin = new MyLocatoin(LoginActivity.this);

        sholatAPI = new SholatAPI(myLocatoin.getMynotedLocation(),"5",LoginActivity.this);

        btn_daftar = (Button) findViewById(R.id.btn_register);
        btn_masuk = (Button) findViewById(R.id.btn_masuk);
        et_email = (TextInputEditText) findViewById(R.id.et_email);
        et_password = (TextInputEditText) findViewById(R.id.et_password);

        btn_fb = (LoginButton) findViewById(R.id.btn_fb);
        btn_twitter = (TwitterLoginButton) findViewById(R.id.btn_twt);

        btn_masuk.setOnClickListener(this);
        btn_daftar.setOnClickListener(this);

        btn_twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.e("twitterlog","berhasil");
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                Log.e("twitterlog",session.getUserName());

                insertDataSosmed(session.getUserName());

                myLoginConfig.noteIntro(session.getUserName());
                Log.e("twitterlog","session");
                RealmResults results = realm.where(SholatWajib.class).findAll();
                if(results.size() == 0) {
                    sholatAPI.setJadwalSholat1Year();
                }
                Log.e("datasholat", sholatAPI.getDataShalat().size()+"");
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Toast.makeText(LoginActivity.this,"Login gagal",Toast.LENGTH_SHORT).show();
            }
        });

        btn_fb.registerCallback(callbackManager, myFacebookLogin());

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        btn_fb.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));

    }

    @Override
    public void onClick(View v) {
        if(v==btn_masuk){
                RealmResults results = realm.where(SholatWajib.class).findAll();
                if(results.size() == 0) {
                    sholatAPI.setJadwalSholat1Year();
                    Log.e("datasholat", sholatAPI.getDataShalat().size() + "");
                }
                ServerHelper serverHelper = new ServerHelper(LoginActivity.this);
                serverHelper.LoginUser(et_email.getText().toString(),et_password.getText().toString());
//            }
        }else if(v==btn_daftar){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        btn_twitter.onActivityResult(requestCode, resultCode, data);
        //fb
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public FacebookCallback myFacebookLogin(){
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String fnm = object.getString("first_name");
                                    String lnm = object.getString("last_name");
                                    String mail = object.getString("email");
                                    myLoginConfig.noteIntro(new User(fnm+""+lnm,mail,"",fnm+" "+lnm));

                                    insertDataSosmed(fnm,mail,fnm+lnm);
                                    Toast.makeText(LoginActivity.this,"Login Berhasil"+myLoginConfig.getDataString(myLoginConfig.KEY_USERNAME),Toast.LENGTH_SHORT).show();
                                    Log.e("dataku",myLoginConfig.getDataString(myLoginConfig.KEY_USERNAME));
                                    RealmResults results = realm.where(SholatWajib.class).findAll();
                                    if(results.size() == 0) {
                                        sholatAPI.setJadwalSholat1Year();
                                    }
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this,"Login gagal,"+exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        };

    }

    public void insertDataSosmed(String username){
        if(!isSavedInDb(username)){
            Log.e("twitterlog","menyimpan data ke lokal");
            realm.beginTransaction();
            User u = new User(username," "," "," ");
            u.setId(generateID());
            Log.e("twitterlog","done setid");
            realm.copyToRealm(u);
            realm.commitTransaction();
            Log.e("twitterlog","done insert");
        }
    }

    public void insertDataSosmed(String username,String email, String nama){
        if(!isSavedInDb(username)){
            realm.beginTransaction();
            User u = new User(username," "," "," ");
            u.setId(generateID());
            realm.copyToRealm(u);
            realm.commitTransaction();
        }
    }

    public int generateID() {
        Number currentIdNum = realm.where(User.class).max("id");
        Log.v("currentID",currentIdNum+"");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
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

    private void configTwitterLogin() {
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.CONSUMER_KEY), getResources().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();

        Twitter.initialize(config);
    }

    public boolean isSavedInDb(String username){
        User u = realm.where(User.class).equalTo("username",username).findFirst();
        if(u == null){
            return false;
        }
        return true;
    }
}
