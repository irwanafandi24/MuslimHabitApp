package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class InfoFragment extends Fragment {
    private CollapsingToolbarLayout CoolToolbar;
    private ImageView img;
    private Toolbar toolbar;
    private Boolean ExpandedActionBar = true;

    //Tab
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = getActivity(); //gimana nih


    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        CoolToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.MyCollapseToolbar);

        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(this.getActivity().getSupportFragmentManager());
        mViewPagerAdapter.addFragment(SholatFragment.newInstance(),"Sholat");
        mViewPagerAdapter.addFragment(PuasaFragment.newInstance(),"Puasa");
        mViewPagerAdapter.addFragment(SedekahFragment.newInstance(),"Sedekah");
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setCurrentItem(0);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Sholat"));
        tabLayout.addTab(tabLayout.newTab().setText("Puasa"));
        tabLayout.addTab(tabLayout.newTab().setText("Sedekah"));

        return rootView;
    }
}
