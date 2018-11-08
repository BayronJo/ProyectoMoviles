package com.appturismo.proyectomoviles.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.Date;

public class CustomDateFormat {
    public static String getUserDate(Context context, Date date) {
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(context);
        return dateFormat.format(date);
    }
}
