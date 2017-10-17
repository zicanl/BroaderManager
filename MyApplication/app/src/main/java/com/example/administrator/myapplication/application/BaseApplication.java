package com.example.administrator.myapplication.application;

import android.app.Application;
import cn.bmob.v3.Bmob;
import com.example.administrator.myapplication.util.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

public class BaseApplication extends Application {

    //创建
    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_ID);
    }
}
