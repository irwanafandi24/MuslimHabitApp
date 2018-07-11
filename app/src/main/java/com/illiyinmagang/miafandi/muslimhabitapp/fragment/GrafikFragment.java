package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.Srevice.myService;

public class GrafikFragment extends MyFragment implements View.OnClickListener{
    public GrafikFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private Button play, stop;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grafik, container, false);
        play = (Button) rootView.findViewById(R.id.btnPlay);
        stop = (Button) rootView.findViewById(R.id.btnStop);

        play.setOnClickListener(this);
        stop.setOnClickListener(this);


        return rootView;
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View view) {
        if (view == play){
            getActivity().startService(new Intent(getActivity(),myService.class));
            //getActivity().startForegroundService(new Intent(getActivity(),myService.class));
        }else if (view == stop){
            getActivity().stopService(new Intent(getActivity(),myService.class));
        }
    }
}
