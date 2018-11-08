package com.bee.parttimer.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseFragment;
import com.bee.parttimer.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostFragment extends BaseFragment {
    @BindView(R.id.float_btn)
    FloatingActionButton float_btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_post,container,false);
        ButterKnife.bind(this,view);
        initView(view);
        initData();
        return view;
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
