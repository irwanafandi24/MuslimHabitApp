package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.Notif.AlarmService;
import com.illiyinmagang.miafandi.muslimhabitapp.Notif.NotificationDisplayService;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Alarm;
import com.illiyinmagang.miafandi.muslimhabitapp.model.RealmHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajibNotif;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SholatFragment extends MyFragment {
    private RecyclerView recyclerView;
    private ArrayList<Sholat> sholatArrayList;
    private ArrayList<Integer> gambar;
    private SholatAPI sholatAPI;
    private MyDateSelected myDateSelected;
    private Sholat sholatSubuh, sholatDuhur, sholatAshar, sholatMaghrib, sholatIsya;
    Handler mHandler;
    boolean y = false;
    String pin0, pin1, pin2, pin3, pin4;

    //for alarm and get Sholat time
    private Realm realm;
    private RealmHelper realmHelper;
    private List<SholatWajibNotif> sholatList;
    private List<Alarm> alarmSholat;
    private String showTime, beforeTime;
    private Long waktuTunggu;
    View rootView;

    public SholatFragment() {
        // Required empty public constructor
    }

    public static SholatFragment newInstance() {
        SholatFragment fragment = new SholatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sholat, container, false);

        myDateSelected = new MyDateSelected(getContext());
        myDateSelected.getMyPosisition();
        Toast.makeText(getContext(),myDateSelected.getMyPosisition()+"",Toast.LENGTH_LONG).show();

        sholatAPI = new SholatAPI(getContext());
        sholatSubuh = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatsubuh();
        sholatDuhur = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatDuhur();
        sholatAshar = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatAshar();
        sholatMaghrib = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatMaghrib();
        sholatIsya = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatIsya();

//<<<<<<< HEAD
        //=============Get it
//=======
///>>>>>>> 6c943c7f481c9b3b2c234289ea0d0d4dbbdfc051
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        sholatList = new ArrayList<>();
        alarmSholat = new ArrayList<>();


        //startService(new Intent(this,MyService.class));
        Log.v("TASK 1 ==========="," OK ");
        this.mHandler = new Handler();
        m_Runnable.run();
//        sholatArrayList.clear();
//        sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatSubuh.getJamSholat())),convertDateFormat(sholatSubuh.getJamSholat()),gambar.get(0)));
//        sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatDuhur.getJamSholat())),convertDateFormat(sholatDuhur.getJamSholat()),gambar.get(0)));
//        sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatAshar.getJamSholat())),convertDateFormat(sholatAshar.getJamSholat()),gambar.get(0)));
//        sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatMaghrib.getJamSholat())),convertDateFormat(sholatMaghrib.getJamSholat()),gambar.get(0)));
//        sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatIsya.getJamSholat())),convertDateFormat(sholatIsya.getJamSholat()),gambar.get(0)));

//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, this.getContext()));

//        Log.e("konvert",convertDateFormat(sholatAshar.getJamSholat()));
        //getActivity().startService(new Intent(getActivity(),MyService.class));
        return rootView;
    }

//<<<<<<< HEAD
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            //Toast.makeText(getContext(),"in runnable",Toast.LENGTH_SHORT).show();
            sholatList = realmHelper.getAllSholat();
            alarmSholat = realmHelper.getAllAlarm();
            if(sholatList.size() == 0){
                SholatWajibNotif s1 = new SholatWajibNotif("Subuh",true);
                realmHelper.saveData(s1);
                SholatWajibNotif s2 = new SholatWajibNotif("Duhur",true);
                realmHelper.saveData(s2);
                SholatWajibNotif s3 = new SholatWajibNotif("Ashar",false);
                realmHelper.saveData(s3);
                SholatWajibNotif s4 = new SholatWajibNotif("Magrib",true);
                realmHelper.saveData(s4);
                SholatWajibNotif s5 = new SholatWajibNotif("Isya",false);
                realmHelper.saveData(s5);
                Log.v("InsertData","DOne inserted");
            }
            sholatList = realmHelper.getAllSholat();
            Log.v("AA========",alarmSholat.size()+"");

            if(alarmSholat.size() == 0){
                Alarm n = new Alarm("10 Menit Sebelumnya");
                Log.v("AA","ENTER KOK");
                realmHelper.saveAlarem(n);
            }
            alarmSholat = realmHelper.getAllAlarm();
//=======
//        Log.v("SHOW TIME",showTime+"");
//        beforeTime = getTimeBeforeAlarm(showTime);
//        waktuTunggu = Long.parseLong(beforeTime);
//        Log.v("UBAH KE INTEGER",waktuTunggu+"");
//>>>>>>> 6c943c7f481c9b3b2c234289ea0d0d4dbbdfc051


            for(Alarm alarmX:alarmSholat){
                showTime = alarmX.getWaktuAlaram();
            }

            Log.v("SHOW TIME",showTime+"");
            beforeTime = getTimeBeforeAlarm(showTime);
            waktuTunggu = Long.parseLong(beforeTime);
            Log.v("WAKTU TUNGGGGGGUUU",waktuTunggu+"");

            sholatArrayList = new ArrayList();
            gambar = new ArrayList();

            gambar.add(R.drawable.ibadahicon);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleIbadah);

            sholatArrayList.clear();
            sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatSubuh.getJamSholat())),convertDateFormat(sholatSubuh.getJamSholat()),gambar.get(0)));
            sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatDuhur.getJamSholat())),convertDateFormat(sholatDuhur.getJamSholat()),gambar.get(0)));
            Log.v("GEET ======== ",sholatArrayList.get(1).getWaktuTunggu());
            sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatAshar.getJamSholat())),convertDateFormat(sholatAshar.getJamSholat()),gambar.get(0)));
            sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatMaghrib.getJamSholat())),convertDateFormat(sholatMaghrib.getJamSholat()),gambar.get(0)));
            sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatIsya.getJamSholat())),convertDateFormat(sholatIsya.getJamSholat()),gambar.get(0)));

            recyclerView.setLayoutManager(new LinearLayoutManager(SholatFragment.this.getContext()));
            recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, SholatFragment.this.getContext()));
            try {
                soundNotif();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SholatFragment.this.mHandler.postDelayed(m_Runnable,3000);
        }

    };

    public String convertDateFormat(String time){
        String out;
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm aa");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        try {
            Date date = dateFormat.parse(time);
            date.setHours(date.getHours()-1); // ini di kurangin satu jam kerna error dari APInya yang selalu lebih dari 1 jam
            out = dateFormat2.format(date);
            return out;
        } catch (ParseException e) {
        }
        return "";
    }

    public String getTimeBeforeAlarm(String y){
        String x = String.valueOf(y.charAt(0));
        if (!String.valueOf(y.charAt(1)).equals(" ")){
            x = x + String.valueOf(y.charAt(1));
        }
        return x;
    }

    public void soundNotif() throws ParseException {
        pin0 = String.valueOf(sholatArrayList.get(0).getWaktuTunggu().charAt(6));
        pin1 = String.valueOf(sholatArrayList.get(1).getWaktuTunggu().charAt(6));
        pin2 = String.valueOf(sholatArrayList.get(2).getWaktuTunggu().charAt(6));
        pin3 = String.valueOf(sholatArrayList.get(3).getWaktuTunggu().charAt(6));
        pin4 = String.valueOf(sholatArrayList.get(4).getWaktuTunggu().charAt(6));

        //get detik
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date dateSecond = null;
        dateSecond = sdf.parse(sdf1.format(cal.getTime()));
        boolean c = false;
        int a = dateSecond.getSeconds();
        if (a==0 || a==1 || a==2 || a==3 || a==4 || a==5){
            c = true;
        }
        Log.v("BOOLEAN C ADALAH: ",c+" INI BOS " + a);
        if(pin0.equals("0") && sholatList.get(0).isSwitchSholat()== true && y==false  && c==true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(pin1.equals("0") && sholatList.get(1).isSwitchSholat()== true && y==false && c==true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(pin2.equals("0") && sholatList.get(2).isSwitchSholat()== true && y==false && c==true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
            Log.v("INI DIA","MASUK BOS");
        }else if(pin3.equals("0") && sholatList.get(3).isSwitchSholat()== true && y==false && c==true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(pin4.equals("0") && sholatList.get(4).isSwitchSholat()== true && y==false && c==true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }
//<<<<<<< HEAD

        if(pin0.equals("0") || pin0.equals("1") && pin0.equals("2") && pin0.equals("3")){
            y = true;
        }else if(pin1.equals("0") || pin1.equals("1") && pin1.equals("2") && pin1.equals("3")){
            y = true;
        }else if(pin2.equals("0") || pin2.equals("1") && pin2.equals("2") && pin2.equals("3")){
            y = true;
        }else if(pin3.equals("0") || pin3.equals("1") && pin3.equals("2") && pin3.equals("3")){
            y = true;
        }else if(pin4.equals("0") || pin4.equals("1") && pin4.equals("2") && pin4.equals("3")){
            y = true;
        }else{
            y=false;
        }

//        Log.v("CEK NOOOOOOOTIIIIIF ",sholatArrayList.get(2).getWaktuTunggu().equals("0")+" BOOLEAN "+sholatList.get(2).isSwitchSholat());
//=======
//>>>>>>> 6c943c7f481c9b3b2c234289ea0d0d4dbbdfc051
    }

    public String getDifferenceTime(String time){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long jam, menit, selisih;
        String imbuhan;
        Date date,date1 = null;
        try {
            date = sdf.parse(sdf1.format(cal.getTime()));
            date1 = sdf.parse(time);
            Log.e("waktu1jam",date.getHours()+"|"+date1.getHours());
            selisih = convertMinutes(date,date1);
            if( selisih > 0){
                imbuhan = "Menuju Adzan";
            }else if(selisih < 0){
                imbuhan = "Setelah Adzan";
                selisih = selisih*-1;
            }else{
                imbuhan = "Adzan";
            }
            jam = (selisih / 60); //menit
            menit = selisih % 60;

            Log.e("selisih",imbuhan+""+jam+" jam,"+menit+" menit,") ;
            if(jam == 0 ){
                return imbuhan+" "+menit+" menit";
            }else if(menit == 0){
                return imbuhan+" "+jam+" jam";
            }else{
                return imbuhan+" "+jam+" jam, "+menit+"menit";
            }


        } catch (ParseException e) {
        }
//
        return "";
    }

    public long convertMinutes(Date now, Date sholat) throws ParseException {
        long jam_now, jm_sholat, menit_now, menit_sholat, totalMenit, alarmBerbunyi;
        jam_now = now.getHours();
        jm_sholat = sholat.getHours();
        menit_now = now.getMinutes();
        menit_sholat = sholat.getMinutes();
        boolean x = false;
        boolean z = true;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date dateSecond = null;
        dateSecond = sdf.parse(sdf1.format(cal.getTime()));
        int a = dateSecond.getSeconds();
        if (a==0 || a==1 || a==2 ){
            z = false;
        }

        totalMenit = (jm_sholat*60+menit_sholat)-(jam_now*60+menit_now);

        alarmBerbunyi = totalMenit-waktuTunggu;
        Log.v("ALARm TUNGGU ADALAH: ",alarmBerbunyi+" YOWWWW");

        if(alarmBerbunyi == 0  && x == false && z==false){
            getActivity().startService(new Intent(getActivity(),AlarmService.class));
        }
        Log.v("NILAI PENGINGAT", alarmBerbunyi+ " = 0 DAN "+ Boolean.toString(x) + " = false DAN "+z+" = false");
//>>>>>>> 3d4f639613f6af4812c194b13af16843962140b7


        Log.v("Tanda Menit adalah",totalMenit+" BOOLEAN ");
        return  totalMenit;
    };

    //========================
    public static class MyService extends Service {
        private MediaPlayer mediaPlayer;

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
//            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
//            mediaPlayer.setLooping(true);
//            mediaPlayer.start();
//            Log.v("AFAAAAAAAAAAAAAN","Masuk kok stick");
            return START_STICKY;
        }

        @Override
        public void onDestroy() {
            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Log.v("AFAAAAAAAAAAAAAN","Masuk kok destroy");
           // mediaPlayer.stop();
            Log.v("AFAAAAAAAAAAAAAN","Masuk kok");
        }
    }
    //=======================

//    @Override
//    public void onResume() {
//        super.onResume();
//    }


}
