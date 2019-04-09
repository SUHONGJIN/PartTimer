package com.mfzj.parttimer.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ActivityCollector;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.widget.WeiboDialogUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_to_register)
    TextView tv_to_register;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_login_username)
    public EditText et_login_username;
    @BindView(R.id.et_login_password)
    public EditText et_login_password;

    private Dialog mWeiboDialog;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //获取已保存的定位信息
        String username= SharedPreferencesUtils.getStringSharedPreferences(LoginActivity.this,"username","");
        et_login_username.setText(username);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_to_register,R.id.iv_back,R.id.btn_login})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_to_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                //判断输入条件是否满足
                if (TextUtils.isEmpty(et_login_username.getText().toString())){
                    ToastUtils.setOkToast(LoginActivity.this,"手机号不能为空!");
                }else if (et_login_username.getText().length()!=11){
                    ToastUtils.setOkToast(LoginActivity.this,"手机号不规范!");
                }else if (TextUtils.isEmpty(et_login_password.getText().toString())){
                    ToastUtils.setOkToast(LoginActivity.this,"请输入密码!");
                }else if(et_login_password.getText().length()<6){
                    ToastUtils.setOkToast(LoginActivity.this,"密码不能小于6位!");
                }else {
                    //加载框
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "登录中...");
                    login(view);
                }

                break;
                default:
                    break;
        }

    }
    /**
     * 账号密码登录
     */
    private void login(final View view) {
        final User user = new User();
        //此处替换为你的用户名
        user.setUsername(et_login_username.getText().toString());
        //此处替换为你的密码
        user.setPassword(et_login_password.getText().toString());
        user.login(new SaveListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {
                if (e == null) {
                    ToastUtils.setOkToast(LoginActivity.this,"登录成功！");
                    ActivityCollector.removeAll();
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                } else {
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(LoginActivity.this,"账号或密码错误！");
                    //Snackbar.make(view, "登录失败! Log：" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1){
            switch (resultCode){
                case 200:
                    et_login_username.setText(data.getStringExtra("username"));
                    break;
                default:break;
            }
        }

    }
}
