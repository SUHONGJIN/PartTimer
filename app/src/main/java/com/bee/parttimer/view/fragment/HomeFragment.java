package com.bee.parttimer.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseFragment;
import com.bee.parttimer.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.mBanner)
    Banner mBanner;

    private Unbinder unbander;
    private List<String> bannerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        //绑定的时候返回一个unbander对象
        unbander = ButterKnife.bind(this,view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        //图片地址集合
        bannerList=new ArrayList<>();
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541497492385&di=128d8fbb2d1a07f1a26149ca68540b59&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a9225995332f0000002129d42836.jpg%402o.jpg");
        bannerList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=147527940,74269358&fm=26&gp=0.jpg");
        bannerList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541497716885&di=6f19350f7712b24e765c603bdd88f73f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d1d257747cce0000012e7e28e6ef.jpg%402o.jpg");
        //图片装载机
        ImageLoader imageLoader=new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).asBitmap().into(imageView);  //加载网络图片
            }
        };
        mBanner.setImageLoader(imageLoader);   //设置图片装载机
        mBanner.setImages(bannerList);  //设置图片地址集合
        mBanner.setBannerAnimation(Transformer.Default); //设置轮播图加载的动画效果
        mBanner.setDelayTime(5000);   //设置加载间隔时间
        mBanner.setIndicatorGravity(BannerConfig.CENTER);  //设置指示器位置
        mBanner.start();  //开始执行

    }

    @Override
    public void initData() {
        //Toast.makeText(getContext(),"我重写了父类的方法Data()",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        unbander.unbind();
    }

}
