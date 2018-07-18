package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyDateSelected;
import com.illiyinmagang.miafandi.muslimhabitapp.Config.Preferences.MyLoginConfig;
import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.ServerHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatAPI;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;


public class SholatRecyclerViewAdapter extends RecyclerView.Adapter<SholatRecyclerViewAdapter.ViewHolder> {

    private final List<Sholat> mValues;
    private Context context;
    private  int row_index = -1;
    private ServerHelper serverHelper;
    private String vWaktu = "";
    private MyLoginConfig myLoginConfig;
    private SholatAPI sholatAPI;
    private MyDateSelected myDateSelected;

    public SholatRecyclerViewAdapter(List<Sholat> items, Context context) {
        mValues = items;
        this.context = context;
        this.serverHelper = new ServerHelper(context);
        try {
            sholatAPI = new SholatAPI(context);
            this.myDateSelected = new MyDateSelected(context);
            this.myLoginConfig = new MyLoginConfig(context);
        }catch (Exception w){
            Log.e("errornjir",w.getLocalizedMessage());
            w.printStackTrace();
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sholatlist, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.namaSholat.setText(mValues.get(position).getNamaSholat());
        holder.jamSholat.setText(mValues.get(position).getJamSholat());
        holder.tungguSholat.setText(mValues.get(position).getWaktuTunggu());
        Log.e("gambar",holder.mItem.getImage()+"");

        if(holder.mItem.getImage() == 0){
            holder.imageSholat.setImageResource(R.drawable.ibadahicon);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getDateNow().equals(sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getTanggalLengkap())){
                        reportingForm(holder.namaSholat.getText().toString());
                    }else{
                        Toast.makeText(context,"Belum saatnya anda ngisi ini",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            holder.imageSholat.setImageResource(holder.mItem.getImage());
        }

    }

    private String getDateNow() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView namaSholat;
        //public final TextView mContentView;
        public final TextView tungguSholat;
        public final TextView jamSholat;
        public ImageView imageSholat;
        RelativeLayout relativeLayout;

        public Sholat mItem;

        public ViewHolder(View view) {
            super(view);
            namaSholat = (TextView) view.findViewById(R.id.txtShalat);
            tungguSholat = (TextView) view.findViewById(R.id.txtWaktu);
            jamSholat = (TextView) view.findViewById(R.id.txtJam);
            imageSholat = (ImageView) view.findViewById(R.id.iconSholat);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relatifSholat);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

    }

    public void reportingForm(final String namaSholat){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

        final View mView = li.inflate(R.layout.dialogsholat, null);

        final Spinner spinnerJenisSholat = (Spinner) mView.findViewById(R.id.spinnerShalat);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(context,R.array.spinSholat,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisSholat.setAdapter(myAdapter);
        spinnerJenisSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerJenisSholat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner spinnerTempatSholat = (Spinner) mView.findViewById(R.id.spinnerTempat);
        ArrayAdapter<CharSequence> myAdapter2 = ArrayAdapter.createFromResource(context,R.array.spinTempatSholat,android.R.layout.simple_spinner_item);
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTempatSholat.setAdapter(myAdapter2);
        spinnerTempatSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner spinnerQobliyah = (Spinner) mView.findViewById(R.id.spinnerQobliyah);
        ArrayAdapter<CharSequence> myAdapter3 = ArrayAdapter.createFromResource(context,R.array.spinKonfir,android.R.layout.simple_spinner_item);
        myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQobliyah.setAdapter(myAdapter3);
        spinnerQobliyah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner spinnerBadiyah = (Spinner) mView.findViewById(R.id.spinnerBadiyah);
        ArrayAdapter<CharSequence> myAdapter4 = ArrayAdapter.createFromResource(context,R.array.spinKonfir,android.R.layout.simple_spinner_item);
        myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBadiyah.setAdapter(myAdapter4);
        spinnerBadiyah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button btnKonfirmasi =  (Button) mView.findViewById(R.id.btnKonfirmSholat);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        final RadioButton radioButtonYes = (RadioButton) mView.findViewById(R.id.waktuYes);
        final RadioButton radioButtonNo = (RadioButton) mView.findViewById(R.id.waktuNo);

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioButtonYes.isChecked()){
                    vWaktu = "100";
                }else if(radioButtonNo.isChecked()){
                    vWaktu = "0";
                }
                changeImage(namaSholat);
                serverHelper.InsertRekap(String.valueOf(myLoginConfig.getDataID(myLoginConfig.KEY_ID)),namaSholat,vWaktu,spinnerTempatSholat.getSelectedItem().toString(),spinnerJenisSholat.getSelectedItem().toString(),
                        spinnerBadiyah.getSelectedItem().toString(),spinnerQobliyah.getSelectedItem().toString());
                dialog.dismiss();
            }
        });
    }

    private void changeImage(String namaS) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        switch (namaS){
            case "Subuh":
                sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatsubuh().setImage(R.drawable.ibadahhijauicon);
                break;
            case "Duhur":
                sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatDuhur().setImage(R.drawable.ibadahhijauicon);
                break;
            case "Ashar":
                sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatAshar().setImage(R.drawable.ibadahhijauicon);
                break;
            case "Maghrib":
                sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatMaghrib().setImage(R.drawable.ibadahhijauicon);
                break;
            case "Isya":
                sholatAPI.getDataShalat().get(myDateSelected.getMyPosisition()).getSholatIsya().setImage(R.drawable.ibadahhijauicon);
                break;
        }
        realm.commitTransaction();
    }
}
