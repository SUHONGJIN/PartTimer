package com.mfzj.parttimer.view.activity.postjob;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.BossSelectionAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class MyPostPartTimerActivity extends BaseActivity {
    @BindView(R.id.float_btn)
    FloatingActionButton float_btn;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private List<JobSelection> datalist;
    private BossSelectionAdapter adapter;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_post_part_timer;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("我发布的兼职");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPostPartTimerActivity.this, AddPartTimerActivity.class));
            }
        });
    }

    @Override
    public void initData() {
        getMyPost();
    }
    /**
     * 查询一对一关联，查询当前用户发表的所有兼职
     */
    private void getMyPost() {
        datalist = new ArrayList<>();

        if (BmobUser.isLogin()) {
            final BmobQuery<JobSelection> query = new BmobQuery<>();
            query.addWhereEqualTo("boss", BmobUser.getCurrentUser(User.class));
            query.order("-updatedAt");
            //包含作者信息
            query.include("boss");
            query.findObjects(new FindListener<JobSelection>() {
                @Override
                public void done(List<JobSelection> object, BmobException e) {
                    if (e == null) {
                        //添加数据到集合
                        datalist.addAll(object);
                        //适配器
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MyPostPartTimerActivity.this);
                        adapter = new BossSelectionAdapter(MyPostPartTimerActivity.this,datalist);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new BossSelectionAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(MyPostPartTimerActivity.this, JobPostDetailsActivity.class);
                                intent.putExtra("job_title",datalist.get(position).getJob_title());
                                intent.putExtra("job_pay",datalist.get(position).getJob_pay());
                                intent.putExtra("job_time",datalist.get(position).getJob_time());
                                intent.putExtra("job_type",datalist.get(position).getJob_type());
                                intent.putExtra("job_company",datalist.get(position).getJob_company());
                                intent.putExtra("job_address",datalist.get(position).getJob_address());
                                intent.putExtra("job_describe",datalist.get(position).getJob_describe());
                                intent.putExtra("job_people",datalist.get(position).getJob_people());
                                intent.putExtra("object_id",datalist.get(position).getObjectId());
                                startActivity(intent);
                            }
                        });
                    } else {

                    }
                }

            });
        } else {

        }
    }
    }