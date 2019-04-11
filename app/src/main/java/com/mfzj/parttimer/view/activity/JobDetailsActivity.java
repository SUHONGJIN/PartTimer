package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.ApplyTable;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class JobDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_job_details_title)
    TextView tv_job_details_title;
    @BindView(R.id.tv_job_details_time)
    TextView tv_job_details_time;
    @BindView(R.id.tv_job_details_pay)
    TextView tv_job_details_pay;
    @BindView(R.id.tv_job_details_company)
    TextView tv_job_details_company;
    @BindView(R.id.tv_job_details_address)
    TextView tv_job_details_address;
    @BindView(R.id.tv_job_details_describe)
    TextView tv_job_details_describe;
    @BindView(R.id.tv_job_details_people)
    TextView tv_job_details_people;
    @BindView(R.id.tv_job_details_type)
    TextView tv_job_details_type;
    @BindView(R.id.btn_apply)
    Button btn_apply;
    @BindView(R.id.tv_report)
    TextView tv_report;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.iv_call)
    ImageView iv_call;
    @BindView(R.id.iv_share)
    ImageView iv_share;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_job_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        String job_title = (String) getIntent().getExtras().get("job_title");
        String job_pay = (String) getIntent().getExtras().get("job_pay");
        String job_time = (String) getIntent().getExtras().get("job_time");
        String job_type = (String) getIntent().getExtras().get("job_type");
        String job_company = (String) getIntent().getExtras().get("job_company");
        String job_address = (String) getIntent().getExtras().get("job_address");
        String job_describe = (String) getIntent().getExtras().get("job_describe");
        String job_people = (String) getIntent().getExtras().get("job_people");

        if (job_title != null) {
            tv_job_details_title.setText(job_title);
        }
        if (job_pay != null) {
            tv_job_details_pay.setText(job_pay);
        }
        if (job_time != null) {
            tv_job_details_time.setText(job_time);
        }
        if (job_type != null) {
            tv_job_details_type.setText(job_type);
        }
        if (job_company != null) {
            tv_job_details_company.setText(job_company);
        }
        if (job_address != null) {
            tv_job_details_address.setText(job_address);
        }
        if (job_describe != null) {
            tv_job_details_describe.setText(job_describe);
        }
        if (job_people != null) {
            tv_job_details_people.setText(job_people);
        }

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_back, R.id.iv_collect, R.id.iv_call, R.id.iv_share, R.id.tv_report, R.id.btn_apply})
    public void OnclickDedatils(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_collect:
                setCollect();
                break;
            case R.id.iv_call:

                if (BmobUser.isLogin()) {
                    ToastUtils.setOkToast(JobDetailsActivity.this, "咨询");
                } else {
                    startActivity(new Intent(JobDetailsActivity.this, LoginActivity.class));
                }
                break;
            case R.id.iv_share:
                ToastUtils.setOkToast(JobDetailsActivity.this, "已分享");
                break;
            case R.id.tv_report:
                ToastUtils.setOkToast(JobDetailsActivity.this, "举报成功！我们会认真处理");
                break;
            case R.id.btn_apply:
                if (BmobUser.isLogin()) {
                    User user = BmobUser.getCurrentUser(User.class);
                    JobSelection apply = new JobSelection();
                    String id = (String) getIntent().getExtras().get("ObjectId");
                    apply.setObjectId(id);

                    ApplyTable applyTable = new ApplyTable();

                    applyTable.setUser(user);
                    applyTable.setJob(apply);
                    applyTable.setApply("已报名");
                    applyTable.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                Log.i("bmob", "报名成功");
                                btn_apply.setClickable(false);
                                btn_apply.setText("已报名");
                                ToastUtils.setOkToast(JobDetailsActivity.this, "报名成功");
                            } else {
                                //Log.i("bmob","失败："+e.getMessage());
                            }
                        }

                    });

                } else {
                    startActivity(new Intent(JobDetailsActivity.this, LoginActivity.class));
                }
                break;
            default:
                break;
        }
    }

    private void setCollect() {
        String id = (String) getIntent().getExtras().get("ObjectId");
        User user = User.getCurrentUser(User.class);
        JobSelection job = new JobSelection();
        job.setObjectId(id);
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
//将当前用户添加到多对多关联中
        relation.add(user);
//多对多关联指向`post`的`likes`字段
        job.setCollect(relation);
        job.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "多对多关联添加成功");
                    iv_collect.setImageResource(R.mipmap.icon_collect_s);
                    ToastUtils.setOkToast(JobDetailsActivity.this,"收藏成功！");
                } else {
                    Log.i("bmob", "失败：" + e.getMessage());
                    ToastUtils.setOkToast(JobDetailsActivity.this,"收藏失败!");
                }
            }

        });
    }
}
