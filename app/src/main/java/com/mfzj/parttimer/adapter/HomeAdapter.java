package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.JobSelection;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<JobSelection, BaseViewHolder> {
    private Context context;

    public HomeAdapter(@Nullable List<JobSelection> data ,Context context) {
        super(R.layout.item_list_job, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, JobSelection item) {
        helper.setText(R.id.tv_job_title,item.getJob_title());
        helper.setText(R.id.tv_job_pay,item.getJob_pay());
        helper.setText(R.id.tv_job_time,item.getJob_time().substring(0, 10));
        helper.setText(R.id.tv_job_type,item.getJob_type());
        helper.setText(R.id.tv_job_company,item.getJob_company());
        helper.setText(R.id.tv_job_address,item.getJob_address());

        Glide.with(context).load(item.getJob_logo()).placeholder(R.drawable.head).error(R.drawable.head).into((ImageView) helper.getView(R.id.civ_boss_logo));

    }
}
