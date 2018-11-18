package com.mfzj.parttimer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.utils.ActivityCollector;
import com.mfzj.parttimer.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());

        //绑定初始化ButterKnife、
        ButterKnife.bind(this);

        //设置状态栏
        setStatuBar();

        //初始化控件
        initView(savedInstanceState);

        //初始化数据
        initData();

        //添加Activity
        ActivityCollector.addActivity(this);
    }

    /**
     * 获取xml布局
     * @return
     */
    public abstract int getContentViewResId();

    /**
     * 初始化控件
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置状态栏
     */
    public void setStatuBar(){
        StatusBarUtil.transparencyBar(this); //设置状态栏全透明
        StatusBarUtil.StatusBarLightMode(this); //设置白底黑字
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除Activity
        ActivityCollector.removeActivity(this);
    }
}
