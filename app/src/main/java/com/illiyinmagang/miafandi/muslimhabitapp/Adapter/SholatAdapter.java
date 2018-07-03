package com.illiyinmagang.miafandi.muslimhabitapp.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.RealmHelper;
import com.illiyinmagang.miafandi.muslimhabitapp.model.SholatWajibNotif;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SholatAdapter extends RecyclerView.Adapter<SholatAdapter.MyViewHolder>{
    private List<SholatWajibNotif> sholatList;
    Context context;
    Realm realm;
    RealmHelper realmHelper;
    public Boolean switched;

    public SholatAdapter(Context context, List<SholatWajibNotif> sholatList) {
        this.context = context;
        this.sholatList = sholatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_nortif_sholat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SholatWajibNotif sholatWajib = sholatList.get(position);
        holder.nameDetail.setText(sholatWajib.getNamaSholat());
        holder.switchDetail.setChecked(sholatWajib.isSwitchSholat());

    }

    @Override
    public int getItemCount() {
        return sholatList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameDetail;
        Switch switchDetail;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameDetail = itemView.findViewById(R.id.txtSholatNotif);
            switchDetail = itemView.findViewById(R.id.sw_sholat);

            //itemView.setOnClickListener(this);
            switchDetail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int posisi = sholatList.get(getAdapterPosition()).getId();
            String namaSholat = sholatList.get(getAdapterPosition()).getNamaSholat();
            Boolean check = sholatList.get(getAdapterPosition()).isSwitchSholat();
            Boolean switched = true;
            Log.v("Detail Checked",posisi + " - "+check);

            //=============================================
            if(check == true) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = LayoutInflater.from(context).inflate(R.layout.dialog_warning_notifshalat, null);
                //        getLayoutInflater().inflate(R.layout.dialog_warning_notifshalat, null);

                Button btnMati = (Button) mView.findViewById(R.id.btn_matikan);
                Button btnBatal = (Button) mView.findViewById(R.id.btn_batalkan);

                TextView contentWarning = (TextView) mView.findViewById(R.id.txt_content_warning);
                contentWarning.setText("Apakah Anda yakin ingin mematikan notifikasi shalat " + namaSholat.toUpperCase() + " ?");
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                btnMati.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchDetail.setChecked(false);
                        dialog.dismiss();
                    }
                });

                btnBatal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchDetail.setChecked(true);
                        dialog.cancel();
                    }
                });
            }
            //=============================================

            if(!switchDetail.isChecked()){
                switched = false;
            }

            Log.v("Masuk sini kan ya","YASS"+posisi+namaSholat+switched);
            RealmConfiguration configuration = new RealmConfiguration.Builder().build();
            realm = Realm.getInstance(configuration);

            realmHelper = new RealmHelper(realm);
            realmHelper.updateSholat(posisi,namaSholat,switched);
        }
    }
}
