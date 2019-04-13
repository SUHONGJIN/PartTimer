package com.mfzj.parttimer.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentDay;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentMonth;
import com.mfzj.parttimer.view.fragment.jobclassfiy.FragmentWeekend;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Duty;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_Finish;
import com.mfzj.parttimer.view.fragment.mystate.Fragment_MyApply;

import java.util.ArrayList;
import java.util.List;

public class PostFragment extends BaseFragment {

    private FragmentDay fg1;
    private FragmentWeekend fg2;
    private FragmentMonth fg3;
    private TabLayout my_tab;
    private ViewPager my_viewpager;
    private List<Fragment> fragments;
    private List<String> titles;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initView(View view) {
        my_tab = (TabLayout) view.findViewById(R.id.my_tab);
        my_viewpager = (ViewPager) view.findViewById(R.id.my_viewpager);
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
        my_viewpager.setAdapter(adapter);
        my_tab.setupWithViewPager(my_viewpager);
    }

    @Override
    public void initData() {

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

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
