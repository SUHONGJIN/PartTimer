package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
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
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541873145266&di=9ded1d2d10f384359e485efb0d62e2f3&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201504%2F24%2F20150424H3411_U2jRc.jpeg")
                .bitmapTransform(new BlurTransformation(UserDataActivity.this,8,5))
                .error(R.drawable.bg_guide1)
                .into(mImage);
        Glide.with(UserDataActivity.this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541873145266&di=9ded1d2d10f384359e485efb0d62e2f3&imgtype=0&src=http%3A%2F%2Fimg5q.duitang.com%2Fuploads%2Fitem%2F201504%2F24%2F20150424H3411_U2jRc.jpeg")
                .error(R.drawable.bg_guide1)
                .into(civ_head);
    }

    @Override
    public void initData() {

    }

}
