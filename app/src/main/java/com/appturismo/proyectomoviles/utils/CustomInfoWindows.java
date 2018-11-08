package com.appturismo.proyectomoviles.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.items.Datos;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomInfoWindows implements GoogleMap.InfoWindowAdapter{
    private Activity context;

    @BindView(R.id.txtvTitle)
    TextView title;
    public CustomInfoWindows(Activity context) {
        this.context = context;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
    @Override
    public View getInfoContents(final Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custom_infowindow, null);
        ButterKnife.bind(this, view);
        final Datos data = new Gson().fromJson(marker.getSnippet(), Datos.class);
        if(data != null){
            this.title.setText(marker.getTitle());
        }
        return view;

    }
}
