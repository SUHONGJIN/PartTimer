package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        /**
         * 定时器，作用于延时页面跳转
         */
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                //如果不是第一次启动应用（true），则直接跳转到主页
                if (SharedPreferencesUtils.getWelcomeGuideBoolean(getBaseContext())){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();  //销毁此活动
                }else {
                    //如果是第一次启动APP，则跳转到欢迎指南页面，同时设置用户偏好为true
                    startActivity(new Intent(SplashActivity.this,WelcomeGuideActivity.class));
                    SharedPreferencesUtils.putWelcomeGuideBoolean(getBaseContext(),true);
                    finish();
                }

            }
        },3000);//表示延时3秒进行跳转
    }

    @Override
    public void initData() {}
}