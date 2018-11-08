package com.appturismo.proyectomoviles.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private String crearUsuario ="create table usuarios"+
            "("+
            "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," +
            "usuario VARCHAR(250),"+
            "nombre VARCHAR(250)," +
            "apellido VARCHAR(250)," +
            "correo VARCHAR(250)," +
            "fecha VARCHAR(20)," +
            "contraseña VARCHAR(100)" +
            ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //primero se crean las tablas maestros (sin Foreign Key)
        db.execSQL(crearUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //primero borrar las maestros-detalle
        db.execSQL("DROP TABLE IF EXISTS usuarios");
    }
}
