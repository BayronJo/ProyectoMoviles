package com.appturismo.proyectomoviles.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appturismo.proyectomoviles.R;

public class CustomSwipeAdapter extends PagerAdapter {
    private int [] imagenes_resource ={R.drawable.imagen1, R.drawable.imagen2,R.drawable.imagen3,R.drawable.imagen4,R.drawable.imagen5,R.drawable.imagen6,R.drawable.imagen7};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.context = ctx;
    }
    @Override
    public int getCount() {
        return imagenes_resource.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return  (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = view.findViewById(R.id.image_view);
        //TextView textView = view.findViewById(R.id.image_count);
        imageView.setImageResource(imagenes_resource[position]);
       // textView.setText("Imagen : " +(position+1));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
