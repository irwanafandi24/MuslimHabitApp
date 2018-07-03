package com.illiyinmagang.miafandi.muslimhabitapp.introslider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.illiyinmagang.miafandi.muslimhabitapp.R;

/**
 * Created by user on 03/07/2018.
 */

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public String isikonten[] = {"isi konten 1","isi konten 2", "isi konten 3"};
    public int isiGambar[] = {R.drawable.notepad,R.drawable.bell,R.drawable.stats};

    public SliderAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return isikonten.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //ini relative layout yang dari R.layout.content_slider1
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v= layoutInflater.from(context).inflate(R.layout.content_slider,container,false);

        TextView txtIsiContent = (TextView) v.findViewById(R.id.txt_isi_slider);
        ImageView img = (ImageView) v.findViewById(R.id.img_icon_view);
        txtIsiContent.setText(isikonten[position]);
        img.setImageResource(isiGambar[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
