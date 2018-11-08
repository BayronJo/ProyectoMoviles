package com.appturismo.proyectomoviles.Modelo;

public class Usuario {
    private String id_usuario;
    private String usuario;
    private String nombre;
    private String apellido;
    private String correo;
    private  String fecha;
    private String contraseña;

    public Usuario(String id_usuario, String usuario, String nombre, String apellido, String correo, String fecha, String contraseña) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha = fecha;
        this.contraseña = contraseña;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getContraseña() {
        return contraseña;
    }
}
