package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//<<<<<<< HEAD
import android.support.annotation.RequiresApi;
//=======
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//>>>>>>> a3665afc1a6936768ebd25bd612a3bdd30d1ac4e
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//<<<<<<< HEAD
import android.widget.Button;
import android.widget.ImageButton;
//=======
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
//>>>>>>> a3665afc1a6936768ebd25bd612a3bdd30d1ac4e
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IbadahFragment extends Fragment implements View.OnClickListener{
    private ImageButton btnRight,btnLeft;
    private TextView txtTanggal, txtBulan;
    private ArrayList<Date> dates;
    private static int position = 1;
    private SimpleDateFormat month_date;
    private String month_name;

    public IbadahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ibadah, container, false);
        // Inflate the layout for this fragment

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.viewPagerHome);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(this.getActivity().getSupportFragmentManager());
        mViewPagerAdapter.addFragment(SholatFragment.newInstance(),"SHALAT");
        mViewPagerAdapter.addFragment(PuasaFragment.newInstance(),"PUASA");
        mViewPagerAdapter.addFragment(SedekahFragment.newInstance(),"SEDEKAH");
        mViewPager.setAdapter(mViewPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#23C27E"));
//        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#23C27E"));

//<<<<<<< HEAD
        btnLeft = (ImageButton) rootView.findViewById(R.id.btn_lef_slider);
        btnRight = (ImageButton) rootView.findViewById(R.id.btn_right_slider);

        btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);

        txtBulan = (TextView) rootView.findViewById(R.id.txt_bulan_slider);
        txtTanggal = (TextView) rootView.findViewById(R.id.txt_tanggal_slider);

        dates = new ArrayList();

        dates.add(new Date(2018,10,7));
        dates.add(new Date(2018,10,8));
        dates.add(new Date(2018,10,9));
        dates.add(new Date(2018,10,10));
        dates.add(new Date(2018,10,11));

        month_date = new SimpleDateFormat("MMMM");

        month_name = month_date.format(dates.get(position));
        txtTanggal.setText(String.valueOf(dates.get(position).getDate()));
        txtBulan.setText(month_name+" November, 2018");
//=======
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialogtambahsholat, null);

                Spinner spinnerPilihSholat = (Spinner) mView.findViewById(R.id.spinnerPilihShalat);
                ArrayAdapter<CharSequence> myAdapter1 = ArrayAdapter.createFromResource(getContext(),R.array.spinPilihSholat,android.R.layout.simple_spinner_item);
                myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPilihSholat.setAdapter(myAdapter1);
                spinnerPilihSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Spinner spinnerRokaatSholat = (Spinner) mView.findViewById(R.id.spinnerJumlahRakaat);
                ArrayAdapter<CharSequence> myAdapter2 = ArrayAdapter.createFromResource(getContext(),R.array.spinRakaat,android.R.layout.simple_spinner_item);
                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRokaatSholat.setAdapter(myAdapter2);
                spinnerRokaatSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Button btnKonfirmasiTambah =  (Button) mView.findViewById(R.id.btnKonfirmTambah);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                btnKonfirmasiTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
//>>>>>>> a3665afc1a6936768ebd25bd612a3bdd30d1ac4e

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v==btnLeft){
            if(position > 0){
                position = position -1;
                month_name = month_date.format(dates.get(position));
                txtTanggal.setText(String.valueOf(dates.get(position).getDate()));
                txtBulan.setText(month_name+" November, 2018");
            }
        }else if(v==btnRight){
            if(position < dates.size()-1){
                position = position +1;
                month_name = month_date.format(dates.get(position));
                txtTanggal.setText(String.valueOf(dates.get(position).getDate()));
                txtBulan.setText(month_name+" November, 2018");
            }

        }
    }

}
