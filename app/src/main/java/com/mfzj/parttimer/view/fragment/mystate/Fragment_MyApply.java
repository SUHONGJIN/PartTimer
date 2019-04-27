package com.mfzj.parttimer.view.fragment.mystate;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.BossSelectionAdapter;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_MyApply extends BaseFragment {
    private List<JobSelection> datalist;
    private BossSelectionAdapter adapter;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private final int UPDATE_DATE_CODE = 1;
    private final int RESULT_DATE_CODE = 100;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tab_myapply;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {
        getMyApply();
    }

    /**
     * 查询当前用户报名的所有兼职
     */
    private void getMyApply() {
        datalist = new ArrayList<>();
        final BmobQuery<JobSelection> query = new BmobQuery<>();
        query.addWhereEqualTo("apply", BmobUser.getCurrentUser(User.class));
        query.order("-updatedAt");
        //包含作者信息
        query.include("apply");
        query.findObjects(new FindListener<JobSelection>() {
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
                    adapter.setOnItemClickListener(new BossSelectionAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
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
                            intent.putExtra("job_logo",datalist.get(position).getJob_logo());
                            intent.putExtra("object_id", datalist.get(position).getObjectId());
                            startActivityForResult(intent,UPDATE_DATE_CODE);
                        }

                    });

                } else {

                }
            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==UPDATE_DATE_CODE){
            switch (resultCode){
                case RESULT_DATE_CODE:
                    //刷新数据
                    getMyApply();
                    break;
                    default:
                        break;
            }
        }
    }
}
