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
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=789661362,2109010345&fm=200&gp=0.jpg")
                .bitmapTransform(new BlurTransformation(UserDataActivity.this,20,2))
                .error(R.drawable.bg_guide1)
                .into(mImage);
        Glide.with(UserDataActivity.this)
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=789661362,2109010345&fm=200&gp=0.jpg")
                .error(R.drawable.bg_guide1)
                .into(civ_head);
    }

    @Override
    public void initData() {

    }

}
