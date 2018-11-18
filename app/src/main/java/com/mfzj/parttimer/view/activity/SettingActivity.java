package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.presenter.impl.SettingAPresenterImpl;
import com.mfzj.parttimer.presenter.inter.ISettingAPresenter;
import com.mfzj.parttimer.utils.ActivityCollector;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.inter.ISettingAView;
import com.mfzj.parttimer.widget.ItemView;

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
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private ISettingAPresenter mISettingAPresenter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mISettingAPresenter = new SettingAPresenterImpl(this);
        tv_title.setText("设置");
    }

    @OnClick({R.id.setting_itemview1,R.id.setting_itemview2,R.id.setting_itemview3,R.id.setting_itemview4,R.id.tv_logout,R.id.iv_back})
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
            case R.id.iv_back:
                finish();
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
