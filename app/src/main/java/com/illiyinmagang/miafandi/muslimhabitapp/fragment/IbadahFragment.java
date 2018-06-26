package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class IbadahFragment extends MyFragment implements View.OnClickListener{
    private ImageButton btnRight,btnLeft;
    private TextView txtTanggal, txtBulan, txtTime;
    private ArrayList<Date> dates;
    private static int position = 1;
    ViewGroup viewGroup1;
    private MyLocatoin myLocatoin;
    private SholatAPI sholatAPI;
    public ArrayList<SholatWajib> sholatWajibs = new ArrayList();
    private Realm realm;

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

        myLocatoin = new MyLocatoin(this.getContext());
//
        if(!myLocatoin.getMynotedLocation().equals("malang")){
            Toast.makeText(this.getContext(),"Jadwal Lokasi Anda Sekarang berada di Kota Malang, Atur Lokasi anda di menu setting",Toast.LENGTH_SHORT).show();
        }

        realm = Realm.getDefaultInstance();

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

        btnLeft = (ImageButton) rootView.findViewById(R.id.btn_lef_slider);
        btnRight = (ImageButton) rootView.findViewById(R.id.btn_right_slider);

        btnRight.setOnClickListener(this);
        btnLeft.setOnClickListener(this);

        txtBulan = (TextView) rootView.findViewById(R.id.txt_bulan_slider);
        txtTanggal = (TextView) rootView.findViewById(R.id.txt_tanggal_slider);

        viewGroup1 = (ViewGroup) rootView.findViewById(R.id.layout_top);

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

                txtTime = (TextView) mView.findViewById(R.id.txtPickTime);
                Calendar calendar = Calendar.getInstance();
                final int jam  = calendar.get(Calendar.HOUR_OF_DAY);
                final int menit = calendar.get(Calendar.MINUTE);

                txtTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour, minutes;
                            if(hourOfDay < 10){
                                 hour = "0"+String.valueOf(hourOfDay);
                            }else{
                                hour = hourOfDay+"";
                            }

                            if(minute < 10){
                                minutes = "0"+String.valueOf(minute);
                            }else{
                                minutes = minute+"";
                            }
                            txtTime.setText(hour+":"+minutes+"PM");
                        }
                    },jam,menit,true);
                    timePickerDialog.show();
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

        //getData jadwal dari API + masuk ke local db
        sholatAPI = new SholatAPI(myLocatoin.getMynotedLocation(),getContext());

        RealmResults results = realm.where(SholatWajib.class).findAll();
        if(results.size() == 0){
            sholatAPI.setJadwalSholat1Year();
//            sholatWajibs = sholatAPI.getDataShalat();
        }
        if(results.size() > 0){
            setUpTanggal();
        }
        Log.e("nop",sholatAPI.getDataShalat().size()+","+sholatWajibs.size());
//        if(sholatWajibs.size() > 0){
//            Log.e("noop",sholatAPI.getDataShalat().size()+".");
//
//        }

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v==btnLeft){
            if(position > 0){
                position = position -1;
                setUpTanggal();
            }
        }else if(v==btnRight){
            if(position < sholatAPI.getDataShalat().size()-1){
                position = position +1;
                setUpTanggal();
            }

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setUpTanggal(){
        String tanggal = sholatAPI.getDataShalat().get(position).getTanggal();
        String thn = sholatAPI.getDataShalat().get(position).getTahun();
        int bulan = sholatAPI.getDataShalat().get(position).getBulan();
        if (tanggal.length() < 2){
            tanggal = "0"+tanggal;
        }
        txtTanggal.setText(tanggal);
        txtBulan.setText(getNameOfMonth(bulan) +", "+thn);
    }

    public String getNameOfMonth(int i){
        return this.getContext().getResources().getStringArray(R.array.months)[i];
    }
}
