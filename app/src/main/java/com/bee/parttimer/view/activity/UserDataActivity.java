package com.bee.parttimer.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseActivity;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UserDataActivity extends BaseActivity {

    @BindView(R.id.mImage)
    ImageView mImage;
    @BindView(R.id.civ_head)
    CircleImageView civ_head;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_user_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        Glide.with(UserDataActivity.this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542425119&di=37abff6014019236f1d1fadc954913ea&imgtype=jpg&er=1&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F692eb742gw1ewi4mr5bgqj20h00h0dik.jpg")
                .bitmapTransform(new BlurTransformation(UserDataActivity.this,8,5))
                .error(R.drawable.bg_guide1)
                .into(mImage);
        Glide.with(UserDataActivity.this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542425119&di=37abff6014019236f1d1fadc954913ea&imgtype=jpg&er=1&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F692eb742gw1ewi4mr5bgqj20h00h0dik.jpg")
                .error(R.drawable.bg_guide1)
                .into(civ_head);
    }

    @Override
    public void initData() {

    }

}
