package com.example.administrator.myapplication.util;


import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

//工具统一类
public class UtilTools {

    public static void setFont(Context mContext, TextView textview){
        //设置字体
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.ttf");
        textview.setTypeface(fontType);
    }
}
