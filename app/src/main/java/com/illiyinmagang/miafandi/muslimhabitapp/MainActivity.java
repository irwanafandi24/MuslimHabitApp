package com.illiyinmagang.miafandi.muslimhabitapp;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLoginConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.GrafikFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.IbadahFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.InfoFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.NotifikasiFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.SettingFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Application.ActivityLifecycleCallbacks {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private NavigationView navigationView;
    private Realm realm;
    private MyLocatoin myLocatoin;
    private MyLoginConfig myLoginConfig;
    //run program in background

    private TextView txtNama,txtAsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLoginConfig = new MyLoginConfig(MainActivity.this);
        myLocatoin = new MyLocatoin(MainActivity.this);

        Log.e("ceksession",myLoginConfig.getDataString(myLoginConfig.KEY_USERNAME)+""+myLoginConfig.isLogedIn());
        if(!myLoginConfig.isLogedIn()){
            Log.e("masuksession","berhasil masuk"+!myLoginConfig.isLogedIn());
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }else{
            Log.e("keluarifsession","ga boleh kseini"+!myLoginConfig.isLogedIn());

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("Muslim Habit");

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerMain);

            mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToogle);
            mToogle.syncState();

            navigationView = (NavigationView) findViewById(R.id.navigationView);
            navigationView.setNavigationItemSelectedListener(this);

            View root = navigationView.getHeaderView(0);
            txtNama = (TextView) root.findViewById(R.id.txtNama);
            txtAsal = (TextView) root.findViewById(R.id.txtAsal);
            txtNama.setText(myLoginConfig.getDataString(myLoginConfig.KEY_USERNAME));
            txtAsal.setText(myLocatoin.getMynotedLocation());

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new IbadahFragment()).commit();
                navigationView.setCheckedItem(R.id.ibadah);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (id == R.id.ibadah) {
            ft.replace(R.id.fragment, new IbadahFragment());
            getSupportActionBar().setTitle("DiaryIbadah");
        } else if (id == R.id.grafik) {
            ft.replace(R.id.fragment, new GrafikFragment());
            getSupportActionBar().setTitle("Grafik Istiqomah");
        } else if (id == R.id.info) {
            ft.replace(R.id.fragment, new InfoFragment());
            getSupportActionBar().setTitle("Info Ibadah");
        } else if (id == R.id.notifikasi) {
            ft.replace(R.id.fragment, new NotifikasiFragment());
            getSupportActionBar().setTitle("Notifikasi");
        } else if (id == R.id.pengaturan) {
            ft.replace(R.id.fragment, new SettingFragment());
            getSupportActionBar().setTitle("Pengaturan");
        }else if(id==R.id.logout){
            myLoginConfig.deleteSession();
            if(LoginManager.getInstance() != null){
                LoginManager.getInstance().logOut();
            }
            Log.e("Logoutfb","FB");
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
            return true;
        }

        ft.commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

//<<<<<<< HEAD
//=======

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new IbadahFragment()).commit();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new IbadahFragment()).commit();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new IbadahFragment()).commit();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
