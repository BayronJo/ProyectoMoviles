package com.appturismo.proyectomoviles.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appturismo.proyectomoviles.Modelo.DB;
import com.appturismo.proyectomoviles.Modelo.Usuario;
import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.utils.DatePickerFragment;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.Checked;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class Registro extends AppCompatActivity  implements View.OnClickListener,Response.Listener<JSONObject>,Response.ErrorListener{

    private DB db;
    private Usuario registro_temp=null;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private ProgressDialog progressDialog;
    @BindView(R.id.txtid) EditText txtid;
    @BindView(R.id.txtusuario)
    @NotEmpty(messageId = R.string.usuario)
    EditText txtusuario;
    @BindView(R.id.txtNombre)
    @NotEmpty(messageId = R.string.nombre)
    EditText txtnombre;
    @BindView(R.id.txtApellido)
    @NotEmpty(messageId = R.string.apellido)
    EditText txtapellido;
    @BindView(R.id.txtCorreo)
    @NotEmpty(messageId = R.string.correo)
    EditText txtCorreo;

    @BindView(R.id.fechanacimiento)
    @NotEmpty(messageId = R.string.fecha)
    EditText txtfecha;
    @BindView(R.id.txtcontra)
    @NotEmpty(messageId = R.string.contra)
    EditText txtontra;
    @BindView(R.id.txtverificar)
    @NotEmpty(messageId = R.string.verifi)
    EditText txtverificar;
    @BindView(R.id.btnAceptar)
    Button btnAceptar;
    @BindView(R.id.chkterminos)
    @Checked
    CheckBox terminos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar =  findViewById(R.id.toolbarregistro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        btnAceptar.setOnClickListener(this);
        txtfecha.setOnClickListener(this);
        db= new DB(this);
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAceptar:
                if(FormValidator.validate(this,new SimpleErrorPopupCallback(getApplicationContext()))){
                    guardar();
                }
                break;
            case R.id.fechanacimiento:
                MostrarDatePicker();
                break;
        }
    }

    private  void MostrarDatePicker(){
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String Fecha = dayOfMonth + "/" + (month+1) + "/" + year;
                txtfecha.setText(Fecha);
            }
        });
        datePickerFragment.show(getFragmentManager(),"Fecha");
    }
    private void guardar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando Datos");
        progressDialog.show();
        String usuario = txtusuario.getText().toString();
        String nombres = txtnombre.getText().toString();
        String apellidos = txtapellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String fechanac = txtfecha.getText().toString();
        String Credencial = txtontra.getText().toString();
        String url = "http://apiturismo.atspace.cc/Registro.php?Usuario="+usuario+"&Nombres="+nombres+"&Apellidos="+apellidos+"&Correo="+correo+"&FechaNac="+fechanac+"&Credencial="+Credencial+"";
        url.replace(" ","%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
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
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Toast.makeText(this,"Ocurrio Un Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        Toast.makeText(this,"Usuario Insertado Con Exito",Toast.LENGTH_SHORT).show();
        finish();
    }
}
