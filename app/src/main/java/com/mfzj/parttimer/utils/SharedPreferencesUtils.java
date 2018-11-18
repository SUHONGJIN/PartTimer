package com.mfzj.parttimer.utils;

/**
 * Created by SuHongJin on 2018/11/6.
 */

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * 共享偏好工具类
 */
public class SharedPreferencesUtils {
    private static final String FILE_NAME="parttimer";
    private static final String VALUE_NAME="guide";
    private static final String VALUE_LOCATION="location";
    //获取
    public static boolean getWelcomeGuideBoolean(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getBoolean(VALUE_NAME,false);
    }
    //写入
    public static void putWelcomeGuideBoolean(Context context,Boolean isFirst){
        Editor editor=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
        editor.putBoolean(VALUE_NAME,isFirst);
        editor.commit();
    }
    //保存字符串
    public static void saveStringSharedPreferences(Context context,String location){
        Editor editor = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit();
        editor.putString(VALUE_LOCATION,location);
        editor.commit();
    }
    //获取字符串
    public  static String getStringSharedPreferences(Context context){
        return context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).getString(VALUE_LOCATION,"定位失败");
    }

}
