package com.mfzj.parttimer.view.activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyPassWordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_old_pwd)
    EditText et_old_pwd;
    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;
    @BindView(R.id.btn_modify_pwd)
    Button btn_modify_pwd;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_modify_pass_word;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("重置密码");
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_modify_pwd,R.id.iv_back})
    public void onClickCommit(View v) {

        switch (v.getId()) {

            case R.id.btn_modify_pwd:
                String pwd_lod = et_old_pwd.getText().toString().trim();
                String pwd_new = et_new_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd_lod)){
                    ToastUtils.setOkToast(ModifyPassWordActivity.this,"输入旧密码！");
                }else if (TextUtils.isEmpty(pwd_new)){
                    ToastUtils.setOkToast(ModifyPassWordActivity.this,"输入新密码！");
                }else if (pwd_lod.length()<6){
                    ToastUtils.setOkToast(ModifyPassWordActivity.this,"旧密码不能小于6位！");
                }else if (pwd_new.length()<6){
                    ToastUtils.setOkToast(ModifyPassWordActivity.this,"新密码不能小于6位！");
                }else{
                    BmobUser.updateCurrentUserPassword(et_old_pwd.getText().toString().trim(), et_new_pwd.getText().toString().trim(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                ToastUtils.setOkToast(ModifyPassWordActivity.this,"修改密码成功,请重新登录！");
                                Intent intent=new Intent(ModifyPassWordActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });

                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
