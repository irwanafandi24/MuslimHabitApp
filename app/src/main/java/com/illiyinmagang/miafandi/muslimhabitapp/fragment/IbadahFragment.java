package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyLocatoin;
import com.illiyinmagang.miafandi.muslimhabitapp.MainActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.Notif.NotificationDisplayService;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Alarm;
import com.illiyinmagang.miafandi.muslimhabitapp.model.RealmHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajibNotif;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class IbadahFragment extends MyFragment implements View.OnClickListener{
    private ImageButton btnRight,btnLeft;
    private TextView txtTanggal, txtBulan, txtTime;
    private ArrayList<Date> dates;
    public static int position = 1;
    ViewGroup viewGroup1;
    private MyLocatoin myLocatoin;
    private SholatAPI sholatAPI;
    private Realm realm;
    public ArrayList<SholatWajib> sholatWajibs = new ArrayList();
    private MyDateSelected myDateSelected;
    ViewPager mViewPager;
    ViewPagerAdapter mViewPagerAdapter;
    TabLayout tabLayout;

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

        myDateSelected = new MyDateSelected(getContext());
        myLocatoin = new MyLocatoin(this.getContext());

        if(!myLocatoin.getMynotedLocation().equals("malang")){
            Toast.makeText(this.getContext(),"Jadwal Lokasi Anda Sekarang berada di Kota Malang, Atur Lokasi anda di menu setting",Toast.LENGTH_SHORT).show();
        }

        realm = Realm.getDefaultInstance();

        mViewPager = (ViewPager) rootView.findViewById(R.id.viewPagerHome);
        mViewPagerAdapter = new ViewPagerAdapter(this.getActivity().getSupportFragmentManager());
        SholatFragment sholatFragment = new SholatFragment();
        PuasaFragment  puasaFragment = new PuasaFragment();
        SedekahFragment sedekahFragment = new SedekahFragment();


        mViewPagerAdapter.addFragment(sholatFragment.newInstance(),"SHALAT");
        mViewPagerAdapter.addFragment(PuasaFragment.newInstance(),"PUASA");
        mViewPagerAdapter.addFragment(SedekahFragment.newInstance(),"SEDEKAH");
        mViewPager.setAdapter(mViewPagerAdapter);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#23C27E"));
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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        Log.v("Tanggal", strDate);

        char a[] = strDate.toCharArray();
        char separator = '-';
        String tgl = "" ,bulan = "",tahun = "";
        int count = 0,i = 0;
        while (i < a.length) {
            if (a[i] != separator) {
                switch (count) {
                    case 0:
                        tahun = tahun + a[i];
                        break;
                    case 1:
                        bulan = bulan + a[i];
                        break;
                    case 2:
                        tgl = tgl + a[i];
                        break;
                }

            } else {
                count++;
            }
            i++;
        }

        Log.d("tanggal1", getDateNow());

        //getData jadwal dari API + masuk ke local db
        sholatAPI = new SholatAPI(myLocatoin.getMynotedLocation(),getContext());

        RealmResults results = realm.where(SholatWajib.class).findAll();
        if(results.size() == 0){
            getActivity().finish();
            startActivity(new Intent(this.getContext(), MainActivity.class));
        }
        else if(results.size() > 0){
            position = sholatAPI.getDataPosistionByDate(getDateNow());
//            Log.e("datjumlah",sholatAPI.getDataShalat().size()+""+sholatAPI.getDataShalat().get(0));
            myDateSelected.notePosition(position);
            setUpTanggal();
            Log.e("posisikuisi",sholatAPI.getDataShalat().size()+"");

        }
        Log.e("nop",sholatAPI.getDataShalat().size()+",");

        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v==btnLeft){
            if(position > 0) {
                position = position - 1;
//                SholatFragment.position = position;
                myDateSelected.notePosition(position);
                setUpTanggal();
                resetUpSchedule();
            }
        }else if(v==btnRight){
            //32 data yang kelebihan bulan desember
            if(position < sholatAPI.getDataShalat().size()-32){
                btnRight.setVisibility(View.VISIBLE);
                position = position +1;
                myDateSelected.notePosition(position);
                setUpTanggal();
                resetUpSchedule();
            }
        }
    }

    private void resetUpSchedule() {
        mViewPagerAdapter.clearContentFragment();
        mViewPagerAdapter.addOnlyFragment(new SholatFragment().newInstance());
        mViewPagerAdapter.addOnlyFragment(new PuasaFragment().newInstance());
        mViewPagerAdapter.addOnlyFragment(new SedekahFragment().newInstance());
        mViewPager.setAdapter(mViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setUpTanggal(){
        try {
            String tanggal = sholatAPI.getDataShalat().get(position).getTanggal();
            String thn = sholatAPI.getDataShalat().get(position).getTahun();
            int bulan = sholatAPI.getDataShalat().get(position).getBulan();

            Log.e("posisiku1",tanggal+"."+bulan+"."+position);

            txtTanggal.setText(tanggal);
            txtBulan.setText(getNameOfMonth(bulan-1) +", "+thn);
        }catch (ArrayIndexOutOfBoundsException e){

        }
    }

    public String getNameOfMonth(int i){
        return this.getContext().getResources().getStringArray(R.array.months)[i];
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getDateNow(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();

        position = sholatAPI.getDataPosistionByDate(getDateNow());
        myDateSelected.notePosition(position);
        RealmResults r1 = realm.where(SholatWajib.class).findAll();
        if(r1.size() > 0){
            setUpTanggal();
        }

    }



//    class SholatFragment
//    public static class SholatFragment extends MyFragment {
//        private RecyclerView recyclerView;
//        private ArrayList<Sholat> sholatArrayList;
//        private ArrayList<Integer> gambar;
//        private SholatAPI sholatAPI;
//        private MyDateSelected myDateSelected;
//        private Sholat sholatSubuh, sholatDuhur, sholatAshar, sholatMaghrib, sholatIsya;
//
//        //for alarm and get Sholat time
//        private Realm realm;
//        private RealmHelper realmHelper;
//        private List<SholatWajibNotif> sholatList;
//        private List<Alarm> alarmSholat;
//        private String showTime, beforeTime;
//        private Long waktuTunggu;
//
//        public SholatFragment() {
//            // Required empty public constructor
//        }
//
//        public static SholatFragment newInstance() {
//            SholatFragment fragment = new SholatFragment();
//            Bundle args = new Bundle();
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_sholat, container, false);
//
//            myDateSelected = new MyDateSelected(getContext());
//            myDateSelected.getMyPosisition();
//            Toast.makeText(getContext(),myDateSelected.getMyPosisition()+"",Toast.LENGTH_LONG).show();
//
//            sholatAPI = new SholatAPI();
////            sholatSubuh = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatsubuh();
////            sholatDuhur = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatDuhur();
////            sholatAshar = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatAshar();
////            sholatMaghrib = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatMaghrib();
////            sholatIsya = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatIsya();
//
//            sholatSubuh = sholatAPI.getDataShalat().get(position).getSholatsubuh();
//            sholatDuhur = sholatAPI.getDataShalat().get(position).getSholatDuhur();
//            sholatAshar = sholatAPI.getDataShalat().get(position).getSholatAshar();
//            sholatMaghrib = sholatAPI.getDataShalat().get(position).getSholatMaghrib();
//            sholatIsya = sholatAPI.getDataShalat().get(position).getSholatIsya();
//
//    //<<<<<<< HEAD
//
//    //=======
//            //=============Get it
//            RealmConfiguration configuration = new RealmConfiguration.Builder().build();
//            realm = Realm.getInstance(configuration);
//
//            realmHelper = new RealmHelper(realm);
//            sholatList = new ArrayList<>();
//            alarmSholat = new ArrayList<>();
//
//            sholatList = realmHelper.getAllSholat();
//            alarmSholat = realmHelper.getAllAlarm();
//
//            for(Alarm alarmX:alarmSholat){
//                showTime = alarmX.getWaktuAlaram();
//            }
//
//    //        Log.v("SHOW TIME",showTime+"");
//    //        beforeTime = getTimeBeforeAlarm(showTime);
//    //        waktuTunggu = Long.parseLong(beforeTime);
//            Log.v("UBAH KE INTEGER",waktuTunggu+"");
//    //>>>>>>> 7a685e399a190e325e181e8eb198986967978dc0
//
//            sholatArrayList = new ArrayList();
//            gambar = new ArrayList();
//
//            gambar.add(R.drawable.ibadahicon);
//            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleIbadah);
//
//            sholatArrayList.clear();
//            sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatSubuh.getJamSholat())),convertDateFormat(sholatSubuh.getJamSholat()),gambar.get(0)));
//            sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatDuhur.getJamSholat())),convertDateFormat(sholatDuhur.getJamSholat()),gambar.get(0)));
//            sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatAshar.getJamSholat())),convertDateFormat(sholatAshar.getJamSholat()),gambar.get(0)));
//            sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatMaghrib.getJamSholat())),convertDateFormat(sholatMaghrib.getJamSholat()),gambar.get(0)));
//            sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatIsya.getJamSholat())),convertDateFormat(sholatIsya.getJamSholat()),gambar.get(0)));
//
//            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//            recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, this.getContext()));
//            soundNotif();
//
//    //        Log.e("konvert",convertDateFormat(sholatAshar.getJamSholat()));
//            return rootView;
//        }
//
//        public String convertDateFormat(String time){
//            String out;
//            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm aa");
//            SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
//            try {
//                Date date = dateFormat.parse(time);
//                out = dateFormat2.format(date);
//                return out;
//            } catch (ParseException e) {
//            }
//            return "";
//        }
//
//        public String getTimeBeforeAlarm(String y){
//            String x = String.valueOf(y.charAt(0));
//            if (!String.valueOf(y.charAt(1)).equals(" ")){
//                x = x + String.valueOf(y.charAt(1));
//            }
//            return x;
//        }
//
//        public void soundNotif(){
//            if(String.valueOf(sholatArrayList.get(0).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(0).isSwitchSholat()== true){
//                getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
//            }else if(String.valueOf(sholatArrayList.get(1).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(1).isSwitchSholat()== true){
//                getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
//            }else if(String.valueOf(sholatArrayList.get(2).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(2).isSwitchSholat()== true){
//                getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
//                Log.v("INI DIA","MASUK BOS");
//            }else if(String.valueOf(sholatArrayList.get(3).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(3).isSwitchSholat()== true){
//                getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
//            }else if(String.valueOf(sholatArrayList.get(4).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(4).isSwitchSholat()== true){
//                getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
//            }
//
//    //        Log.v("CEK NOOOOOOOTIIIIIF ",sholatArrayList.get(2).getWaktuTunggu().equals("0")+" BOOLEAN "+sholatList.get(2).isSwitchSholat());
//        }
//
//        public String getDifferenceTime(String time){
//    //        Log.e("selisih",time) ;
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            long jam, menit, selisih;
//            String imbuhan;
//            Date date,date1 = null;
//            try {
//                date = sdf.parse(sdf1.format(cal.getTime()));
//                date1 = sdf.parse(time);
//
//                selisih = convertMinutes(date,date1);
//                if( selisih > 0){
//                    imbuhan = "Menuju Adzan";
//                }else if(selisih < 0){
//                    imbuhan = "Setelah Adzan";
//                    selisih = selisih*-1;
//                }else{
//                    imbuhan = "Adzan";
//                }
//
//                jam = selisih / 60; //menit
//                menit = selisih % 60;
//
//                Log.e("selisih",imbuhan+""+jam+" jam,"+menit+" menit,") ;
//                if(jam == 0 ){
//                    return imbuhan+" "+menit+" menit";
//                }else if(menit == 0){
//                    return imbuhan+" "+jam+" jam";
//                }else{
//                    return imbuhan+" "+jam+" jam, "+menit+"menit";
//                }
//
//
//            } catch (ParseException e) {
//            }
//    //
//            return "";
//        }
//
//        public long convertMinutes(Date now, Date sholat){
//            long jam_now, jm_sholat, menit_now, menit_sholat, totalMenit, alarmBerbunyi;
//            jam_now = now.getHours();
//            jm_sholat = sholat.getHours();
//            menit_now = now.getMinutes();
//            menit_sholat = sholat.getMinutes();
//
//            totalMenit = (jm_sholat*60+menit_sholat)-(jam_now*60+menit_now);
//    //        alarmBerbunyi = totalMenit-waktuTunggu;
//
//    //        if(alarmBerbunyi == 0){
//    //            getActivity().startService(new Intent(getActivity(),AlarmService.class));
//    //        }
//
//            Log.v("Tanda Menit adalah",totalMenit+"");
//            return  totalMenit;
//        };
//
//        @Override
//        public void onResume() {
//            super.onResume();
//        }
//    }

}
