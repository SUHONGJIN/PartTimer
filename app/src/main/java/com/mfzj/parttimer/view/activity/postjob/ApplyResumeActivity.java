package com.mfzj.parttimer.view.activity.postjob;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.ApplyTable;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class ApplyResumeActivity extends BaseActivity {

    @BindView(R.id.mImage)
    ImageView mImage;
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
    @BindView(R.id.ll_show_apply_btn)
    LinearLayout ll_show_apply_btn;
    @BindView(R.id.btn_yes)
    Button btn_yes;
    @BindView(R.id.btn_no)
    Button btn_no;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_resume;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_to_edit_resume.setVisibility(View.GONE);
        ll_show_apply_btn.setVisibility(View.VISIBLE);
        showUserInfo();
    }

    @Override
    public void initData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                showUserInfo();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
    }

    @OnClick({R.id.iv_close,R.id.btn_yes,R.id.btn_no})
    public void OnClickResume(View view) {
        switch (view.getId()){
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_yes:
                break;
            case R.id.btn_no:
                reject();
                break;
                default:break;
        }
    }

    /**
     * 拒绝招聘此用户
     */
    private void reject() {

    }

    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        String name = getIntent().getStringExtra("name");
        String birth = getIntent().getStringExtra("birth");
        String city = getIntent().getStringExtra("city");
        String phone = getIntent().getStringExtra("phone");
        String email = getIntent().getStringExtra("email");
        String sex = getIntent().getStringExtra("sex");
        String avatar = getIntent().getStringExtra("avatar");
        String identity = getIntent().getStringExtra("identity");
        String intro = getIntent().getStringExtra("intro");
        String experience = getIntent().getStringExtra("experience");

        Glide.with(ApplyResumeActivity.this)
                .load(avatar)
                .bitmapTransform(new BlurTransformation(ApplyResumeActivity.this, 2, 5))
                .error(R.color.yellow)
                .into(mImage);
        Glide.with(ApplyResumeActivity.this)
                .load(avatar)
                .error(R.drawable.head)
                .into(civ_head);

        if (name != null) {
            tv_show_name.setText(name);
            tv_name.setText(name);
        }
        if (birth != null) {
            tv_birth.setText(birth);
        }
        if (city != null) {
            tv_city.setText(city);
        }
        if (phone != null) {
            tv_phone.setText(phone);
        }
        if (email != null) {
            tv_email.setText(email);
        }
        if (identity != null) {
            tv_identity.setText(identity);
        }
        if (experience != null) {
            tv_experience.setText(experience);
        }
        if (intro != null) {
            tv_intro.setText(intro);
        }
        if (sex != null) {
            if (sex.equals("男")) {
                iv_show_men.setVisibility(View.VISIBLE);
                iv_show_women.setVisibility(View.GONE);
            } else if (sex.equals("女")) {
                iv_show_men.setVisibility(View.GONE);
                iv_show_women.setVisibility(View.VISIBLE);
            }

        }

    }

}
