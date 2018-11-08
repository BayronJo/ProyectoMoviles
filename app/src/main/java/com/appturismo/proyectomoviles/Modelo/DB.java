package com.appturismo.proyectomoviles.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DB {
    private  DBHelper dbHelper;
    public DB(Context context){
        //se define el nombre de la base de datos
        dbHelper = new DBHelper(context,"BD_Turismo",null,1);
    }

    //Consulta Multitabla
    public Cursor getCursorUsuario(String usuario,String contraseña){
        return dbHelper.getReadableDatabase().rawQuery(
                "SELECT usuario,nombre,apellido,contraseña,correo FROM usuarios WHERE correo =?  and contraseña =?", new String[]{usuario,contraseña});
    }

    public boolean guardar_O_ActualizarCategoria(Usuario usuario){
        ContentValues initialValues = new ContentValues();
        if(!usuario.getId_usuario().isEmpty())
            initialValues.put("id_usuario", Integer.parseInt(usuario.getId_usuario()));
        initialValues.put("usuario",usuario.getUsuario());
        initialValues.put("nombre",usuario.getNombre());
        initialValues.put("apellido",usuario.getApellido());
        initialValues.put("correo",usuario.getCorreo());
        initialValues.put("fecha",usuario.getFecha());
        initialValues.put("contraseña",usuario.getContraseña());
        int id = (int) dbHelper.getWritableDatabase().insertWithOnConflict(
                "usuarios",
                null,
                initialValues,
                SQLiteDatabase.CONFLICT_REPLACE);
        return id>0;
    }

    public void borrarCategoria(String id){
        dbHelper.getWritableDatabase().execSQL(String.format("DELETE FROM categoria WHERE id_categoria ='%s'",id));
    }
    public void borrarProducto(String id){
        dbHelper.getWritableDatabase().execSQL(String.format("DELETE FROM producto WHERE id_producto ='%s'",id));
    }
}
