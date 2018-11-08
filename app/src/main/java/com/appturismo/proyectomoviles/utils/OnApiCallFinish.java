package com.appturismo.proyectomoviles.utils;

public interface OnApiCallFinish {
    void onSucess(Integer Id, String content);
    void onError(Integer Id, Integer code);
}

