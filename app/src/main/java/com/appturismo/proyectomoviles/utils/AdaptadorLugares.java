package com.appturismo.proyectomoviles.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.items.Datos;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdaptadorLugares extends RecyclerView.Adapter<AdaptadorLugares.ViewHolder> {

    private List<Datos> mDataSet;
    private OnItemClickListener listener;
    private int idLayout;
    private Context context;

    public AdaptadorLugares(Context context, List<Datos> mDataSet, int idLayout, OnItemClickListener listener) {
        this.context = context;
        this.mDataSet = mDataSet;
        this.idLayout = idLayout;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(idLayout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(this.mDataSet.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }


    public interface OnItemClickListener{
        void onItemClick(Datos toDo, int position);
    }

    public void setmDataSet(List<Datos> mDataSet) {
        this.mDataSet = mDataSet;
        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView imagen;
        @BindView(R.id.lblbTitulo)
        TextView lbltitulo;
        @BindView(R.id.lblDescripcion)
        TextView lbldescripcion;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(final Datos datos, final OnItemClickListener listener) {
            this.lbltitulo.setText(datos.getTitulo());
            //this.lbldescripcion.setText(datos.getDescripcion());
            datos.setDato(datos.getDato());
            if(datos.getImagen()!=null){
                this.imagen.setImageBitmap(datos.getImagen());
            }else{
                this.imagen.setImageResource(R.drawable.ic_launcher_background);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(datos,getAdapterPosition());
                }
            });
        }
    }
}
