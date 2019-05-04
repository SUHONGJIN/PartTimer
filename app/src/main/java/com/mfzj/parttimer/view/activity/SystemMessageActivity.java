package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.SystemMessageAdapter;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.bean.SystemMessage;
import com.mfzj.parttimer.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SystemMessageActivity extends BaseActivity {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private List<SystemMessage> datalist = new ArrayList<>();
    SystemMessageAdapter adapter;
    @BindView(R.id.tv_title)
    TextView textView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    private final int limit = 10;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_system_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        textView.setText("系统消息");
        adapter = new SystemMessageAdapter(datalist, SystemMessageActivity.this);
    }

    @OnClick(R.id.iv_back)
    public void click(View view) {
        finish();
    }

    @Override
    public void initData() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(SystemMessageActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mSmartRefreshLayout.autoRefresh();  //自动刷新

        mRecyclerView.setAdapter(adapter);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                BmobQuery<SystemMessage> bmobQuery = new BmobQuery<>();
                bmobQuery.order("-createdAt");
                bmobQuery.setSkip(0);
                bmobQuery.setLimit(limit);
                bmobQuery.findObjects(new FindListener<SystemMessage>() {
                    @Override
                    public void done(List<SystemMessage> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                datalist.clear();
                                datalist.addAll(list);
                                adapter.replaceData(datalist);
                            }

                        } else {
                            ToastUtils.setOkToast(SystemMessageActivity.this, "获取数据失败！请重试~");
                        }
                        mSmartRefreshLayout.finishRefresh();
                    }

                });
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                BmobQuery<SystemMessage> bmobQuery = new BmobQuery<>();
                bmobQuery.setSkip(datalist.size());
                bmobQuery.setLimit(limit);
                bmobQuery.order("-createdAt");
                bmobQuery.findObjects(new FindListener<SystemMessage>() {
                    @Override
                    public void done(List<SystemMessage> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                datalist.addAll(list);
                                adapter.replaceData(datalist);
                            }

                        } else {
                            ToastUtils.setOkToast(SystemMessageActivity.this, "获取数据失败！请重试~");
                        }
                        mSmartRefreshLayout.finishLoadMore();
                    }
                });
            }
        });

    }
}
