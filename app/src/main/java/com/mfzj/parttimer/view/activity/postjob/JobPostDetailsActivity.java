package com.mfzj.parttimer.view.activity.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class JobPostDetailsActivity extends BaseActivity {
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
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

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
        String job_address= (String) getIntent().getExtras().get("job_address");
        String job_describe= (String) getIntent().getExtras().get("job_describe");
        String job_people= (String) getIntent().getExtras().get("job_people");

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
        if (job_address!=null){
            tv_job_details_address.setText(job_address);
        }
        if (job_describe!=null){
            tv_job_details_describe.setText(job_describe);
        }
        if (job_people!=null){
            tv_job_details_people.setText(job_people);
        }

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_back,R.id.btn_apply})
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
                default:break;
        }
    }
}
