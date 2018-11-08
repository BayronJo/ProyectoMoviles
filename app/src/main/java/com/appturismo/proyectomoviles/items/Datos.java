package com.appturismo.proyectomoviles.items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Datos {
    public  Integer ID;
    public String Titulo;
    public String Descripcion;
    public String Latitud;
    public String Longitud;
    public String Dato;
    public Bitmap imagen;

    public String getDato() {
        return Dato;
    }


    public void setDato(String dato) {
        Dato = dato;
        try {
            byte[] bytecode = Base64.decode(dato,Base64.DEFAULT);
            this.imagen = BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public Integer getID() {
        return ID;
    }
    public Bitmap getImagen() {
        return imagen;
    }

    public String getLatitud() {
        return Latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }
}
