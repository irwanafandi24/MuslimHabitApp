package com.illiyinmagang.miafandi.muslimhabitapp;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
            //navigationView.setCheckedItem(R.id.ibadah);
            getSupportActionBar().setTitle("DiaryIbadah");
        }else if(id == R.id.grafik){
            ft.replace(R.id.fragment, new GrafikFragment());
            //navigationView.setCheckedItem(R.id.grafik);
            getSupportActionBar().setTitle("Grafik Istiqomah");
        }else if(id == R.id.info){
            ft.replace(R.id.fragment, new InfoFragment());
            //navigationView.setCheckedItem(R.id.info);
            getSupportActionBar().setTitle("Info Ibadah");
        }else if(id == R.id.notifikasi){
            ft.replace(R.id.fragment, new NotifikasiFragment());
            //navigationView.setCheckedItem(R.id.notifikasi);
            getSupportActionBar().setTitle("Notifikasi");
        }else if(id == R.id.pengaturan){
            ft.replace(R.id.fragment, new SettingFragment());
            //navigationView.setCheckedItem(R.id.pengaturan);
            getSupportActionBar().setTitle("Pengaturan");
        }

        ft.commit();
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
