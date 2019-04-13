package com.mfzj.parttimer.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Hire;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_MyApply;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Duty;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Finish;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyStateActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewpager)
    ViewPager mViewpager;
    private Fragment_Hire hire;
    private Fragment_MyApply myApply;
    private Fragment_Duty duty;
    private Fragment_Finish fragment_finish;
    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_state;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tv_title.setText("我参与的兼职");

        hire=new Fragment_Hire();
        myApply=new Fragment_MyApply();
        duty=new Fragment_Duty();
        fragment_finish=new Fragment_Finish();

        //添加碎片
        fragments=new ArrayList<>();
        fragments.add(myApply);
        fragments.add(hire);
        fragments.add(duty);
        fragments.add(fragment_finish);

        //添加标题
        titles=new ArrayList<>();
        titles.add("已报名");
        titles.add("已录用");
        titles.add("已到岗");
        titles.add("已完成");

        mViewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewpager);

        //选中的位置
        String tag=getIntent().getExtras().get("tag").toString();
        if (tag!=null){

            if (tag.equals("1")){
                mTabLayout.getTabAt(0).select();
            }
            if (tag.equals("2")){
                mTabLayout.getTabAt(1).select();
            }
            if (tag.equals("3")){
                mTabLayout.getTabAt(2).select();
            }
            if (tag.equals("4")){
                mTabLayout.getTabAt(3).select();
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
