package com.example.jarfi.sispak.util;

import android.app.Application;
import android.content.Context;

public class Jarfi extends Application {
    public static Context context = null;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
    }
}
