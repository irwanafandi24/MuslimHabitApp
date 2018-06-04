package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.illiyinmagang.miafandi.muslimhabitapp.LocationConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private TextView rd_ithna, rd_umm, rd_wml, rd_unisma, rd_isna, rd_hanafi, rd_salafi;
    private ImageView imgCheckKalkulasi, imgCheckJuristik;
    private View ln1, ln2, ln3, ln4, ln5, ln6, ln7;
    private LayoutParams lp, lp2;
    private RelativeLayout relPickLoc;
    private TextView txtLokasi;
    private LocationConfig locationConfig;
    private double longitude,latitude;
    private FusedLocationProviderClient mFusedLocationClient;

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

        relPickLoc.setOnClickListener(this);
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
                }else if(v==relPickLoc){
                    showDialogChangeLocation(this.getContext(),txtLokasi);
                }

    }

    private void showDialogChangeLocation(final Context context, final TextView txt) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_change_location, null);

        Button btnUbah = (Button) mView.findViewById(R.id.btn_ubah_location);
        Button btnBatal = (Button) mView.findViewById(R.id.btn_cancle_location);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                if ( !lm.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                }else{
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getParentFragment().getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1);
                    }else{
//                        Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if(location != null){
//                            longitude = location.getLongitude();
//                            latitude = location.getLatitude();
//                            locationConfig = new LocationConfig(context);
//                            locationConfig.getAddress(latitude,longitude);
//                            txt.setText(locationConfig.getAddressComplete());
//                        }else{
//                            Toast.makeText(context,"GPS Sedang Diaktifkan, Harap Tunggu sebentar dan Coba lagi",Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();

                        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        if (location != null) {
                                            longitude = location.getLongitude();
                                            latitude = location.getLatitude();
                                            locationConfig = new LocationConfig(context);
                                            locationConfig.getAddress(latitude,longitude);
                                            txt.setText(locationConfig.getAddressComplete());
                                        }else{
                                            Toast.makeText(context,"GPS Sedang Diaktifkan, Harap Tunggu sebentar dan Coba lagi",Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
}
