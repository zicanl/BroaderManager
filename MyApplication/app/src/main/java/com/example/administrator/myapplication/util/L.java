package com.example.administrator.myapplication.util;

//封装类

import android.util.Log;

public class L {

    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String Tag = "Smartbutler";


    //五个等级 DIWEF

    public static void d(String text){
        if(DEBUG){
            Log.d(Tag,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(Tag,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(Tag,text);
        }
    }
    public static void e(String text){
        if(DEBUG){
            Log.e(Tag,text);
        }
    }
}
