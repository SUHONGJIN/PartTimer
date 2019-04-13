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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
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
import com.mfzj.parttimer.adapter.SelectionAdapter;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.BannerBean;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.mfzj.parttimer.view.activity.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout mSearchLayout;
    @BindView(R.id.ll_load_state)
    LinearLayout ll_load_state;
    @BindView(R.id.rl_network_error)
    RelativeLayout rl_network_error;
    boolean isExpand = false;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.btn_load)
    Button btn_load;

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

    private List<JobSelection> datalist;
    private SelectionAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        //获取轮播图
        getBannerData();
        //getJobList();
        //配置定位信息
        configLcation();
        //检查权限的获取状态并开始定位
        checkPermission();
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getBannerData();
                //getJobList();
                mSmartRefreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_load.setText("加载中...");
                getBannerData();
                //getJobList();
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
                        web_list.add(bannerbean.getBanner_web_url());
                    }
                    //获取兼职信息列表
                    getJobList();

                } else {
                    Log.e("banner", "轮播图数据获取失败----" + e);
                }
            }
        });
    }


    private void getJobList() {
        datalist = new ArrayList<>();
        //获取后台数据
        BmobQuery<JobSelection> query = new BmobQuery<JobSelection>();
        query.findObjects(new FindListener<JobSelection>() {
            @Override
            public void done(final List<JobSelection> list, BmobException e) {
                if (e == null) {

                    mRecyclerView.setVisibility(View.VISIBLE);
                    ll_load_state.setVisibility(View.GONE);
                    rl_network_error.setVisibility(View.GONE);

                    //添加数据到集合
                    datalist.addAll(list);
                    //适配器
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    adapter = new SelectionAdapter(getContext(), datalist, titles_list, images_list, web_list);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new SelectionAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getContext(), JobDetailsActivity.class);
                            intent.putExtra("job_title", datalist.get(position).getJob_title());
                            intent.putExtra("job_pay", datalist.get(position).getJob_pay());
                            intent.putExtra("job_time", datalist.get(position).getJob_time());
                            intent.putExtra("job_type", datalist.get(position).getJob_type());
                            intent.putExtra("job_company", datalist.get(position).getJob_company());
                            intent.putExtra("job_address", datalist.get(position).getJob_address());
                            intent.putExtra("job_describe", datalist.get(position).getJob_describe());
                            intent.putExtra("job_people", datalist.get(position).getJob_people());
                            intent.putExtra("job_logo", datalist.get(position).getJob_logo());
                            intent.putExtra("object_id", datalist.get(position).getObjectId());
                            startActivity(intent);
                        }
                    });
                } else {
                    ToastUtils.setOkToast(getContext(), "请检查网络！");
                    rl_network_error.setVisibility(View.VISIBLE);
                    ll_load_state.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);

                }
            }
        });
    }


    @Override
    public void initData() {
    }

    @OnClick({R.id.tv_location, R.id.ll_search})
    public void Onlick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                Intent intent = new Intent(getContext(), CityPickerActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.ll_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
        }
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
