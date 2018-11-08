package com.appturismo.proyectomoviles.Activities;


import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;


import com.appturismo.proyectomoviles.Fragments.ListaLugares;
import com.appturismo.proyectomoviles.Fragments.LugaresVisitados;
import com.appturismo.proyectomoviles.Fragments.Principal;
import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.utils.CurrentLocation;

import com.appturismo.proyectomoviles.utils.LocationData;

import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;


import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener,ListaLugares.OnItemTodoListener{
    LatLng location;
    private Marker marcador;
    private GoogleMap mMap;
    public static CurrentLocation currentLocation;
    public  static String correo;
    public static String usuario;


    private  Context context;
    public static LocationData data;
    public static  final  int CODIGO_SESSION=1;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipal,new Principal()).commit();
        BottomNavigationView navigationView1 = findViewById(R.id.nav_opciones);
        navigationView1.setOnNavigationItemSelectedListener(this);

        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public Context getContext() {
        return context;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.mapaprincipal:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipal,new Principal()).commit();
                break;
            case R.id.lugaresdisponibles:
                //Reemplazando el Fragment
               getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipal,new ListaLugares()).commit();
                break;
            case R.id.visitado:
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorPrincipal,new LugaresVisitados()).commit();
                break;
            case  R.id.inicioSession:
                Intent intent = new Intent(this,InicioSession.class);
                startActivityForResult(intent,CODIGO_SESSION);
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODIGO_SESSION:
                if(data==null)return;
                TextView correo= navigationView.findViewById(R.id.usuario);
                TextView usuario = navigationView.findViewById(R.id.nombre);
                correo.setText(data.getExtras().getString("correo"));
                usuario.setText(data.getExtras().getString("usuario"));
                break;
        }
    }

    @Override
    public void OnItemTodo(int operacion, Integer idTodo) {
        Intent intent = new Intent(this,InformacionLugar.class);
        intent.putExtra("IDLUGAR",idTodo);
        startActivity(intent);
    }
}
