package com.mfzj.parttimer.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.bean.Advertising;
import com.mfzj.parttimer.view.activity.WebDetailsActivity;
import com.zhouwei.mzbanner.holder.MZViewHolder;

public class AdvertisingBannerViewHolder implements MZViewHolder<Advertising> {
    private ImageView aImage;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_advertising_banner,null);
        aImage = (ImageView) view.findViewById(R.id.aImage);
        return view;
    }

    @Override
    public void onBind(final Context context, int i, final Advertising Advertising) {

        Glide.with(context).load(Advertising.getaBannerPic()).placeholder(R.drawable.banner_default).error(R.drawable.banner_default).into(aImage);

        aImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebDetailsActivity.class);
                intent.putExtra("url", Advertising.getaBannerWeb());
                context.startActivity(intent);
            }
        });
    }

}