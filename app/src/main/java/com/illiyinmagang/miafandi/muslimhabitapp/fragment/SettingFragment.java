package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class SettingFragment extends Fragment implements View.OnClickListener{

    public SettingFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView rd_ithna, rd_umm, rd_wml, rd_unisma, rd_isna, rd_hanafi, rd_salafi;
    private ImageView imgCheckKalkulasi,imgCheckJuristik;
    private View ln1,ln2,ln3,ln4,ln5,ln6,ln7;
    private LayoutParams lp,lp2;
    private RelativeLayout relPickLoc;
    private TextView txtLokasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        relPickLoc = (RelativeLayout) v.findViewById(R.id.rel_pick_location);
        txtLokasi = (TextView) v.findViewById(R.id.txt_location_now);

        rd_isna = (TextView) v.findViewById(R.id.rd_ISNA);
        rd_ithna = (TextView) v.findViewById(R.id.rd_itna);
        rd_umm = (TextView) v.findViewById(R.id.rd_umm);
        rd_unisma = (TextView) v.findViewById(R.id.rd_Universitas_Islamic_Science);
        rd_wml = (TextView) v.findViewById(R.id.rd_wml);
        rd_salafi = (TextView) v.findViewById(R.id.rd_shalafi);
        rd_hanafi = (TextView) v.findViewById(R.id.rd_hanafi);

        ln1 = v.findViewById(R.id.ln1);
        ln2 = v.findViewById(R.id.ln2);
        ln3 = v.findViewById(R.id.ln3);
        ln4 = v.findViewById(R.id.ln4);
        ln5 = v.findViewById(R.id.ln5);
        ln6 = v.findViewById(R.id.ln6);
        ln7 = v.findViewById(R.id.ln7);

        imgCheckKalkulasi = (ImageView) v.findViewById(R.id.img_checked_kalkulasi);
        imgCheckJuristik = (ImageView) v.findViewById(R.id.imgCheckedJuristik);

        lp = (RelativeLayout.LayoutParams) imgCheckKalkulasi.getLayoutParams();
        lp2 = (RelativeLayout.LayoutParams) imgCheckJuristik.getLayoutParams();

        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        rd_isna.setOnClickListener(this);
        rd_unisma.setOnClickListener(this);
        rd_wml.setOnClickListener(this);
        rd_umm.setOnClickListener(this);
        rd_ithna.setOnClickListener(this);
        rd_salafi.setOnClickListener(this);
        rd_hanafi.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
                if(v == rd_isna){
                    lp.setMargins(0,0,0,20);
                    lp.addRule(RelativeLayout.ABOVE,ln3.getId());
                    imgCheckKalkulasi.setLayoutParams(lp);
                }else if(v == rd_wml){
                    lp.setMargins(0,0,0,20);
                    lp.addRule(RelativeLayout.ABOVE,ln4.getId());
                    imgCheckKalkulasi.setLayoutParams(lp);
                }else if(rd_unisma == v){
                    lp.addRule(RelativeLayout.ABOVE,ln2.getId());
                    lp.setMargins(0,0,0,20);
                    imgCheckKalkulasi.setLayoutParams(lp);
                }else if(rd_ithna == v){
                    lp.setMargins(0,0,0,20);
                    lp.addRule(RelativeLayout.ABOVE,ln1.getId());
                    imgCheckKalkulasi.setLayoutParams(lp);
                }else if(rd_umm == v){
                    lp.addRule(RelativeLayout.ABOVE,ln5.getId());
                    lp.setMargins(0,0,0,20);
                    imgCheckKalkulasi.setLayoutParams(lp);
                }else if(rd_hanafi == v){
                    lp2.addRule(RelativeLayout.ABOVE,ln7.getId());
                    lp2.setMargins(0,0,0,20);
                    imgCheckJuristik.setLayoutParams(lp2);
                }else if(rd_salafi == v){
                    lp2.addRule(RelativeLayout.ABOVE,ln6.getId());
                    lp2.setMargins(0,0,0,20);
                    imgCheckJuristik.setLayoutParams(lp2);
                }

    }
}
