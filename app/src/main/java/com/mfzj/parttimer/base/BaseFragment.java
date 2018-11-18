package com.mfzj.parttimer.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbander;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(getLayoutResId(),container,false);
        //绑定的时候返回一个unbander对象
        unbander = ButterKnife.bind(this,view);
        initView(view);
        initData();
        return view;
    }

    /**
     * 获取布局资源
     * @return
     */
    public abstract int getLayoutResId();

    /**
     * 初始化控件
     * @param view
     */
    public abstract void initView(View view);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        unbander.unbind();
    }

}
