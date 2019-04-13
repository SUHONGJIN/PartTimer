package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.StrategyAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.StrategyTable;
import com.mfzj.parttimer.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 兼职攻略页面
 */
public class JobStrategyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private List<StrategyTable> datalist;
    private StrategyAdapter adapter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_job_strategy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("兼职攻略");
    }

    @Override
    public void initData() {
        getStrategy();

    }

    @OnClick(R.id.iv_back)
    public void Onclick(View view) {
        finish();
    }


    /**
     * 查询兼职攻略
     */
    private void getStrategy() {
        datalist = new ArrayList<>();
        BmobQuery<StrategyTable> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<StrategyTable>() {
            @Override
            public void done(final List<StrategyTable> data, BmobException e) {
                if (e == null) {
                    datalist.addAll(data);
                    adapter = new StrategyAdapter(datalist, JobStrategyActivity.this);
                    //瀑布流布局
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    //设置item之间的间隔
                    SpacesItemDecoration decoration = new SpacesItemDecoration(16);
                    mRecyclerView.addItemDecoration(decoration);
                    mRecyclerView.setAdapter(adapter);
                    //点击事件
                    adapter.setOnItemClickListener(new StrategyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(JobStrategyActivity.this, WebDetailsActivity.class);
                            intent.putExtra("url", data.get(position).getStrategy_web_url());
                            startActivity(intent);
                        }
                    });
                } else {

                }
            }
        });
    }

}
