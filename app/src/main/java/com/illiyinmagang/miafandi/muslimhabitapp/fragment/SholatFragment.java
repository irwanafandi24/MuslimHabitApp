package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),getDifferenceTime(sholatSubuh.getJamSholat()),convertDateFormat(sholatSubuh.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),getDifferenceTime(sholatDuhur.getJamSholat()),convertDateFormat(sholatDuhur.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),getDifferenceTime(sholatAshar.getJamSholat()),convertDateFormat(sholatAshar.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),getDifferenceTime(sholatMaghrib.getJamSholat()),convertDateFormat(sholatMaghrib.getJamSholat()),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),getDifferenceTime(sholatIsya.getJamSholat()),convertDateFormat(sholatIsya.getJamSholat()),gambar.get(0)));

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
        long jam, menit, detik, selisih;
        String imbuhan;
        Date date,date1 = null;
        try {
            date = sdf.parse(sdf1.format(cal.getTime()));
            date1 = sdf.parse(time);
//            Log.e("selisih",date.getTime()+"."+date1.getTime()) ;

            if(date.getTime() < date1.getTime()){
                selisih = date1.getTime() - date.getTime();
                imbuhan = "Menuju Adzan";
            }else{
                selisih =  date.getTime() - date1.getTime();
                imbuhan = "Setelah Adzan";
            }

            jam = selisih / (60 * 1000) % 60;;
            menit = selisih / (60 * 60 * 1000) % 24;
            detik = selisih / 1000 % 60;

            Log.e("selisih",imbuhan+""+jam+" jam,"+menit+" menit,"+detik+"detik") ;
//                tinggal ngasih kondisi if aja disini
            if(jam >= 1){
                return imbuhan+" "+menit+" menit,"+detik+"detik";
            }else if(menit >= 1){
                return imbuhan+detik+"detik";
            }else if(detik >= 5){
                return "Saatnya Adzan";
            }else{
                return imbuhan+""+jam+" jam,"+menit+" menit,"+detik+"detik";
            }
        } catch (ParseException e) {
//            Log.e("selisih",e.getMessage());
        }
//
        return "";
    }
}
