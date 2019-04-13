package com.mfzj.parttimer.view.fragment.jobclassfiy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.BossSelectionAdapter;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
    private List<JobSelection> datalist;
    private BossSelectionAdapter adapter;
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
        return R.layout.fargment_day_layout;
    }

    @Override
    public void initView(View view) {
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDayJob();
                mSmartRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_load.setText("加载中...");
                getDayJob();
            }
        });
    }

    @Override
    public void initData() {
        getDayJob();
    }

    /**
     * name为football的类别
     */
    private void getDayJob() {
        datalist = new ArrayList<>();
        BmobQuery<JobSelection> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("job_type", "日结");
        categoryBmobQuery.findObjects(new FindListener<JobSelection>() {
            @Override
            public void done(List<JobSelection> object, BmobException e) {
                if (e == null) {
                    //添加数据到集合
                    datalist.addAll(object);
                    //适配器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    adapter = new BossSelectionAdapter(getContext(), datalist);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(adapter);

                    rl_network_error.setVisibility(View.GONE);
                    ll_load_state.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    adapter.setOnItemClickListener(new BossSelectionAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), JobDetailsActivity.class);
                            intent.putExtra("job_title", datalist.get(position).getJob_title());
                            intent.putExtra("job_pay", datalist.get(position).getJob_pay());
                            intent.putExtra("job_time", datalist.get(position).getJob_time());
                            intent.putExtra("job_type", datalist.get(position).getJob_type());
                            intent.putExtra("job_company", datalist.get(position).getJob_company());
                            intent.putExtra("job_address", datalist.get(position).getJob_address());
                            intent.putExtra("job_describe", datalist.get(position).getJob_describe());
                            intent.putExtra("job_people", datalist.get(position).getJob_people());
                            intent.putExtra("job_logo", datalist.get(position).getJob_logo());
                            intent.putExtra("object_id", datalist.get(position).getObjectId());
                            startActivity(intent);
                        }

                    });

                } else {
                    //Log.e("BMOB", e.toString());
                    rl_network_error.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    ll_load_state.setVisibility(View.GONE);
                    btn_load.setText("重新加载");
                }
            }
        });
    }
}
