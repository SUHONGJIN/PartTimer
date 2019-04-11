package com.mfzj.parttimer.view.activity.postjob;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.ApplyDetailsAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.ApplyTable;
import com.mfzj.parttimer.bean.JobSelection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ApplyDetailsActivity extends BaseActivity {

    private ApplyDetailsAdapter adapter;
    List<ApplyTable> datalist;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_apply_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("已报名兼职");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        datalist = new ArrayList<>();

        String object_id = (String) getIntent().getExtras().get("object_id");
        BmobQuery<ApplyTable> query = new BmobQuery<ApplyTable>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        JobSelection post = new JobSelection();
        post.setObjectId(object_id);
        query.addWhereEqualTo("job", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,job,apply");
        query.findObjects(new FindListener<ApplyTable>() {

            @Override
            public void done(List<ApplyTable> objects, BmobException e) {
                //添加数据到集合
                datalist.addAll(objects);
                //适配器
                LinearLayoutManager layoutManager = new LinearLayoutManager(ApplyDetailsActivity.this);
                adapter = new ApplyDetailsAdapter(ApplyDetailsActivity.this,datalist);
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(adapter);
            }
        });

    }
}
