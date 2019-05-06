package com.mfzj.parttimer.view.fragment.jobclassfiy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.HomeAdapter;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentDay extends BaseFragment {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private List<JobSelection> datalist = new ArrayList<>();
    private HomeAdapter adapter;
    @BindView(R.id.ll_load_state)
    LinearLayout ll_load_state;
    @BindView(R.id.rl_network_error)
    RelativeLayout rl_network_error;
    @BindView(R.id.btn_load)
    Button btn_load;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fargment_job_classfy_layout;
    }

    @Override
    public void initView(View view) {

        mSmartRefreshLayout.autoRefresh();

        adapter = new HomeAdapter(datalist, getContext());

        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_load.setText("加载中...");
                mSmartRefreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                BmobQuery<JobSelection> query = new BmobQuery<>();
                query.order("-createdAt");
                query.addWhereEqualTo("job_type", "日结");
                query.setLimit(10);
                query.setSkip(0);
                query.findObjects(new FindListener<JobSelection>() {
                    @Override
                    public void done(List<JobSelection> list, BmobException e) {
                        if (e == null) {
                            rl_network_error.setVisibility(View.GONE);
                            ll_load_state.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);

                            datalist.clear();
                            datalist.addAll(list);
                            adapter.replaceData(datalist);
                        } else {
                            rl_network_error.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            ll_load_state.setVisibility(View.GONE);
                            btn_load.setText("重新加载");
                            ToastUtils.setOkToast(getContext(), "请求失败，请重试！");
                        }
                        mSmartRefreshLayout.finishRefresh();
                    }
                });

            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                BmobQuery<JobSelection> query = new BmobQuery<>();
                query.order("-createdAt");
                query.addWhereEqualTo("job_type", "日结");
                query.setLimit(10);
                query.setSkip(datalist.size());
                query.findObjects(new FindListener<JobSelection>() {
                    @Override
                    public void done(List<JobSelection> list, BmobException e) {
                        if (e == null) {
                            rl_network_error.setVisibility(View.GONE);
                            ll_load_state.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            datalist.addAll(list);
                            adapter.replaceData(datalist);
                        } else {
                            rl_network_error.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                            ll_load_state.setVisibility(View.GONE);
                            btn_load.setText("重新加载");
                            ToastUtils.setOkToast(getContext(), "请求失败，请重试！");
                        }
                        mSmartRefreshLayout.finishLoadMore();
                    }
                });
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), JobDetailsActivity.class);
                intent.putExtra("job_title", datalist.get(position).getJob_title());
                intent.putExtra("job_pay", datalist.get(position).getJob_pay());
                intent.putExtra("job_time", datalist.get(position).getJob_time());
                intent.putExtra("job_type", datalist.get(position).getJob_type());
                intent.putExtra("job_company", datalist.get(position).getJob_company());
                intent.putExtra("job_phone", datalist.get(position).getJob_phone());
                intent.putExtra("job_address", datalist.get(position).getJob_address());
                intent.putExtra("job_describe", datalist.get(position).getJob_describe());
                intent.putExtra("job_people", datalist.get(position).getJob_people());
                intent.putExtra("job_logo", datalist.get(position).getJob_logo());
                intent.putExtra("object_id", datalist.get(position).getObjectId());
                startActivity(intent);
            }
        });
    }

}
