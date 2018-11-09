package com.bee.parttimer.view.fragment;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseFragment;
import com.bee.parttimer.utils.ToastUtils;

import butterknife.BindView;

public class PostFragment extends BaseFragment {
    @BindView(R.id.float_btn)
    FloatingActionButton float_btn;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initView(View view) {
        float_btn=(FloatingActionButton)view.findViewById(R.id.float_btn);
        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.setOkToast(getContext(),"悬浮按钮！");
            }
        });
    }

    @Override
    public void initData() {

    }
}
