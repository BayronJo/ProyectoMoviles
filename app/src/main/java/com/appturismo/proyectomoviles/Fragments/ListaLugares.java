package com.appturismo.proyectomoviles.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appturismo.proyectomoviles.Api.ApiClient;
import com.appturismo.proyectomoviles.Api.Response;
import com.appturismo.proyectomoviles.R;
import com.appturismo.proyectomoviles.items.Datos;
import com.appturismo.proyectomoviles.utils.AdaptadorLugares;
import com.appturismo.proyectomoviles.utils.ApiCaller;
import com.appturismo.proyectomoviles.utils.OnApiCallFinish;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaLugares extends Fragment implements OnApiCallFinish,AdaptadorLugares.OnItemClickListener{

    AdaptadorLugares adaptadorLugares;
    @BindView(R.id.myTodoList)
    RecyclerView myTodoList;
    private Boolean Delete=false;
    List<Datos> listadatos;
    private OnItemTodoListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_lista_lugares, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Datos> TodoList = new ArrayList<>();
        this.adaptadorLugares = new AdaptadorLugares(getContext(),TodoList, R.layout.item_lugares, this);
        this.myTodoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.myTodoList.setAdapter(this.adaptadorLugares);
        this.myTodoList.setItemAnimator(new DefaultItemAnimator());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tareas");
        FillList();
    }

    @Override
    public void onSucess(Integer Id, String content) {
        if(Delete){
            FillList();
            Delete=false;
        }else {
            Response res = new Gson().fromJson(content, Response.class);
            this.adaptadorLugares.setmDataSet(res.getResults());
        }
    }



    private void FillList() {
        ApiCaller caller = ApiClient.getTaskList(this);
        caller.setUrl("http://apiturismo.atspace.cc/ejemplo.php");
        caller.ProgressDialog(getActivity());
        caller.execute();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemTodoListener) {
            mListener = (OnItemTodoListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onError(Integer Id, Integer code) {

    }

    @Override
    public void onItemClick(Datos toDo, int position) {
        mListener.OnItemTodo(1, toDo.getID());
        //Toast.makeText(getContext(),""+toDo.getID()+"\n"+toDo.getTitulo(),Toast.LENGTH_SHORT).show();
    }

    public  interface OnItemTodoListener{
        void OnItemTodo(int operacion,Integer idTodo);
    }
}
