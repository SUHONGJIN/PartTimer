package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.mfzj.parttimer.bean.User;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MyResumeActivity extends BaseActivity {

    @BindView(R.id.mImage)
    ImageView mImage;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_to_edit_resume)
    TextView tv_to_edit_resume;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_gender)
    TextView tv_gender;
    @BindView(R.id.tv_birth)
    TextView tv_birth;
    @BindView(R.id.tv_identity)
    TextView tv_identity;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_intro)
    TextView tv_intro;
    @BindView(R.id.tv_experience)
    TextView tv_experience;
    @BindView(R.id.tv_show_name)
    TextView tv_show_name;

    public static final int REQUEST_INFO = 1;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showUserInfo();
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_to_edit_resume,R.id.iv_close})
    public void OnClickResume(View view){
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_to_edit_resume:
                Intent intent=new Intent(MyResumeActivity.this,EditResumeActivity.class);
                startActivityForResult(intent,REQUEST_INFO);
                break;
                default:break;
        }
    }

    private void showUserInfo(){

        User user = BmobUser.getCurrentUser(User.class);

        Glide.with(MyResumeActivity.this)
                .load(user.getAvatar())
                .bitmapTransform(new BlurTransformation(MyResumeActivity.this,2,5))
                .error(R.color.yellow)
                .into(mImage);
        Glide.with(MyResumeActivity.this)
                .load(user.getAvatar())
                .error(R.drawable.head)
                .into(civ_head);
        if (user.getName()!=null){
            tv_name.setText(user.getName());
            tv_show_name.setText(user.getName());
            tv_show_name.setVisibility(View.VISIBLE);
        }
        if (user.getGender()==0){
            tv_gender.setText("男生");
        }else if(user.getGender()==1){
            tv_gender.setText("女生");
        }else {
            tv_gender.setText("保密");
        }

        if (user.getBirth()!=null){
            tv_birth.setText(user.getBirth());
        }
        if (user.getIdentity()!=null){
            tv_identity.setText(user.getIdentity());
        }
        if (user.getPhone()!=null){
            tv_phone.setText(user.getPhone());
        }
        if (user.getEmail()!=null){
            tv_email.setText(user.getEmail());
        }
        if (user.getIntro()!=null){
            tv_intro.setText(user.getIntro());
        }
        if (user.getExperience()!=null){
            tv_experience.setText(user.getExperience());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INFO){
            switch (resultCode){
                case 200:
                    //刷新用户信息
                    showUserInfo();
                    break;
                    default:break;
            }
        }
    }
}
