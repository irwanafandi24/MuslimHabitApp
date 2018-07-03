package com.illiyinmagang.miafandi.muslimhabitapp.introslider;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.LoginActivity;
import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewPager slider;
    private TextView txtSkip, txtNext;
    private TextView[] tanda;
    private LinearLayout layoutTitik;
    int posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        slider = (ViewPager) findViewById(R.id.vp_myslider);
        txtNext = (TextView) findViewById(R.id.txt_next);
        txtSkip = (TextView) findViewById(R.id.txt_skip);
        layoutTitik = (LinearLayout) findViewById(R.id.layout_titik);

        slider.setAdapter(new SliderAdapter(IntroActivity.this));
        tambahIndokator(0);
        slider.addOnPageChangeListener(changeListener);
        txtNext.setOnClickListener(this);
        txtSkip.setOnClickListener(this);
    }

    public void tambahIndokator(int position) {
        tanda = new TextView[3];
        layoutTitik.removeAllViews();

        for (int i = 0; i < tanda.length; i++) {
            tanda[i] = new TextView(this);
            tanda[i].setText(Html.fromHtml("&#8226;"));
            tanda[i].setTextColor(Color.parseColor("#23C27E"));

            layoutTitik.addView(tanda[i]);
        }
        if (tanda.length > 0) {
            tanda[position].setTextColor(Color.WHITE);
        }
        if (position > 0) {
            txtSkip.setText("BACK");
        } else {
            txtSkip.setText("SKIP");
        }
        if (position == 2) {
            txtNext.setText("DONE");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == txtNext) {
            if (posisi < 2) {
                slider.setCurrentItem(posisi + 1);
            } else if (txtNext.getText().toString().equals("DONE")) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
//            }
            } else if (v == txtSkip) {
                if (posisi > 0) {
                    slider.setCurrentItem(posisi - 1);
                } else if (posisi == 0) {
                    startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                }
            }
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            tambahIndokator(position);
//            ini caranya ngeget posisi konten yang ditampilkan saat ini, sengaja di isikan ke variable global 'posisi' biar bisa dipakek untuk action button next,back, skip
            posisi = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}