package com.illiyinmagang.miafandi.muslimhabitapp;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.GrafikFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.IbadahFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.InfoFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.NotifikasiFragment;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Muslim Habit");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerMain);

        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new IbadahFragment()).commit();
            navigationView.setCheckedItem(R.id.ibadah);
        }

    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(id == R.id.ibadah){
            ft.replace(R.id.fragment, new IbadahFragment());
            getSupportActionBar().setTitle("DiaryIbadah");
        }else if(id == R.id.grafik){
            ft.replace(R.id.fragment, new GrafikFragment());
            getSupportActionBar().setTitle("Grafik Istiqomah");
        }else if(id == R.id.info){
            ft.replace(R.id.fragment, new InfoFragment());
            getSupportActionBar().setTitle("Info Ibadah");
        }else if(id == R.id.notifikasi){
            ft.replace(R.id.fragment, new NotifikasiFragment());
            getSupportActionBar().setTitle("Notifikasi");
        }else if(id == R.id.pengaturan){
            ft.replace(R.id.fragment, new SettingFragment());
            getSupportActionBar().setTitle("Pengaturan");
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

    public void setUpLokasi(){
        final LocationConfig[] locationConfig = new LocationConfig[1];
        final double[] longitude = new double[1];
        final double[] latitude = new double[1];
        FusedLocationProviderClient mFusedLocationClient;

        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
        }else{
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }else {
              mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    longitude[0] = location.getLongitude();
                                    latitude[0] = location.getLatitude();

                                    locationConfig[0] = new LocationConfig(MainActivity.this);
                                    //                            locationConfig.getAddress(latitude,longitude);
                                }else{
                                    Toast.makeText(MainActivity.this,"GPS Sedang Diaktifkan, Harap Tunggu sebentar dan Coba lagi",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

    }

}
