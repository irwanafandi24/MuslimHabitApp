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

import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;

import java.util.ArrayList;

public class SholatFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Sholat> sholatArrayList;
    private ArrayList<Integer> gambar;

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
        sholatArrayList = new ArrayList();
        gambar = new ArrayList();

        gambar.add(R.drawable.ibadahicon);
        sholatArrayList.add(new Sholat("Shalat Subuh","30 Menit Lagi","04.10",gambar.get(0)));
        sholatArrayList.add(new Sholat("Shalat Duhur","60 Jam Lalu","11.45",gambar.get(0)));
        sholatArrayList.add(new Sholat("Shalat Ashar","45 Menit Lalu","14.50",gambar.get(0)));
        sholatArrayList.add(new Sholat("Shalat Magrib","90 Menit Lagi","17.30",gambar.get(0)));
        sholatArrayList.add(new Sholat("Shalat Isya","55 Menit Lagi","18.27",gambar.get(0)));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleIbadah);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(new SholatRecyclerViewAdapter(sholatArrayList, this.getContext()));


        return rootView;
    }
}
