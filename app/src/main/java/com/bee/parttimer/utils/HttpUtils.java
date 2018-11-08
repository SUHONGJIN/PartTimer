package com.bee.parttimer.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by SuHongJin on 2018/11/5.
 */

/**
 * 网络请求工具类
 */
public class HttpUtils {
    /**
     * 网络请求
     * @param address  url地址
     * @param callback 回调
     */
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
