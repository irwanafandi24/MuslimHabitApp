package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class IbadahFragment extends Fragment {
    public IbadahFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ibadah, container, false);
        // Inflate the layout for this fragment

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.viewPagerHome);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(this.getActivity().getSupportFragmentManager());
        mViewPagerAdapter.addFragment(SholatFragment.newInstance(),"SHALAT");
        mViewPagerAdapter.addFragment(PuasaFragment.newInstance(),"PUASA");
        mViewPagerAdapter.addFragment(SedekahFragment.newInstance(),"SEDEKAH");
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#23C27E"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#23C27E"));

        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }
}
