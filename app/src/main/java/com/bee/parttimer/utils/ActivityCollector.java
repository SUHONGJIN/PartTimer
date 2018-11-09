package com.bee.parttimer.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuHongJin on 2018/11/9.
 */

/**
 * Activity的管理类
 */
public class ActivityCollector {

    /**
     * 创建一个List集合存放Activity
     */
    public static List<Activity> activities=new ArrayList<>();

    /**
     * 添加Activity
     * @param activity
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAll(){
        for(Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
        activities.clear();
    }

}
