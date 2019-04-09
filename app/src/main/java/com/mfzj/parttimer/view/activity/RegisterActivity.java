package com.mfzj.parttimer.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.contract.RegisterContract;
import com.mfzj.parttimer.utils.ActivityCollector;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.widget.WeiboDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_register_username)
    public EditText et_register_username;
    @BindView(R.id.et_register_password)
    public EditText et_register_password;

    private Dialog mWeiboDialog;

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

                //判断输入条件是否满足
                if (TextUtils.isEmpty(et_register_username.getText().toString())){
                    ToastUtils.setOkToast(RegisterActivity.this,"手机号不能为空!");
                }else if (et_register_username.getText().length()!=11){
                    ToastUtils.setOkToast(RegisterActivity.this,"手机号不规范!");
                }else if (TextUtils.isEmpty(et_register_password.getText().toString())){
                    ToastUtils.setOkToast(RegisterActivity.this,"请输入密码!");
                }else if(et_register_password.getText().length()<6){
                    ToastUtils.setOkToast(RegisterActivity.this,"密码不能小于6位!");
                }else {
                    //加载框
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(RegisterActivity.this, "注册中...");
                    signUp(view);
                }
                break;

                default:
                    break;
        }
    }

    /**
     * 账号密码注册
     */
    private void signUp(final View view) {
        final User user = new User();
        user.setUsername(et_register_username.getText().toString());
        user.setPassword(et_register_password.getText().toString());
        user.setIsverify("未实名认证");
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);

                    ToastUtils.setOkToast(RegisterActivity.this,"注册成功!");
                    //保存用户名到共享偏好储存
                    SharedPreferencesUtils.saveStringSharedPreferences(RegisterActivity.this,"username",et_register_username.getText().toString());
                    //清除Bmob缓存用户对象。
                    BmobUser.logOut();
                    //返回数据给上一个界面
                    Intent intentTemp = new Intent();
                    intentTemp.putExtra("username",et_register_username.getText().toString());
                    setResult(200,intentTemp);
                    //返回
                    finish();
                } else {
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(RegisterActivity.this,"账号或已存在!");
                    //Snackbar.make(view, "注册失败! Log：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Log.i("tag1",e.getMessage()+"===============");
                }
            }
        });
    }

}
