package com.mfzj.parttimer.view.activity;

import android.content.Context;
import android.graphics.Color;
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
import com.mfzj.parttimer.widget.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyStateActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mViewpager)
    ViewPager mViewPager;
    @BindView(R.id.mMagicIndicator)
    MagicIndicator mMagicIndicator;
    private Fragment_Hire hire;
    private Fragment_MyApply myApply;
    private Fragment_Duty duty;
    private Fragment_Finish fragment_finish;
    private List<Fragment> fragments;
    private List<String> mTitleDataList;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_state;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tv_title.setText("我参与的兼职");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //初始化碎片
        hire = new Fragment_Hire();
        myApply = new Fragment_MyApply();
        duty = new Fragment_Duty();
        fragment_finish = new Fragment_Finish();

        //添加碎片
        fragments = new ArrayList<>();
        fragments.add(myApply);
        fragments.add(hire);
        fragments.add(duty);
        fragments.add(fragment_finish);

        //添加标题
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("已报名");
        mTitleDataList.add("已录用");
        mTitleDataList.add("已到岗");
        mTitleDataList.add("已完成");
        //适配器
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));


    }

    @Override
    public void initData() {

        mMagicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true); //标题自动充满屏幕
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitleDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setHorizontallyScrolling(false);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#FFC107"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);

        //根据tag判断要跳转到哪个指定的页面。
        int tag = getIntent().getIntExtra("tag",1);
        if (tag==0){
            mViewPager.setCurrentItem(0);
        }
        if (tag==1){
            mViewPager.setCurrentItem(1);
        }
        if (tag==2){
            mViewPager.setCurrentItem(2);
        }
        if (tag==3){
            mViewPager.setCurrentItem(3);
        }
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
    }
}
