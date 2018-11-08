package com.appturismo.proyectomoviles.Api;

import com.appturismo.proyectomoviles.items.Datos;

import java.util.List;

public class Response {
    private String more;
    private List<Datos> usuario;

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public List<Datos> getResults() {
        return usuario;
    }

    public void setResults(List<Datos> usuario) {
        this.usuario = usuario;
    }
}
