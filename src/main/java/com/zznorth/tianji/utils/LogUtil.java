package com.zznorth.tianji.utils;

import android.util.Log;


public class LogUtil {
    private static  final boolean LOG = true;

    public static void i(String TAG, String string) {
        if (LOG) {
            Log.i(TAG, string);
        }
    }

    public static void i(String TAG,int i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

    public static void i(String TAG,double i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

    public static void i(String TAG,boolean i){
        if(LOG){
            Log.i(TAG,i+"");
        }
    }

}
