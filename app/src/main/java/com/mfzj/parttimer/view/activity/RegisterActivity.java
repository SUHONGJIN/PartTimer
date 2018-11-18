package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reister;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.iv_back,R.id.btn_register})
    public void registerClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                break;

                default:
                    break;
        }
    }

}
