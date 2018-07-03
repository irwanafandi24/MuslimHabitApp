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

import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.Notif.NotificationDisplayService;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SholatFragment extends MyFragment {
    private RecyclerView recyclerView;
    private ArrayList<Sholat> sholatArrayList;
    private ArrayList<Integer> gambar;
    private SholatAPI sholatAPI;
    private MyDateSelected myDateSelected;
    private Sholat sholatSubuh, sholatDuhur, sholatAshar, sholatMaghrib, sholatIsya;

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

        sholatAPI = new SholatAPI();

        sholatSubuh = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatsubuh();
        sholatDuhur = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatDuhur();
        sholatAshar = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatAshar();
        sholatMaghrib = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatMaghrib();
        sholatIsya = sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatIsya();

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

        Log.e("konvert",convertDateFormat(sholatAshar.getJamSholat()));
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

            jam = selisih / 60; //menit
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
        long jam_now, jm_sholat, menit_now, menit_sholat, totalMenit;
        jam_now = now.getHours();
        jm_sholat = sholat.getHours();
        menit_now = now.getMinutes();
        menit_sholat = sholat.getMinutes();

        totalMenit = (jm_sholat*60+menit_sholat)-(jam_now*60+menit_now);

        if (totalMenit == 0){
            getActivity().startService(new Intent(getActivity(),NotificationDisplayService.class));
        }

        Log.v("Tanda Menit adalah",totalMenit+"");
        return  totalMenit;
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}
