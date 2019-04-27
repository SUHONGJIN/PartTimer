package com.mfzj.parttimer.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentDay;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentMonth;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentWeekend;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Duty;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Finish;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_MyApply;
import com.mfzj.parttimer.widget.ColorFlipPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PostFragment extends BaseFragment {

    private FragmentDay fg1;
    private FragmentWeekend fg2;
    private FragmentMonth fg3;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private List<String> titles;
    @BindView(R.id.mMagicIndicator)
    MagicIndicator mMagicIndicator;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.my_viewpager);
        fg1 = new FragmentDay();
        fg2 = new FragmentWeekend();
        fg3 = new FragmentMonth();
        //添加fragment
        fragments = new ArrayList<>();
        fragments.add(fg1);
        fragments.add(fg2);
        fragments.add(fg3);
        //添加标题
        titles = new ArrayList<>();
        titles.add("日结兼职");
        titles.add("周结兼职");
        titles.add("月结兼职");
        MyAdapter adapter = new MyAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initData() {
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator7 = new CommonNavigator(getContext());
        commonNavigator7.setScrollPivotX(0.65f);
        commonNavigator7.setAdjustMode(true);
        commonNavigator7.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(Color.parseColor("#9e9e9e"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
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
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 34));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#FFC107"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator7);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);

    }
    private class MyAdapter extends FragmentPagerAdapter {
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
