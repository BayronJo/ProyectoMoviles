package com.appturismo.proyectomoviles.Activities;

import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.utils.CustomSwipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformacionLugar extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
   private Integer id;
   private TextView txtdescripcion;
   private ProgressDialog progreso;
   RequestQueue requestQueue;
   JsonObjectRequest jsonObjectRequest;
   @BindView(R.id.imagen)
    RelativeLayout relativeLayout;
    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_lugar);
        ButterKnife.bind(this);
        Toolbar toolbar =  findViewById(R.id.toolbarinfo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtdescripcion = findViewById(R.id.Descripcion);
        id=getIntent().getExtras().getInt("IDLUGAR");
        requestQueue = Volley.newRequestQueue(this);
        ConsultarDatos();
        viewPager = findViewById(R.id.view_pager);
        customSwipeAdapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(customSwipeAdapter);
    }

    private void ConsultarDatos(){
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando Datos");
        progreso.show();
        String URL ="http://apiturismo.atspace.cc/ConsultaLugares.php?Id="+id+"";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        JSONArray json = response.optJSONArray("usuario");
        try {
            for(int i=0;i<json.length();i++){
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                txtdescripcion.setText(jsonObject.getString("Descripcion"));
                getSupportActionBar().setTitle(jsonObject.getString("Titulo"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
