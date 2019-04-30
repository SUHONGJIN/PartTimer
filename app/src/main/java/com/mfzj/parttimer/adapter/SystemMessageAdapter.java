package com.mfzj.parttimer.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.SystemMessage;

import java.util.List;

public class SystemMessageAdapter extends BaseQuickAdapter<SystemMessage, BaseViewHolder> {
    private Context context;

    public SystemMessageAdapter(@Nullable List<SystemMessage> data, Context context) {
        super(R.layout.item_system_message, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessage item) {
        helper.setText(R.id.tv_system_message,item.getMessage());
        helper.setText(R.id.tv_system_message_date,item.getCreatedAt());
    }
}
