package com.bee.parttimer.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseActivity;
import com.bee.parttimer.presenter.impl.SettingAPresenterImpl;
import com.bee.parttimer.presenter.inter.ISettingAPresenter;
import com.bee.parttimer.view.inter.ISettingAView;

public class SettingActivity extends BaseActivity implements ISettingAView {

    private ISettingAPresenter mISettingAPresenter;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mISettingAPresenter = new SettingAPresenterImpl(this);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }


    @Override
    public void initData() {

    }
}
