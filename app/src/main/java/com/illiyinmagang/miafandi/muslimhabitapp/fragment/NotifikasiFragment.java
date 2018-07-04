package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Adapter.SholatAdapter;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Alarm;
import com.illiyinmagang.miafandi.muslimhabitapp.model.RealmHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajibNotif;
import com.illiyinmagang.miafandi.muslimhabitapp.fragment.SholatFragment;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NotifikasiFragment extends MyFragment implements View.OnClickListener{
    public NotifikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
//    private Switch swSubuh,swDuhur, swAshar, swMaghrib, swIsya;
//    private ImageView imgUbahWaktu;
    private TextView txtWaktuNotif;
    private RelativeLayout relPickTime;
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    SholatAdapter adapter;
    List<SholatWajibNotif> sholatList;
    List<Alarm> alarmSholat;
    String showTime, beforeTime;
    Long timeBeforeAlarm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notifikasi, container, false);

        txtWaktuNotif = (TextView) v.findViewById(R.id.txt_waktu_reminder);
        relPickTime = (RelativeLayout) v.findViewById(R.id.rel_pick_reminder);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycleSholatWajib);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //setup realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        sholatList = new ArrayList<>();
        alarmSholat = new ArrayList<>();

        sholatList = realmHelper.getAllSholat();
        if(sholatList.size() == 0){
            SholatWajibNotif s1 = new SholatWajibNotif("Subuh",true);
            realmHelper.saveData(s1);
            SholatWajibNotif s2 = new SholatWajibNotif("Duhur",true);
            realmHelper.saveData(s2);
            SholatWajibNotif s3 = new SholatWajibNotif("Ashar",false);
            realmHelper.saveData(s3);
            SholatWajibNotif s4 = new SholatWajibNotif("Magrib",true);
            realmHelper.saveData(s4);
            SholatWajibNotif s5 = new SholatWajibNotif("Isya",false);
            realmHelper.saveData(s5);
            Log.v("InsertData","DOne inserted");
        }
        alarmSholat = realmHelper.getAllAlarm();
        if(alarmSholat.size() == 0){
            Alarm n = new Alarm("10 Menit Sebelumnya");
            realmHelper.saveAlarem(n);
        }

        sholatList = realmHelper.getAllSholat();
        Log.v("Jumlah isi",sholatList.size()+"");
        adapter = new SholatAdapter(this.getContext(),sholatList);
        recyclerView.setAdapter(adapter);

        relPickTime.setOnClickListener(this);
        alarmSholat = realmHelper.getAllAlarm();
        for(Alarm alarmX:alarmSholat){
            txtWaktuNotif.setText(alarmX.getWaktuAlaram());
            showTime = alarmX.getWaktuAlaram();
        }

       // timeBeforeAlarm = Long.parseLong(beforeTime);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==relPickTime){
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
        mBuilder.setCancelable(false);
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
//>>>>>>> 3e608ed26405357874d06a84b2316aa92b65ab3f

    public void showAllertDialog(final Context context, final TextView txt){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_pilihwaktu_notifshalat, null);

        Button btnUbah = (Button) mView.findViewById(R.id.btn_ubah);
        Button btnBatal = (Button) mView.findViewById(R.id.btn_cancle);

        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String waktu = getStringFromRadio(mView);
                Toast.makeText(context,"Berhasil Diganti "+waktu,Toast.LENGTH_SHORT).show();
                realmHelper.updateAlarem(1,waktu+" Sebelumnya");
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

