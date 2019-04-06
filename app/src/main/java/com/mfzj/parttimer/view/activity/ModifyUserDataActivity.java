package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyUserDataActivity extends BaseActivity{

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_nick)
    EditText et_nick;
    @BindView(R.id.et_motto)
    EditText et_motto;
    @BindView(R.id.btn_commit_info)
    Button btn_commit_info;

    private String tag;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_modify_user_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tag = getIntent().getExtras().getString("tag");

        User user = BmobUser.getCurrentUser(User.class);
        if (tag.equals("nick")) {
            tv_title.setText("修改昵称");
            et_nick.setVisibility(View.VISIBLE);
            //获取用户昵称
            if (user.getNick() != null) {
                et_nick.setText(user.getNick().toString());
                //显示光标位置在最后
                et_nick.setSelection(user.getNick().length());
            }

        } else if (tag.equals("motto")) {
            tv_title.setText("修改座右铭");
            et_motto.setVisibility(View.VISIBLE);
            //获取个性签名
            if (user.getMotto() != null) {
                et_motto.setText(user.getMotto());
                //显示光标位置在最后
                et_motto.setSelection(user.getMotto().length());
            }
        }
    }

    @Override
    public void initData() {

    }
    private void modifyMotto() {
        BmobUser myUser= BmobUser.getCurrentUser(User.class);
        myUser.setValue("motto",et_motto.getText().toString());
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    ToastUtils.setOkToast(ModifyUserDataActivity.this,"座右铭已修改！");
                    setResult(200);
                    finish();
                }else {
                    ToastUtils.setOkToast(ModifyUserDataActivity.this,"修改座右铭失败！");
                }
            }
        });
    }

    private void modifyNick() {
        BmobUser myUser= BmobUser.getCurrentUser(User.class);
        myUser.setValue("nick",et_nick.getText().toString());
        myUser.update(myUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    ToastUtils.setOkToast(ModifyUserDataActivity.this,"昵称已修改！");
                    setResult(200);
                    finish();
                }else {
                    ToastUtils.setOkToast(ModifyUserDataActivity.this,"修改昵称失败！");
                }
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.btn_commit_info})
    public void onClickModify(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_commit_info:
                //修改昵称
                if (tag.equals("nick")){ modifyNick(); }
                //修改昵称
                if (tag.equals("motto")){ modifyMotto(); }
                break;
        }

    }

}
