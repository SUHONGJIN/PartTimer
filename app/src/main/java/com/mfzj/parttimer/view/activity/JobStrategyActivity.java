package com.mfzj.parttimer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.StrategyAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.StrategyTable;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.widget.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

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
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    private List<StrategyTable> datalist;
    private StrategyAdapter adapter;
    private final int REQUEST_UPDATE_CODE = 1;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_job_strategy;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("兼职攻略");
        mSmartRefreshLayout.autoRefresh();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getStrategy();
            }
        });
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
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<StrategyTable>() {
            @Override
            public void done(final List<StrategyTable> data, BmobException e) {
                if (e == null) {

                    mSmartRefreshLayout.finishRefresh();
                    datalist.clear();
                    datalist.addAll(data);
                    adapter = new StrategyAdapter(datalist, JobStrategyActivity.this);
                    //瀑布流布局
                    mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    //设置item之间的间隔
                    //SpacesItemDecoration decoration = new SpacesItemDecoration(16);
                    //mRecyclerView.addItemDecoration(decoration);
                    mRecyclerView.setAdapter(adapter);
                    //点击事件
                    adapter.setOnItemClickListener(new StrategyAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            StrategyTable strategyTable = new StrategyTable();
                            strategyTable.setStrategy_read(datalist.get(position).getStrategy_read()+1);
                            strategyTable.update(datalist.get(position).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                    }else {
                                    }
                                }
                            });
                            Intent intent = new Intent(JobStrategyActivity.this, WebDetailsActivity.class);
                            intent.putExtra("url", data.get(position).getStrategy_web_url());
                            startActivityForResult(intent,REQUEST_UPDATE_CODE);
                        }
                    });
                } else {

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_CODE){
            switch (resultCode){
                case 200:
                    //getStrategy();
                    break;
                    default:
                        break;
            }
        }
    }
}
