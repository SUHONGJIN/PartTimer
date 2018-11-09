package com.bee.parttimer.view.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeGuideActivity extends BaseActivity {

    @BindView(R.id.pager_guide)
    ViewPager viewPager;
    @BindView(R.id.btn_guide)
    Button btn_guide;

    List<View> list;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_guide;
    }

    /**
     * 跳转到主页
     *
     * @param view
     */
    @OnClick(R.id.btn_guide)
    public void click(View view) {
        startActivity(new Intent(WelcomeGuideActivity.this, MainActivity.class));
        finish();
    }

    /**
     * 初始化ViewPager
     */
    @Override
    public void initView(Bundle savedInstanceState) {

        list = new ArrayList<View>();

        ImageView iv1 = new ImageView(this);

        iv1.setImageResource(R.drawable.bg_guide1);     //为ImageView添加图片资源
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);   //设置图片充满屏幕
        list.add(iv1);                                  //把ImageView添加到list集合

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.bg_guide2);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        list.add(iv2);

        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.bg_guide3);
        iv3.setScaleType(ImageView.ScaleType.FIT_XY);
        list.add(iv3);

        setPager();

    }

    @Override
    public void initData() {}

    /**
     * ViewPager的适配器
     */
    class MyPagerAdapter extends PagerAdapter {

        //计算item个数
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        //item的销毁方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }

    /**
     * 设置ViewPager
     */
    private void setPager() {
        //监听ViewPager滑动的效果
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //选中的页卡
            @Override
            public void onPageSelected(int position) {
                //如果是第三个页面就显示按钮，反之不显示
                if (position == 2) {
                    btn_guide.setVisibility(View.VISIBLE);
                } else {
                    btn_guide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //设置适配器
        viewPager.setAdapter(new MyPagerAdapter());
    }


}

