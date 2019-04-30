package com.mfzj.parttimer.utils;

/**
 * Created by SuHongJin on 2018/11/5.
 */

import android.os.Environment;

/**
 * 基本配置信息
 */
public class AppConfig {
    /**
     * 网络请求地址
     */

    public static final String BMOB_APP_KEY="867ea944e58d62bbe33e3324f5f03abe";  //bmob的appkey


    public static final String PIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TakePhotoPic";
}
