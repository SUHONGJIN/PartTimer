package com.mfzj.parttimer.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.ToastUtils;

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
    //创建权限集合
    private List<String> permissionList = new ArrayList<>();

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
    public void initData() {
        //检查权限是否获取
        if (ContextCompat.checkSelfPermission(WelcomeGuideActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(WelcomeGuideActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(WelcomeGuideActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(WelcomeGuideActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!permissionList.isEmpty()) {
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WelcomeGuideActivity.this, permission, 1);
        } else {
            //开始定位
        }
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int results : grantResults) {
                        if (results != PackageManager.PERMISSION_GRANTED) {
                            ToastUtils.setOkToast(WelcomeGuideActivity.this, "为了能正常使用APP，建议打开相应的权限~");
                        } else {

                        }
                    }
                } else {
                    ToastUtils.setOkToast(this, "未知权限错误！");
                }
                break;
            default:
                break;
        }
    }
}

