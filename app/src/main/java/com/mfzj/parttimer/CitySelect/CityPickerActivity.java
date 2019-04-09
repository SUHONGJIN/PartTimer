package com.mfzj.parttimer.CitySelect;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.mfzj.parttimer.CitySelect.adapter.CityListAdapter;
import com.mfzj.parttimer.CitySelect.bean.AreasBean;
import com.mfzj.parttimer.CitySelect.bean.City;
import com.mfzj.parttimer.CitySelect.bean.CityPickerBean;
import com.mfzj.parttimer.CitySelect.bean.LocateState;
import com.mfzj.parttimer.CitySelect.util.GsonUtil;
import com.mfzj.parttimer.CitySelect.util.PinyinUtils;
import com.mfzj.parttimer.CitySelect.util.ReadAssetsFileUtil;
import com.mfzj.parttimer.CitySelect.widget.SideLetterBar;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.OnClick;

public class CityPickerActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    private ListView mListView;
    private SideLetterBar mLetterBar;
    private CityListAdapter mCityAdapter;
    public LocationClient mLocationClient = null;

    @Override
    public int getContentViewResId() {
        return R.layout.cp_activity_city_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        tv_title.setText("选择城市");
        mListView = findViewById(R.id.listview_all_city);
        TextView overlay = findViewById(R.id.tv_letter_overlay);
        mLetterBar = findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });
        mCityAdapter = new CityListAdapter(this);
        mListView.setAdapter(mCityAdapter);
        //请求定位
        requestLocation();
    }

    @Override
    public void initData() {
        getCityData();

        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {//选择城市
                Log.i("tag1","点击1。。");
                Intent intentTemp = new Intent();
                intentTemp.putExtra("cityname",name);
                setResult(200,intentTemp);
                finish();
            }

            @Override
            public void onLocateClick() {//点击定位按钮
                Log.i("tag1","点击2。。");
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                requestLocation();
            }
        });
    }


    public void getCityData() {
        String json = ReadAssetsFileUtil.getJson(this, "city.json");
        CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
        HashSet<City> citys = new HashSet<>();
        for (AreasBean areasBean : bean.data.areas) {
            String name = areasBean.name.replace("　", "");
            citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name), areasBean.is_hot == 1));
            for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
                citys.add(new City(childrenBeanX.id, childrenBeanX.name, PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
            }
        }
        //set转换list
        ArrayList<City> cities = new ArrayList<>(citys);
        //按照字母排序
        Collections.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City city, City t1) {
                return city.getPinyin().compareTo(t1.getPinyin());
            }
        });
        mCityAdapter.setData(cities);
    }

    /**
     * 开始定位的方法
     */
    public void requestLocation(){

        MyLocationListener myListener = new MyLocationListener();

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    /**
     * 获取定位结果
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            String cityname = location.getCity();    //获取城市
             Log.i("tag1",cityname+"===================");
            if (cityname!=null){
                mCityAdapter.updateLocateState(LocateState.SUCCESS, cityname);
            }else {
                mCityAdapter.updateLocateState(LocateState.FAILED, null);
            }


        }
    }

    @OnClick(R.id.iv_back)
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁定位客户端
        if (mLocationClient!=null){
            mLocationClient.stop();
        }

    }
}
