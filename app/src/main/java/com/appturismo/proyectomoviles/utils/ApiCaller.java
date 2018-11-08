package com.appturismo.proyectomoviles.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import com.appturismo.proyectomoviles.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ApiCaller extends AsyncTask<Void, Integer, String> {
    private static final String TAG="ApiCaller";
    private boolean isPost;
    private String url="";
    private int statusCode=0;
    private int requestId=0;
    private boolean isDelete;
    ProgressDialog ProgressToDo;
    private HashMap<String, String> valor;
    private OnApiCallFinish listener;
    private int Solicitud=0;
    @Override
    protected String doInBackground(Void... voids) {
        while(Solicitud<100){
            Solicitud++;
            publishProgress(Solicitud);
            SystemClock.sleep(15);
        }
        if(Solicitud==100) {
            OkHttpClient cliente = new OkHttpClient();
            Request request;

            if (!isPost) {
                request = getMethod();
            } else {
                request = postMethod();
            }
            if (isDelete) {
                request = deleteMethod();
            }
            try {
                Response response = cliente.newCall(request).execute();

                this.statusCode = response.code();

                if (this.statusCode >= 200 && this.statusCode <= 300) {
                    if (response.body() != null) {
                        if(ProgressToDo!=null) {
                            ProgressToDo.dismiss();
                        }
                        return response.body().string();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return "";
        }
        return "";
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        if(this.listener==null){return;}
        if(this.statusCode>=200 && this.statusCode<=300){
            this.listener.onSucess(this.requestId, content);
        }else{
            this.listener.onError(this.requestId,this.statusCode);
        }
    }

    private Request getMethod(){
        Request.Builder requestBuilder= new Request.Builder();
        Request request;
        if(!this.url.equals("")){
            requestBuilder.url(this.url);
        }
        request=requestBuilder.build();
        return request;
    }

    private Request deleteMethod(){
        Request request;
        Request.Builder requestBuilder= new Request.Builder();
        if(!this.url.equals("")){
            requestBuilder.url(this.url);
        }
        if(this.valor.isEmpty()){
            requestBuilder.delete();
        }else{
            FormBody.Builder form= new FormBody.Builder();
            for(Map.Entry<String, String> pair: valor.entrySet()){
                form.add(pair.getKey(), pair.getValue());
            }

            FormBody formBody = form.build();
            requestBuilder.delete(formBody);
        }
        request=requestBuilder.build();
        return request;
    }

    private Request postMethod(){

        Request request=null;
        if(!this.url.equals("")){
            if(!this.valor.isEmpty()){
                //Se crea un contenedor de los valores que se reciben
                FormBody.Builder form=new FormBody.Builder();
                //Iterando el hashmap en base al entrySet()
                for(Map.Entry<String, String> pair: valor.entrySet()){
                    form.add(pair.getKey(), pair.getValue());
                }

                //Es necesario crear un RequestBody para poder asignarselo al metodo post(); del request.Builder
                RequestBody body=form.build();
                request=new Request.Builder().url(this.url).post(body).build();
            }
        }

        return request;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(ProgressToDo!=null) {
            ProgressToDo.setProgress(values[0]);
        }
    }

    public void setPost(boolean post) {
        isPost = post;
    }

    public void setValor(HashMap<String, String> valor) {
        this.valor = valor;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public void setOnApiCallFinish(OnApiCallFinish listener) {
        this.listener = listener;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void ProgressDialog(Context context){
        ProgressToDo=new ProgressDialog(context);
        ProgressToDo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ProgressToDo.setMessage(context.getString(R.string.Load));
        ProgressToDo.show();
    }
}
