package com.appturismo.proyectomoviles.Api;



import com.appturismo.proyectomoviles.utils.ApiCaller;
import com.appturismo.proyectomoviles.utils.OnApiCallFinish;

import java.util.HashMap;

public class ApiClient {
    public static final int TASK_LIST = 100;
    private static final String URL = "";

    public static ApiCaller getTaskList(OnApiCallFinish listener){
        ApiCaller caller = new ApiCaller();
        caller.setUrl(URL);
        //caller.setPost(false);
        caller.setRequestId(TASK_LIST);
        caller.setOnApiCallFinish(listener);
        return caller;
    }

    public static ApiCaller getTaskItem(OnApiCallFinish listener, String id){
        ApiCaller caller = new ApiCaller();
        caller.setUrl(URL);
        //caller.setUrl(URL.replace("{Id}", id ));
        caller.setRequestId(TASK_LIST);
        caller.setOnApiCallFinish(listener);
        return caller;
    }

    public static ApiCaller setTaskItemEdit(OnApiCallFinish listener, String id, HashMap<String, String> valor){
        ApiCaller caller = new ApiCaller();
        caller.setUrl(URL.replace("{id}", id ));
        caller.setRequestId(TASK_LIST);
        caller.setOnApiCallFinish(listener);
        caller.setPost(true);
        caller.setValor(valor);
        return caller;
    }

    public static ApiCaller setNewTaskItem(OnApiCallFinish listener, HashMap<String, String> valor){
        ApiCaller caller = new ApiCaller();
        caller.setUrl(URL.replace("{id}", ""));
        caller.setRequestId(TASK_LIST);
        caller.setPost(true);
        caller.setValor(valor);
        caller.setOnApiCallFinish(listener);
        return caller;
    }

    public static ApiCaller deleteTaskItem(OnApiCallFinish listener, String id, HashMap<String, String> valor){
        ApiCaller caller = new ApiCaller();
        caller.setUrl(URL.replace("{id}", id));
        caller.setValor(valor);
        caller.setRequestId(TASK_LIST);
        caller.setDelete(true);
        caller.setOnApiCallFinish(listener);
        return caller;
    }
}

