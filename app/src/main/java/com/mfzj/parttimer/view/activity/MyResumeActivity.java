package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.User;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MyResumeActivity extends BaseActivity {

    @BindView(R.id.iv_head_bg)
    ImageView iv_head_bg;
    @BindView(R.id.iv_show_men)
    ImageView iv_show_men;
    @BindView(R.id.iv_show_women)
    ImageView iv_show_women;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_to_edit_resume)
    TextView tv_to_edit_resume;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_birth)
    TextView tv_birth;
    @BindView(R.id.tv_city)
    TextView tv_city;
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showUserInfo();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
    }

    @OnClick({R.id.tv_to_edit_resume, R.id.iv_close, R.id.civ_head})
    public void OnClickResume(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_to_edit_resume:
                Intent intent = new Intent(MyResumeActivity.this, EditResumeActivity.class);
                startActivityForResult(intent, REQUEST_INFO);
                break;
            case R.id.civ_head:
                startActivity(new Intent(MyResumeActivity.this, MyDataActivity.class));
                break;
            default:
                break;
        }
    }

    private void showUserInfo() {

        User user = BmobUser.getCurrentUser(User.class);



        Glide.with(MyResumeActivity.this)
                .load(user.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.banner_default)
                .error(R.drawable.banner_default)
                .apply(bitmapTransform(new BlurTransformation(22,2)))
                .into(iv_head_bg);

        Glide.with(MyResumeActivity.this)
                .load(user.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.head)
                .error(R.drawable.head)
                .into(civ_head);

        if (user.getName() != null) {
            tv_name.setText(user.getName());
            tv_show_name.setText(user.getName());

            if (user.getGender() != null) {
                String sex = user.getGender();
                if (sex.equals("男")) {
                    iv_show_men.setVisibility(View.VISIBLE);
                    iv_show_women.setVisibility(View.GONE);
                } else if (sex.equals("女")) {
                    iv_show_men.setVisibility(View.GONE);
                    iv_show_women.setVisibility(View.VISIBLE);
                }
            }
        }

        if (user.getBirth() != null) {
            tv_birth.setText(user.getBirth());
        }
        if (user.getCity() != null) {
            tv_city.setText(user.getCity());
        }
        if (user.getIdentity() != null) {
            tv_identity.setText(user.getIdentity());
        }
        if (user.getPhone() != null) {
            tv_phone.setText(user.getPhone());
        }
        if (user.getMyemail() != null) {
            tv_email.setText(user.getMyemail());
        }
        if (user.getIntro() != null) {
            tv_intro.setText(user.getIntro());
        }
        if (user.getExperience() != null) {
            tv_experience.setText(user.getExperience());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_INFO) {
            switch (resultCode) {
                case 200:
                    //刷新用户信息
                    showUserInfo();
                    break;
                default:
                    break;
            }
        }
    }
}
