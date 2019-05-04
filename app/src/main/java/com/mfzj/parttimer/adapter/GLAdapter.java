package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.StrategyTable;

import java.util.List;

public class GLAdapter extends BaseQuickAdapter<StrategyTable, BaseViewHolder> {
    private Context context;

    public GLAdapter(@Nullable List<StrategyTable> data , Context context) {
        super(R.layout.item_home_head_strategy, data);
        this.context= context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StrategyTable item) {
        helper.setText(R.id.tv_str_title,item.getStrategy_title());
        Glide.with(context)
                .load(item.getStrategy_image_url())
                .placeholder(R.drawable.banner_default)
                .error(R.drawable.banner_default)
                .into((ImageView) helper.getView(R.id.iv_str_img));
    }
}
