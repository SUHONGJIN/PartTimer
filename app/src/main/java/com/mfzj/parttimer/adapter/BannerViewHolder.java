package com.mfzj.parttimer.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.BannerBean;
import com.mfzj.parttimer.view.activity.WebDetailsActivity;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class BannerViewHolder implements MZViewHolder<BannerBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner,null);
        mImageView = (ImageView) view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(final Context context, int i, final BannerBean bannerBean) {

        Glide.with(context).load(bannerBean.getBanner_image_url()).placeholder(R.drawable.banner_default).error(R.drawable.banner_default).into(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebDetailsActivity.class);
                intent.putExtra("url", bannerBean.getBanner_web_url());
                context.startActivity(intent);
            }
        });
    }

}