package com.bee.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseActivity;
import com.bee.parttimer.presenter.impl.SettingAPresenterImpl;
import com.bee.parttimer.presenter.inter.ISettingAPresenter;
import com.bee.parttimer.utils.ActivityCollector;
import com.bee.parttimer.utils.ToastUtils;
import com.bee.parttimer.view.inter.ISettingAView;
import com.bee.parttimer.widget.ItemView;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements ISettingAView {

    @BindView(R.id.setting_itemview1)
    ItemView setting_itemview1;
    @BindView(R.id.setting_itemview2)
    ItemView setting_itemview2;
    @BindView(R.id.setting_itemview3)
    ItemView setting_itemview3;
    @BindView(R.id.setting_itemview4)
    ItemView setting_itemview4;
    @BindView(R.id.tv_logout)
    TextView tv_logout;

    private ISettingAPresenter mISettingAPresenter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mISettingAPresenter = new SettingAPresenterImpl(this);
    }

    @OnClick({R.id.setting_itemview1,R.id.setting_itemview2,R.id.setting_itemview3,R.id.setting_itemview4,R.id.tv_logout})
    public void OnClickItemView(View view){
        switch (view.getId()){
            case R.id.setting_itemview1:
                ToastUtils.setOkToast(SettingActivity.this,"s点击有效1");
                break;
            case R.id.setting_itemview2:
                ToastUtils.setOkToast(SettingActivity.this,"s点击有效2");
                break;
            case R.id.setting_itemview3:
                ToastUtils.setOkToast(SettingActivity.this,"s点击有效3");
                break;
            case R.id.setting_itemview4:
                ToastUtils.setOkToast(SettingActivity.this,"s点击有效4");
                break;
            case R.id.tv_logout:
                ToastUtils.setOkToast(SettingActivity.this,"logout!");
                ActivityCollector.removeAll();
                break;

            default:break;
        }
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
