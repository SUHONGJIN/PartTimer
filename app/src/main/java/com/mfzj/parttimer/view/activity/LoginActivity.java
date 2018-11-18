package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_to_register)
    TextView tv_to_register;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_to_register,R.id.iv_back,R.id.btn_login})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_to_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
                default:
                    break;
        }

    }
}
