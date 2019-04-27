package com.mfzj.parttimer.view.activity.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longsh.optionframelibrary.OptionMaterialDialog;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.mfzj.parttimer.view.activity.setting.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class JobPostDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_job_details_title)
    TextView tv_job_details_title;
    @BindView(R.id.tv_job_details_time)
    TextView tv_job_details_time;
    @BindView(R.id.tv_job_details_pay)
    TextView tv_job_details_pay;
    @BindView(R.id.tv_job_details_company)
    TextView tv_job_details_company;
    @BindView(R.id.tv_job_details_phone)
    TextView tv_job_details_phone;
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
    @BindView(R.id.btn_delete)
    Button btn_delete;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.civ_boss_logo)
    CircleImageView civ_boss_logo;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_job_post_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("兼职详情");
        String job_title= (String) getIntent().getExtras().get("job_title");
        String job_pay= (String) getIntent().getExtras().get("job_pay");
        String job_time= (String) getIntent().getExtras().get("job_time");
        String job_type= (String) getIntent().getExtras().get("job_type");
        String job_company= (String) getIntent().getExtras().get("job_company");
        String job_phone= (String) getIntent().getExtras().get("job_phone");
        String job_address= (String) getIntent().getExtras().get("job_address");
        String job_describe= (String) getIntent().getExtras().get("job_describe");
        String job_people= (String) getIntent().getExtras().get("job_people");
        String job_logo = (String) getIntent().getExtras().get("job_logo");

        if (job_title!=null){
            tv_job_details_title.setText(job_title);
        }
        if (job_pay!=null){
            tv_job_details_pay.setText(job_pay);
        }
        if (job_time!=null){
            tv_job_details_time.setText(job_time);
        }
        if (job_type!=null){
            tv_job_details_type.setText(job_type);
        }
        if (job_company!=null){
            tv_job_details_company.setText(job_company);
        }
        if (job_phone!=null){
            tv_job_details_phone.setText(job_phone);
        }
        if (job_address!=null){
            tv_job_details_address.setText(job_address);
        }
        if (job_describe!=null){
            tv_job_details_describe.setText(job_describe);
        }
        if (job_people!=null){
            tv_job_details_people.setText(job_people);
        }
        if (job_logo != null) {
            Glide.with(JobPostDetailsActivity.this).load(job_logo).error(R.drawable.head).into(civ_boss_logo);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_back,R.id.btn_apply,R.id.btn_delete})
    public void OnclickDedatils(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_apply:
                Intent intent = new Intent(JobPostDetailsActivity.this, ApplyDetailsActivity.class);
                String object_id= (String) getIntent().getExtras().get("object_id");
                intent.putExtra("object_id",object_id);
                startActivity(intent);
                break;
            case R.id.btn_delete:
                final OptionMaterialDialog mMaterialDialog0 = new OptionMaterialDialog(JobPostDetailsActivity.this);
                mMaterialDialog0.setTitle("温馨提示：")
                        .setMessage("确定删除该兼职吗")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteJob();
                                mMaterialDialog0.dismiss();
                            }
                        })
                        .setNegativeButton("取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mMaterialDialog0.dismiss();
                                    }
                                })
                        .setCanceledOnTouchOutside(true)
                        .show();

                break;
                default:break;
        }
    }
    /**
     * 删除一对一关联，解除帖子和用户的关系
     */
    private void deleteJob() {

        String object_id= (String) getIntent().getExtras().get("object_id");

        JobSelection post = new JobSelection();
        post.setObjectId(object_id);
        //删除一对一关联，解除帖子和用户的关系
        post.remove("boss");

        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.setOkToast(JobPostDetailsActivity.this,"删除兼职成功");
                    setResult(200);
                    finish();
                } else {
                    ToastUtils.setOkToast(JobPostDetailsActivity.this,"删除兼职失败");
                }
            }
        });

    }

}
