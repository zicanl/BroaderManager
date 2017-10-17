package com.example.administrator.myapplication.util;


//sharedpreference 封装

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {

    public static final String Name = "config";


    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getString(Context mContext, String key, String defvalue)
    {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        return sp.getString(key,defvalue);
    }


    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(Context mContext, String key, int defvalue)
    {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        return sp.getInt(key,defvalue);
    }

    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static boolean getBoolean(Context mContext, String key, boolean defvalue)
    {
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defvalue);
    }

    //删除 单个
    public static void deleShare(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
    //删除 全部
    public static void deleAll(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


}
