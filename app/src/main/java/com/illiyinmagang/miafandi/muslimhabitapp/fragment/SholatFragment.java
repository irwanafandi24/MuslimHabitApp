package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class SholatFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sholat, container, false);
    }
}
