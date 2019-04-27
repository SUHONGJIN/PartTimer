package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {

    @BindView(R.id.btn_drawings)
    Button btn_drawings;
    @BindView(R.id.iv_close)
    ImageView iv_close;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.iv_close,R.id.btn_drawings})
    public void OnClickW(View view){
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_drawings:
                startActivity(new Intent(WalletActivity.this,AuthenticationActivity.class));
                ToastUtils.setOkToast(WalletActivity.this,"请先实名认证");
                break;
                default:break;
        }
    }
}
