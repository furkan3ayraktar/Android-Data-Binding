package com.furkanbayraktar.databinding.logging;

import android.util.Log;
import com.furkanbayraktar.databinding.ViewBinder;

/**
 * Created by Furkan Bayraktar.
 * Created at 8/15/14.
 * Copyright Valensas A.S.
 */
public class Logger {

    public static void info(String message){
        if(ViewBinder.getInstance().isLoggingEnabled()){
            Log.i(ViewBinder.TAG, message);
        }
    }

    public static void error(String message, Throwable e){
        Log.e(ViewBinder.TAG, message, e);
    }

    public static void warn(String message, Throwable e){
        if(ViewBinder.getInstance().isLoggingEnabled()) {
            Log.w(ViewBinder.TAG, message, e);
        }
    }
}
