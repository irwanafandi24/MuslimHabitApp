package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;

import java.util.ArrayList;
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
        sholatArrayList.add(new Sholat(sholatSubuh.getNamaSholat(),"30 Menit Lagi",sholatSubuh.getJamSholat(),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatDuhur.getNamaSholat(),"30 Menit Lagi",sholatDuhur.getJamSholat(),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatAshar.getNamaSholat(),"30 Menit Lagi",sholatAshar.getJamSholat(),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatMaghrib.getNamaSholat(),"30 Menit Lagi",sholatMaghrib.getJamSholat(),gambar.get(0)));
        sholatArrayList.add(new Sholat(sholatIsya.getNamaSholat(),"30 Menit Lagi",sholatIsya.getJamSholat(),gambar.get(0)));

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, this.getContext()));
        return rootView;
    }
}
