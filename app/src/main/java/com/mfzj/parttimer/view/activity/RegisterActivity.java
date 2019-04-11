package com.mfzj.parttimer.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    @BindView(R.id.btn_get_code)
    Button btn_get_code;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_register_username)
    EditText et_register_username;
    @BindView(R.id.et_register_password)
    EditText et_register_password;
    @BindView(R.id.et_register_code)
    EditText et_register_code;

    private Dialog mWeiboDialog;
//    private EventHandler eventHandler;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reister;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //mob验证码
        //verificationPhone();
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.iv_back, R.id.btn_register, R.id.btn_get_code})
    public void registerClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                //判断输入条件是否满足
                if (TextUtils.isEmpty(et_register_username.getText().toString())) {
                    ToastUtils.setOkToast(RegisterActivity.this, "手机号不能为空!");
                } else if (et_register_username.getText().length() != 11) {
                    ToastUtils.setOkToast(RegisterActivity.this, "手机号不规范!");
                } else if (TextUtils.isEmpty(et_register_password.getText().toString())) {
                    ToastUtils.setOkToast(RegisterActivity.this, "请输入密码!");
                } else if (et_register_password.getText().length() < 6) {
                    ToastUtils.setOkToast(RegisterActivity.this, "密码不能小于6位!");
                } else if (et_register_code.getText().length() == 0) {
                    ToastUtils.setOkToast(RegisterActivity.this, "请填写验证码");
                } else {
                    // 提交验证码，其中的code表示验证码，如“1357”
                    //SMSSDK.submitVerificationCode("86", et_register_username.getText().toString(), et_register_code.getText().toString());
                }
                break;
            case R.id.btn_get_code:
                if (TextUtils.isEmpty(et_register_username.getText().toString())) {
                    ToastUtils.setOkToast(RegisterActivity.this, "请填写手机号");
                }else if (et_register_username.getText().length() != 11) {
                    ToastUtils.setOkToast(RegisterActivity.this, "手机号不规范!");
                }else {
                    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                    //SMSSDK.getVerificationCode("86", et_register_username.getText().toString());
                }
                break;
            default:
                break;
        }
    }

//    private void verificationPhone() {
//
//        // 在尝试读取通信录时以弹窗提示用户（可选功能）
//        SMSSDK.setAskPermisionOnReadContact(false);
//
//        eventHandler = new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
//                Message msg = new Message();
//                msg.arg1 = event;
//                msg.arg2 = result;
//                msg.obj = data;
//                new Handler(Looper.getMainLooper(), new Handler.Callback() {
//                    @Override
//                    public boolean handleMessage(Message msg) {
//                        int event = msg.arg1;
//                        int result = msg.arg2;
//                        Object data = msg.obj;
//                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                            if (result == SMSSDK.RESULT_COMPLETE) {
//                                // TODO 处理成功得到验证码的结果
//                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
//                                ToastUtils.setOkToast(RegisterActivity.this, "验证码已发送");
//                                //倒计时60s
//                                new CountDownTimer(60000,1000) {
//                                    @Override
//                                    public void onTick(long time) {
//                                       // Log.i("TAG","倒计时"+time/1000+"==================");
//                                        btn_get_code.setText(time/1000+"秒后重新获取");
//                                        btn_get_code.setClickable(false);
//                                    }
//
//                                    @Override
//                                    public void onFinish() {
//                                        //Log.i("TAG","end---------------------------------------");
//                                        btn_get_code.setText("获取验证码");
//                                        btn_get_code.setClickable(true);
//                                    }
//                                }.start();
//                            } else {
//                                // TODO 处理错误的结果
//                                ((Throwable) data).printStackTrace();
//                                ToastUtils.setOkToast(RegisterActivity.this, "验证码发送失败");
//                            }
//                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                            if (result == SMSSDK.RESULT_COMPLETE) {
//                                // TODO 处理验证码验证通过的结果
//                                //ToastUtils.setOkToast(RegisterActivity.this, "验证成功");
//                                //验证成功后开始注册
//                                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(RegisterActivity.this, "注册中...");
//                                signUp();
//                            } else {
//                                // TODO 处理错误的结果
//                                ((Throwable) data).printStackTrace();
//                                ToastUtils.setOkToast(RegisterActivity.this, "验证错误");
//                            }
//                        }
//                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
//                        return false;
//                    }
//                }).sendMessage(msg);
//            }
//        };
//        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
//       // SMSSDK.registerEventHandler(eventHandler);
//    }

    /**
     * 账号密码注册
     */
    private void signUp() {
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
                    ToastUtils.setOkToast(RegisterActivity.this, "注册成功!");
                    //保存用户名到共享偏好储存
                    SharedPreferencesUtils.saveStringSharedPreferences(RegisterActivity.this, "username", et_register_username.getText().toString());
                    //清除Bmob缓存用户对象。
                    BmobUser.logOut();
                    //返回数据给上一个界面
                    Intent intentTemp = new Intent();
                    intentTemp.putExtra("username", et_register_username.getText().toString());
                    setResult(200, intentTemp);
                    //返回
                    finish();
                } else {
                    //关闭加载框
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    ToastUtils.setOkToast(RegisterActivity.this, "账号或已存在!");
                    //Log.i("tag1", e.getMessage() + "===============");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 使用完EventHandler需注销，否则可能出现内存泄漏
        //SMSSDK.unregisterEventHandler(eventHandler);
    }
}
