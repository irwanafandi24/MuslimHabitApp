package com.illiyinmagang.miafandi.muslimhabitapp.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.namaSholat.setText(mValues.get(position).getNamaSholat());
        holder.jamSholat.setText(mValues.get(position).getJamSholat());
        holder.tungguSholat.setText(mValues.get(position).getWaktuTunggu());
        holder.imageSholat.setImageResource(mValues.get(position).getImage());

//        if(holder.mItem.getImbuhan().equals("Menuju Adzan")){
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
//            holder.jamSholat.setTextColor(Color.parseColor("#C4C4C4"));
//            holder.imageSholat.setImageResource(R.drawable.ibadahicon);
//        }else if(holder.mItem.getImbuhan().equals("Adzan")){
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            holder.jamSholat.setTextColor(Color.parseColor("#23C27E"));
//            holder.imageSholat.setImageResource(R.drawable.ibadahhijauicon);
//            notifyDataSetChanged();
//            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    reportingForm();
//                    notifyDataSetChanged();
//
//                }
//            });
//        }else{
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffcdd2"));
//            holder.jamSholat.setTextColor(Color.parseColor("#f44336"));
//            holder.imageSholat.setImageResource(R.drawable.ibadahicon);
//            notifyDataSetChanged();
//        }
//        if(row_index==position){
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            holder.jamSholat.setTextColor(Color.parseColor("#23C27E"));
//            holder.imageSholat.setImageResource(R.drawable.ibadahhijauicon);
//
//            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
//
//            final View mView = li.inflate(R.layout.dialogsholat, null);
//                Spinner spinnerJenisSholat = (Spinner) mView.findViewById(R.id.spinnerShalat);
//                ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(context,R.array.spinSholat,android.R.layout.simple_spinner_item);
//                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinnerJenisSholat.setAdapter(myAdapter);
//                spinnerJenisSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//            Spinner spinnerTempatSholat = (Spinner) mView.findViewById(R.id.spinnerTempat);
//            ArrayAdapter<CharSequence> myAdapter2 = ArrayAdapter.createFromResource(context,R.array.spinTempatSholat,android.R.layout.simple_spinner_item);
//            myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerTempatSholat.setAdapter(myAdapter2);
//            spinnerTempatSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//            Spinner spinnerQobliyah = (Spinner) mView.findViewById(R.id.spinnerQobliyah);
//            ArrayAdapter<CharSequence> myAdapter3 = ArrayAdapter.createFromResource(context,R.array.spinKonfir,android.R.layout.simple_spinner_item);
//            myAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerQobliyah.setAdapter(myAdapter3);
//            spinnerQobliyah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//            Spinner spinnerBadiyah = (Spinner) mView.findViewById(R.id.spinnerBadiyah);
//            ArrayAdapter<CharSequence> myAdapter4 = ArrayAdapter.createFromResource(context,R.array.spinKonfir,android.R.layout.simple_spinner_item);
//            myAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerBadiyah.setAdapter(myAdapter4);
//            spinnerBadiyah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
//
//            Button btnKonfirmasi =  (Button) mView.findViewById(R.id.btnKonfirmSholat);
//
//            mBuilder.setView(mView);
//            final AlertDialog dialog = mBuilder.create();
//            dialog.show();
//
//            btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.cancel();
//                }
//            });
//        }
//        else
//        {
//            holder.relativeLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
//            holder.jamSholat.setTextColor(Color.parseColor("#C4C4C4"));
//            holder.imageSholat.setImageResource(R.drawable.ibadahicon);
//        }
//
//

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
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void reportingForm(){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);

        final View mView = li.inflate(R.layout.dialogsholat, null);
        Spinner spinnerJenisSholat = (Spinner) mView.findViewById(R.id.spinnerShalat);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(context,R.array.spinSholat,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisSholat.setAdapter(myAdapter);
        spinnerJenisSholat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Spinner spinnerTempatSholat = (Spinner) mView.findViewById(R.id.spinnerTempat);
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

        Spinner spinnerQobliyah = (Spinner) mView.findViewById(R.id.spinnerQobliyah);
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

        Spinner spinnerBadiyah = (Spinner) mView.findViewById(R.id.spinnerBadiyah);
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

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}
