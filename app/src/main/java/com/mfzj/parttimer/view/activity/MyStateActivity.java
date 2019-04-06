package com.mfzj.parttimer.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyStateActivity extends BaseActivity {

//    @BindView(R.id.mTabLayout)
//    TabLayout mTabLayout;
//    @BindView(R.id.mViewpager)
//    ViewPager mViewpager;

    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_state;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        fragments = new ArrayList<>();

    }

    @Override
    public void initData() {

    }
}
