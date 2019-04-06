package com.mfzj.parttimer.utils;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //bmob默认初始化
        Bmob.initialize(this, "867ea944e58d62bbe33e3324f5f03abe");
        //极光推送配置
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}
