package com.mfzj.parttimer.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mfzj.parttimer.CitySelect.CityPickerActivity;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.BannerViewHolder;
import com.mfzj.parttimer.adapter.GLAdapter;
import com.mfzj.parttimer.adapter.HomeAdapter;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.BannerBean;
import com.mfzj.parttimer.bean.JobSelection;
import com.mfzj.parttimer.bean.StrategyTable;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.JobDetailsActivity;
import com.mfzj.parttimer.view.activity.JobStrategyActivity;
import com.mfzj.parttimer.view.activity.SearchActivity;
import com.mfzj.parttimer.view.activity.WebDetailsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

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
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)

    SmartRefreshLayout mSmartRefreshLayout;
    private MZBannerView mBanner;
    private RecyclerView sRecyclerView;
    //百度定位
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    //创建权限集合
    private List<String> permissionList = new ArrayList<>();
    private List<JobSelection> datalist = new ArrayList<>();
    private List<StrategyTable> strategyList = new ArrayList<>();
    private HomeAdapter adapter;
    private GLAdapter sAdapter;
    private RelativeLayout rl_strategy;

    private final int UPDATE_DATE_CODE = 2;
    private final int RESULT_DATE_CODE = 100;
    private final int RESULT_LOCATION_CODE = 200;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {

        //配置定位信息
        configLcation();

        //检查权限的获取状态并开始定位
        checkPermission();

        adapter = new HomeAdapter(datalist, getContext());
        sAdapter = new GLAdapter(strategyList, getContext());

        headPart();

        //自动刷新
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void initData() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                //刷新兼职攻略数据
                getStrategy();

                BmobQuery<JobSelection> query = new BmobQuery<>();
                query.order("-createdAt");
                query.setSkip(0);
                query.setLimit(10);
                query.findObjects(new FindListener<JobSelection>() {
                    @Override
                    public void done(List<JobSelection> list, BmobException e) {
                        if (e == null) {
                            datalist.clear();
                            datalist.addAll(list);
                            adapter.replaceData(datalist);
                        } else {
                            ToastUtils.setOkToast(getContext(), "获取数据失败，请重试~");
                        }
                    }
                });
                mSmartRefreshLayout.finishRefresh();
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                BmobQuery<JobSelection> query = new BmobQuery<>();
                query.order("-createdAt");
                query.setSkip(datalist.size());
                query.setLimit(10);
                query.findObjects(new FindListener<JobSelection>() {
                    @Override
                    public void done(List<JobSelection> list, BmobException e) {
                        if (e == null) {
                            datalist.addAll(list);
                            adapter.replaceData(datalist);
                        } else {
                            ToastUtils.setOkToast(getContext(), "获取数据失败，请重试~");
                        }
                    }
                });
                mSmartRefreshLayout.finishLoadMore();
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), JobDetailsActivity.class);
                intent.putExtra("job_title", datalist.get(position).getJob_title());
                intent.putExtra("job_pay", datalist.get(position).getJob_pay());
                intent.putExtra("job_time", datalist.get(position).getJob_time());
                intent.putExtra("job_type", datalist.get(position).getJob_type());
                intent.putExtra("job_company", datalist.get(position).getJob_company());
                intent.putExtra("job_phone", datalist.get(position).getJob_phone());
                intent.putExtra("job_address", datalist.get(position).getJob_address());
                intent.putExtra("job_describe", datalist.get(position).getJob_describe());
                intent.putExtra("job_people", datalist.get(position).getJob_people());
                intent.putExtra("job_logo", datalist.get(position).getJob_logo());
                intent.putExtra("object_id", datalist.get(position).getObjectId());
                startActivityForResult(intent, UPDATE_DATE_CODE);
            }
        });
    }

    /**
     * 头部内容
     */
    private void headPart() {
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.item_header_layout, null);
        mBanner = (MZBannerView) headView.findViewById(R.id.mBanner);
        sRecyclerView = (RecyclerView) headView.findViewById(R.id.sRecyclerView);
        rl_strategy = (RelativeLayout) headView.findViewById(R.id.rl_strategy);
        rl_strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), JobStrategyActivity.class));
            }
        });
        getBannerData();
        getStrategy();
        adapter.setHeaderView(headView);
    }

    /**
     * 获取兼职攻略
     */
    private void getStrategy() {
        BmobQuery<StrategyTable> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<StrategyTable>() {
            @Override
            public void done(List<StrategyTable> list, BmobException e) {
                if (e == null) {
                    strategyList.clear();
                    strategyList.addAll(list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    sRecyclerView.setLayoutManager(layoutManager);
                    sRecyclerView.setAdapter(sAdapter);
                } else {

                }
            }
        });
        sAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), WebDetailsActivity.class);
                intent.putExtra("url", strategyList.get(position).getStrategy_web_url());
                startActivity(intent);
            }
        });
    }

    /**
     * 获取轮播图后台数据
     */
    private void getBannerData() {
        BmobQuery<BannerBean> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<BannerBean>() {
            @Override
            public void done(final List<BannerBean> list, BmobException e) {
                if (e == null) {
                    // 设置数据
                    mBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
                        @Override
                        public BannerViewHolder createViewHolder() {
                            return new BannerViewHolder();
                        }
                    });
                    mBanner.setIndicatorVisible(false);
                    mBanner.setDelayedTime(3000);
                    mBanner.start();

                } else {
                    ToastUtils.setOkToast(getContext(), "请检查网络！");
                }
            }
        });

    }


    @OnClick({R.id.tv_location, R.id.ll_search})
    public void Onlick(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                Intent intent = new Intent(getContext(), CityPickerActivity.class);
                startActivityForResult(intent, UPDATE_DATE_CODE);
                break;
            case R.id.ll_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
        }
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
        if (requestCode == UPDATE_DATE_CODE) {
            switch (resultCode) {
                case RESULT_DATE_CODE:
                    getBannerData();
                    break;
                case RESULT_LOCATION_CODE:
                    if (data == null) {
                        return;
                    }
                    String cityname = data.getStringExtra("cityname");
                    tv_location.setText(cityname);
                    SharedPreferencesUtils.saveStringSharedPreferences(getContext(), "location", cityname);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }
}
