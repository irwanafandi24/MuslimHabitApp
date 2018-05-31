package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class NotifikasiFragment extends Fragment implements View.OnClickListener{
    public NotifikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private Switch swSubuh,swDuhur, swAshar, swMaghrib, swIsya;
    private TextView txtWaktuNotif;
    private ImageView imgUbahWaktu;
    private RelativeLayout relPickTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifikasi, container, false);
        swSubuh = (Switch) v.findViewById(R.id.sw_subuh);
        swDuhur = (Switch) v.findViewById(R.id.sw_duhur);
        swAshar = (Switch) v.findViewById(R.id.sw_ashar);
        swMaghrib = (Switch) v.findViewById(R.id.sw_maghrib);
        swIsya = (Switch) v.findViewById(R.id.sw_isya);

        txtWaktuNotif = (TextView) v.findViewById(R.id.txt_waktu_reminder);
        relPickTime = (RelativeLayout) v.findViewById(R.id.rel_pick_reminder);

        swIsya.setOnClickListener(this);
        swAshar.setOnClickListener(this);
        swDuhur.setOnClickListener(this);
        swMaghrib.setOnClickListener(this);
        swSubuh.setOnClickListener(this);

        relPickTime.setOnClickListener(this);

        txtWaktuNotif.setText("15 Menit Sebelumnya");
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==swSubuh){
            if(!swSubuh.isChecked()){
                showAllertDialog("Peringatan",swSubuh.getText().toString(),this.getContext(),swSubuh);
            }
        }else if(v==swDuhur){
            if(!swDuhur.isChecked()){
                showAllertDialog("Peringatan",swDuhur.getText().toString(),this.getContext(),swDuhur);
            }
        }else if(v==swAshar){
            if(!swAshar.isChecked()){
                showAllertDialog("Peringatan",swAshar.getText().toString(),this.getContext(),swAshar);
            }
        }else if(v==swMaghrib){
            if(!swMaghrib.isChecked()){
                showAllertDialog("Peringatan",swAshar.getText().toString().toString(),this.getContext(),swMaghrib);
            }
        }else if(v==swIsya){
            if(!swIsya.isChecked()){
                showAllertDialog("Peringatan",swIsya.getText().toString(),this.getContext(),swIsya);
            }
        }else if(v==relPickTime){
            showAllertDialog(this.getContext(),txtWaktuNotif);
        }
    }

    public void showAllertDialog(String judul, String Content, Context context, final Switch sw){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = getLayoutInflater().inflate(R.layout.dialog_warning_notifshalat, null);

        Button btnMati = (Button) mView.findViewById(R.id.btn_matikan);
        Button btnBatal = (Button) mView.findViewById(R.id.btn_batalkan);

        TextView contentWarning = (TextView) mView.findViewById(R.id.txt_content_warning);
        contentWarning.setText("Apakah Anda yakin ingin mematikan notifikasi shalat "+Content.toUpperCase()+" ?");
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnMati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sw.setChecked(false);
                dialog.dismiss();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw.setChecked(true);
                dialog.cancel();
            }
        });

    }

    public void showAllertDialog(final Context context, final TextView txt){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_pilihwaktu_notifshalat, null);

        Button btnUbah = (Button) mView.findViewById(R.id.btn_ubah);
        Button btnBatal = (Button) mView.findViewById(R.id.btn_cancle);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String waktu = getStringFromRadio(mView);
                Toast.makeText(context,"Berhasil Diganti "+waktu,Toast.LENGTH_SHORT).show();
                txt.setText(waktu+" Sebelumnya");
                dialog.dismiss();
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    private String getStringFromRadio(View mView) {
        RadioButton rd3m = (RadioButton) mView.findViewById(R.id.rd_3m);
        RadioButton rd5m = (RadioButton) mView.findViewById(R.id.rd_5m);
        RadioButton rd10m = (RadioButton) mView.findViewById(R.id.rd_10m);
        RadioButton rd15m = (RadioButton) mView.findViewById(R.id.rd_15m);
        RadioButton rd30m = (RadioButton) mView.findViewById(R.id.rd_30m);

        if(rd3m.isChecked()){
            return rd3m.getText().toString();
        }else if(rd5m.isChecked()){
            return rd5m.getText().toString();
        }else if(rd10m.isChecked()){
            return rd10m.getText().toString();
        }else if(rd30m.isChecked()){
            return rd30m.getText().toString();
        }else if(rd15m.isChecked()){
            return rd15m.getText().toString();
        }
        return "";
    }
}

