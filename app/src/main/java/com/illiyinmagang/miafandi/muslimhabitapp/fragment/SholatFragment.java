package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyDateSelected;
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

    //for alarm and get Sholat time
    private Realm realm;
    private RealmHelper realmHelper;
    private List<SholatWajibNotif> sholatList;
    private List<Alarm> alarmSholat;
    private String showTime, beforeTime;
    private Long waktuTunggu;

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
        View rootView = inflater.inflate(R.layout.fragment_sholat, container, false);

        myDateSelected = new MyDateSelected(getContext());
        myDateSelected.getMyPosisition();
        Toast.makeText(getContext(),myDateSelected.getMyPosisition()+"",Toast.LENGTH_LONG).show();

        sholatAPI = new SholatAPI(getContext());
        sholatSubuh = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatsubuh();
        sholatDuhur = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatDuhur();
        sholatAshar = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatAshar();
        sholatMaghrib = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatMaghrib();
        sholatIsya = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatIsya();

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        sholatList = new ArrayList<>();
        alarmSholat = new ArrayList<>();

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

        if(alarmSholat.size() == 0){
            Alarm n = new Alarm("10 Menit Sebelumnya");
            realmHelper.saveAlarem(n);
        }
        alarmSholat = realmHelper.getAllAlarm();


        for(Alarm alarmX:alarmSholat){
            showTime = alarmX.getWaktuAlaram();
        }

        Log.v("SHOW TIME",showTime+"");
        beforeTime = getTimeBeforeAlarm(showTime);
        waktuTunggu = Long.parseLong(beforeTime);
        Log.v("UBAH KE INTEGER",waktuTunggu+"");

        sholatArrayList = new ArrayList();
        gambar = new ArrayList();

        gambar.add(R.drawable.ibadahicon);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleIbadah);

        sholatArrayList.clear();
        sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatSubuh.getJamSholat())),convertDateFormat(sholatSubuh.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatDuhur.getJamSholat())),convertDateFormat(sholatDuhur.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatAshar.getJamSholat())),convertDateFormat(sholatAshar.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatMaghrib.getJamSholat())),convertDateFormat(sholatMaghrib.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),getDifferenceTime(convertDateFormat(sholatIsya.getJamSholat())),convertDateFormat(sholatIsya.getJamSholat()),gambar.get(0)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, this.getContext()));
        soundNotif();

//        Log.e("konvert",convertDateFormat(sholatAshar.getJamSholat()));
        return rootView;
    }

    public String convertDateFormat(String time){
        String out;
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm aa");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        try {
            Date date = dateFormat.parse(time);
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

    public void soundNotif(){
        if(String.valueOf(sholatArrayList.get(0).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(0).isSwitchSholat()== true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(String.valueOf(sholatArrayList.get(1).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(1).isSwitchSholat()== true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(String.valueOf(sholatArrayList.get(2).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(2).isSwitchSholat()== true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
            Log.v("INI DIA","MASUK BOS");
        }else if(String.valueOf(sholatArrayList.get(3).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(3).isSwitchSholat()== true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }else if(String.valueOf(sholatArrayList.get(4).getWaktuTunggu().charAt(6)).equals("0") && sholatList.get(4).isSwitchSholat()== true){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }
    }

    public String getDifferenceTime(String time){
//        Log.e("selisih",time) ;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        long jam, menit, selisih;
        String imbuhan;
        Date date,date1 = null;
        try {
            date = sdf.parse(sdf1.format(cal.getTime()));
            date1 = sdf.parse(time);

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

    public long convertMinutes(Date now, Date sholat){
        long jam_now, jm_sholat, menit_now, menit_sholat, totalMenit, alarmBerbunyi;
        jam_now = now.getHours();
        jm_sholat = sholat.getHours()-1; // ini soalnya dari API dapetnya +1 jam
        menit_now = now.getMinutes();
        menit_sholat = sholat.getMinutes();

        totalMenit = (jm_sholat*60+menit_sholat)-(jam_now*60+menit_now);
//        alarmBerbunyi = totalMenit-waktuTunggu;

//        if(alarmBerbunyi == 0){
//            getActivity().startService(new Intent(getActivity(),AlarmService.class));
//        }

        Log.v("Tanda Menit adalah",totalMenit+"");
        return  totalMenit;
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}
