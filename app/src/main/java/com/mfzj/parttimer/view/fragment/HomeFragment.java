package com.mfzj.parttimer.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.mfzj.parttimer.CitySelect.CityPickerActivity;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.BannerBean;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.mBanner)
    Banner mBanner;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout mSearchLayout;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    boolean isExpand = false;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TransitionSet mSet;
    //轮播图的集合
    private ArrayList<String> images_list;
    private ArrayList<String> titles_list;
    private ArrayList<String> web_list;
    //百度定位
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //创建权限集合
    private List<String> permissionList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {

        //获取轮播图
        getBannerData();

        //配置定位信息
        configLcation();

        //检查权限的获取状态并开始定位
        checkPermission();

        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CityPickerActivity.class);
                startActivityForResult(intent, 2);
            }
        });

    }

    /**
     * 检查权限的获取状态并开始定位
     */
    private void checkPermission() {
        //检查权限是否获取
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!permissionList.isEmpty()) {
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permission, 1);
        } else {
            //开始定位
            requestLocation();
        }
    }

    /**
     * 配置定位信息
     */
    private void configLcation() {
        //配置定位SDK参数
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    //获取轮播图后台数据
    private void getBannerData() {
        //放图片地址和标题的集合
        titles_list = new ArrayList<>();
        images_list = new ArrayList<>();
        web_list = new ArrayList<>();
        BmobQuery<BannerBean> query = new BmobQuery<BannerBean>();
        query.findObjects(new FindListener<BannerBean>() {
            @Override
            public void done(List<BannerBean> list, BmobException e) {
                if (e == null) {
                    for (BannerBean bannerbean : list) {
                        //添加数据到集合
                        titles_list.add(bannerbean.getBanner_title());
                        images_list.add(bannerbean.getBanner_image_url());
                        web_list.add(bannerbean.getBanner_image_url());
                        setBanner(titles_list, images_list, web_list);
                    }
                } else {
                    Log.e("banner", "轮播图数据获取失败----" + e);
                }
            }
        });
    }

    /**
     * 设置轮播图
     *
     * @param titlesList
     * @param titles_list
     * @param images_list
     */
    private void setBanner(ArrayList<String> titlesList, ArrayList<String> titles_list, ArrayList<String> images_list) {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new MyLoader());
        //设置图片地址集合
        mBanner.setImages(this.images_list);
        //设置标题的集合
        mBanner.setBannerTitles(this.titles_list);
        //设置轮播间隔时间
        mBanner.setDelayTime(3000);
        //设置轮播动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置轮播
        mBanner.isAutoPlay(true);
        //设置指示器位置
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //设置监听
        //mBanner.setOnBannerListener(this);
        //启动轮播图
        mBanner.start();
        //轮播图的点击事件
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ToastUtils.setOkToast(getContext(), web_list.get(position));
            }
        });
    }

    //轮播图图片加载器
    private class MyLoader implements ImageLoaderInterface {
        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }

        @Override
        public View createImageView(Context context) {
            return null;
        }
    }

    @Override
    public void initData() {

        //设置toolbar初始透明度为0
        toolbar.getBackground().mutate().setAlpha(0);
        //scrollview滚动状态监听
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //改变toolbar的透明度
                changeToolbarAlpha();
                //滚动距离>=大图高度-toolbar高度 即toolbar完全盖住大图的时候 且不是伸展状态 进行伸展操作
                if (mScrollView.getScrollY() >= mBanner.getHeight() - toolbar.getHeight() && !isExpand) {
                    expand();
                    isExpand = true;
                }
                //滚动距离<=0时 即滚动到顶部时  且当前伸展状态 进行收缩操作
                else if (mScrollView.getScrollY() <= 0 && isExpand) {
                    reduce();
                    isExpand = false;
                }
            }
        });
    }

    /**
     * 开始定位的方法
     */
    public void requestLocation() {
        mLocationClient.start();
    }

    /**
     * 获取定位结果
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            String cityname = location.getCity();    //获取城市
            //获取到定位信息保存到本地储存
            if (cityname != null) {
                SharedPreferencesUtils.saveStringSharedPreferences(getContext(), "location", cityname);
            }
            //获取已保存的定位信息
            String city = SharedPreferencesUtils.getStringSharedPreferences(getContext(), "location", "定位失败");
            if (city != null) {
                tv_location.setText(city);
            } else {
                tv_location.setText("定位中...");
            }

        }
    }

    private void changeToolbarAlpha() {
        int scrollY = mScrollView.getScrollY();
        //快速下拉会引起瞬间scrollY<0
        if (scrollY < 0) {
            toolbar.getBackground().mutate().setAlpha(0);
            return;
        }
        //计算当前透明度比率
        float radio = Math.min(1, scrollY / (mBanner.getHeight() - toolbar.getHeight() * 1f));
        //设置透明度
        toolbar.getBackground().mutate().setAlpha((int) (radio * 0xFF));
    }

    private void expand() {
        //设置伸展状态时的布局
        tvSearch.setText("搜索附近兼职");
        tv_location.setVisibility(View.GONE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) mSearchLayout.getLayoutParams();
        LayoutParams.width = LayoutParams.MATCH_PARENT;
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        mSearchLayout.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(mSearchLayout);
    }

    private void reduce() {
        //设置收缩状态时的布局
        tvSearch.setText("搜索");
        tv_location.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) mSearchLayout.getLayoutParams();
        LayoutParams.width = dip2px(80);
        LayoutParams.setMargins(dip2px(10), dip2px(10), dip2px(10), dip2px(10));
        mSearchLayout.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(mSearchLayout);
    }

    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }

    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int results : grantResults) {
                        if (results != PackageManager.PERMISSION_GRANTED) {
                            ToastUtils.setOkToast(getContext(), "为了能正常使用APP，建议打开相应的权限~");
                        } else {
                            requestLocation();
                        }
                    }
                } else {
                    ToastUtils.setOkToast(getContext(), "未知权限错误！");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 200) {
                if (data == null) {
                    return;
                }
                String cityname = data.getStringExtra("cityname");
                tv_location.setText(cityname);
                SharedPreferencesUtils.saveStringSharedPreferences(getContext(), "location", cityname);
            }
        }
    }

}
