package com.mfzj.parttimer.view.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.longsh.optionframelibrary.OptionBottomDialog;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.ActivityCollector;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.HelpActivity;
import com.mfzj.parttimer.view.activity.MainActivity;
import com.mfzj.parttimer.view.inter.ISettingAView;
import com.mfzj.parttimer.widget.ItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

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


    @Override
    public int getContentViewResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tv_title.setText("设置");

        //用户登录成功就显示，否则就隐藏。
        if (BmobUser.isLogin()){
            tv_logout.setVisibility(View.VISIBLE);
            setting_itemview1.setVisibility(View.VISIBLE);
        }else {
            tv_logout.setVisibility(View.GONE);
            setting_itemview1.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.setting_itemview1,R.id.setting_itemview2,R.id.setting_itemview3,R.id.setting_itemview4,R.id.tv_logout,R.id.iv_back})
    public void OnClickItemView(View view){
        switch (view.getId()){
            case R.id.setting_itemview1:
                startActivity(new Intent(SettingActivity.this, ModifyPassWordActivity.class));
                break;
            case R.id.setting_itemview2:
                startActivity(new Intent(SettingActivity.this, HelpActivity.class));
                break;
            case R.id.setting_itemview3:
                final OptionMaterialDialog mMaterialDialog0 = new OptionMaterialDialog(SettingActivity.this);
                mMaterialDialog0.setTitle("温馨提示：")
                        .setMessage("确定清除缓存吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.setOkToast(SettingActivity.this,"清除缓存成功");
                                mMaterialDialog0.dismiss();
                            }
                        })
                        .setNegativeButton("取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog0.dismiss();
                                    }
                                })
                        .setCanceledOnTouchOutside(true)
                        .show();

                break;
            case R.id.setting_itemview4:
                //检查APP更新
                ToastUtils.setOkToast(SettingActivity.this,"已经是最新版本~");
                break;
            case R.id.tv_logout:

                List<String> stringList = new ArrayList<String>();
                stringList.add("退出登录");
                final OptionBottomDialog optionBottomDialog = new OptionBottomDialog(SettingActivity.this, stringList);
                optionBottomDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            BmobUser.logOut();  //退出登录，同时清除Bmob缓存用户对象。
                            ActivityCollector.removeAll();
                            startActivity(new Intent(SettingActivity.this, MainActivity.class));//跳转到主页
                        }
                        optionBottomDialog.dismiss();
                    }
                });
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
