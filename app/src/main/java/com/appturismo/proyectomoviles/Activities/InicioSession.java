package com.appturismo.proyectomoviles.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appturismo.proyectomoviles.Modelo.DB;
import com.appturismo.proyectomoviles.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class InicioSession extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener{

    private Cursor fila;
    private DB db;
    @BindView(R.id.textcorreo)
    @NotEmpty(messageId = R.id.txtCorreo)
    EditText txtcorreo;
    @BindView(R.id.txtcontra)
    @NotEmpty(messageId = R.id.txtcontra)
    EditText txtContra;
    @BindView(R.id.btnentrar)
    Button btnEntrar;
    @BindView(R.id.txtregistrate)
    TextView resigtro;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private ProgressDialog progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio_session);
        ButterKnife.bind(this);
        Toolbar toolbar =  findViewById(R.id.toolbarlogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new DB(this);
        btnEntrar.setOnClickListener(this);
        resigtro.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtregistrate:
                Intent registro = new Intent(this,Registro.class);
                startActivity(registro);
                break;
            case R.id.btnentrar:
                if(FormValidator.validate(this,new SimpleErrorPopupCallback(this))){
                    progreso = new ProgressDialog(this);
                    progreso.setMessage("Resgistrando....");
                    progreso.show();
                    String Url = "http://apiturismo.atspace.cc/ListaUsuario.php?Correo="+txtcorreo.getText().toString()+"&Credencial="+txtContra.getText().toString()+"";
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,Url,null,this,this);
                    requestQueue.add(jsonObjectRequest);
                }
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"Ocurrio Un Error Al iniciar Session",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Toast.makeText(this,"Session Iniciada Con Exito",Toast.LENGTH_SHORT).show();
        JSONArray json = response.optJSONArray("usuarios");
        JSONObject jsonObject = null;
        try {
            for(int i=0;i<json.length();i++){

                jsonObject = json.getJSONObject(i);
            }
            Intent resultado = new Intent();
            resultado.putExtra("correo",jsonObject.getString("Correo").toString());
            resultado.putExtra("usuario",jsonObject.getString("Usuario").toString());
            setResult(MainActivity.CODIGO_SESSION,resultado);
           finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
