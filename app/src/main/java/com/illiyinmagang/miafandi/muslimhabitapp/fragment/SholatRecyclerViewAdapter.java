package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;
import com.illiyinmagang.miafandi.muslimhabitapp.model.Sholat;

import java.util.List;



public class SholatRecyclerViewAdapter extends RecyclerView.Adapter<SholatRecyclerViewAdapter.ViewHolder> {

    private final List<Sholat> mValues;
    private Context context;
    private  int row_index = -1;

    public SholatRecyclerViewAdapter(List<Sholat> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sholatlist, parent, false);
        final ViewHolder viewHolder = new ViewHolder(rootView);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.namaSholat.setText(mValues.get(position).getNamaSholat());
        //holder.mContentView.setText(mValues.get(position).getDeskripsi());
        holder.jamSholat.setText(mValues.get(position).getJamSholat());
        holder.tungguSholat.setText(mValues.get(position).getWaktuTunggu());
        holder.imageSholat.setImageResource(mValues.get(position).getImage());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.jamSholat.setTextColor(Color.parseColor("#23C27E"));
            holder.imageSholat.setImageResource(R.drawable.ibadahhijauicon);
        }
        else
        {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
            holder.jamSholat.setTextColor(Color.parseColor("#C4C4C4"));
            holder.imageSholat.setImageResource(R.drawable.ibadahicon);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            Event contentEvent = mValues.get(position);
//            Intent intent = new Intent(context, DetailEvent.class);
//            intent.putExtra("judulAcara",contentEvent.getJudul());
//            intent.putExtra("pembuatAcara",contentEvent.getPembuat());
//            intent.putExtra("tanggalAcara",contentEvent.getTanggal());
//            intent.putExtra("statusAcara",contentEvent.getStatus());
//            intent.putExtra("tempatAcara",contentEvent.getTempat());
//            intent.putExtra("deskripsiAcara",contentEvent.getDeskripsi());
//            intent.putExtra("jamAcara",contentEvent.getWaktu());
//            intent.putExtra("noteAcara",contentEvent.getNote());
//            intent.putExtra("posterAcara",contentEvent.getImage());
//            context.startActivity(intent);

        }
    }
}
