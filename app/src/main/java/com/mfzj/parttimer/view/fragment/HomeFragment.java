package com.mfzj.parttimer.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.utils.SharedPreferencesUtils;
import com.mfzj.parttimer.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.mBanner)
    Banner mBanner;

    @BindView(R.id.tv_location)
    TextView tv_location;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private List<String> bannerList;
    //创建权限集合
    private List<String> permissionList=new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        //图片地址集合
        bannerList=new ArrayList<>();
        
        bannerList.add("http://image-1256444076.picgz.myqcloud.com/ad/app_ad_ios.png");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554358768819&di=9c84301d6f2b99603e4a748b3347a6e6&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F17%2F09%2F26%2F924bec50d30abc11d9b9ffec2206537f.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554358768818&di=f2f7ab143f2de6d825f922b17021bdd0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F014cb65771ef600000018c1b8608be.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554358768617&di=00cb15d2f4fcba21be84f6446708419a&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F05%2F24%2F99%2F8059f5b19446a1f.jpg");

        //图片装载机
        ImageLoader imageLoader=new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).asBitmap().into(imageView);  //加载网络图片
            }
        };
        mBanner.setImageLoader(imageLoader);   //设置图片装载机
        mBanner.setImages(bannerList);  //设置图片地址集合
        mBanner.setBannerAnimation(Transformer.Accordion); //设置轮播图加载的动画效果
        mBanner.setDelayTime(5000);   //设置加载间隔时间
        mBanner.setIndicatorGravity(BannerConfig.CENTER);  //设置指示器位置
        mBanner.start();  //开始执行

        //配置定位SDK参数
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

        //检查权限是否获取
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!permissionList.isEmpty()){
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(),permission,1);
        }else{
            //开始定位
            requestLocation();
        }

    }

    /**
     * 初始化一些定位配置
     */
    @Override
    public void initData() {

    }

    /**
     * 开始定位的方法
     */
    public void requestLocation(){
        mLocationClient.start();
    }

    /**
     * 获取定位结果
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){

            String cityname = location.getCity();    //获取城市
            //获取到定位信息保存到本地储存
            if (cityname != null) {
                SharedPreferencesUtils.saveStringSharedPreferences(getContext(),cityname);
            }
            //获取已保存的定位信息
            String city=SharedPreferencesUtils.getStringSharedPreferences(getContext());
            if (city!=null){
                tv_location.setText(city);
            }else{
                tv_location.setText("定位中...");
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int results : grantResults){
                        if (results != PackageManager.PERMISSION_GRANTED){
                            ToastUtils.setOkToast(getContext(),"为了能正常使用APP，建议打开相应的权限~");
                        }else {
                            requestLocation();
                        }
                    }
                }else{
                    ToastUtils.setOkToast(getContext(),"未知权限错误！");
                }
                break;
            default:break;
        }
    }
}
