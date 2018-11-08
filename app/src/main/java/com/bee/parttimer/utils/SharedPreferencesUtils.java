package com.bee.parttimer.utils;

/**
 * Created by SuHongJin on 2018/11/6.
 */

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * 共享偏好工具类
 */
public class SharedPreferencesUtils {
    private static final String FILE_NAME="parttime";
    private static final String VALUE_NAME="guide";
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

}
