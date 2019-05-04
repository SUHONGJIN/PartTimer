package com.mfzj.parttimer.utils;

import android.app.Application;

import com.mob.MobSDK;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //bmob默认初始化
        Bmob.initialize(this, AppConfig.BMOB_APP_KEY);

        //极光推送配置
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        //mob初始化
        MobSDK.init(this);

    }
    public static MyApplication getInstance() {
        if (instance == null) {
            return new MyApplication();
        }
        return instance;
    }
}
