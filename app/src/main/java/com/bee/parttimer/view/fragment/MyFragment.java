package com.bee.parttimer.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseFragment;
import com.bee.parttimer.utils.ToastUtils;
import com.bee.parttimer.view.activity.UserDataActivity;
import com.bee.parttimer.widget.ItemView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends BaseFragment {

    @BindView(R.id.rl_userdata)
    RelativeLayout rl_userdata;
    @BindView(R.id.civ_userhead)
    CircleImageView civ_userhead;
    @BindView(R.id.itemview_item1)
    ItemView itemView1;
    @BindView(R.id.itemview_item2)
    ItemView itemView2;
    @BindView(R.id.itemview_item3)
    ItemView itemView3;
    @BindView(R.id.itemview_item4)
    ItemView itemView4;
    @BindView(R.id.itemview_item5)
    ItemView itemView5;
    @BindView(R.id.itemview_item6)
    ItemView itemView6;
    @BindView(R.id.itemview_item7)
    ItemView itemView7;
    @BindView(R.id.itemview_item8)
    ItemView itemView8;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        //加载头像
        Glide.with(getContext())
                .load("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=789661362,2109010345&fm=200&gp=0.jpg")
                .into(civ_userhead);
    }

    /**
     * ItemView的点击事件
     * @param view
     */
    @OnClick({R.id.itemview_item1,R.id.itemview_item2,R.id.itemview_item3,R.id.itemview_item4,R.id.itemview_item5,R.id.itemview_item6,R.id.itemview_item7,R.id.itemview_item8})
    public void ClickItemView(View view){
        switch (view.getId()){
            case R.id.itemview_item1:
                ToastUtils.setOkToast(getContext(),"点击有效1");
                break;
            case R.id.itemview_item2:
                ToastUtils.setOkToast(getContext(),"点击有效2");
                break;
            case R.id.itemview_item3:
                ToastUtils.setOkToast(getContext(),"点击有效3");
                break;
            case R.id.itemview_item4:
                ToastUtils.setOkToast(getContext(),"点击有效4");
                break;
            case R.id.itemview_item5:
                ToastUtils.setOkToast(getContext(),"点击有效5");
                break;
            case R.id.itemview_item6:
                ToastUtils.setOkToast(getContext(),"点击有效6");
                break;
            case R.id.itemview_item7:
                ToastUtils.setOkToast(getContext(),"点击有效7");
                break;
            case R.id.itemview_item8:
                ToastUtils.setOkToast(getContext(),"点击有效8");
                break;

                default:
                    break;
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.rl_userdata)
    public void click(View view){
        startActivity(new Intent(getContext(), UserDataActivity.class));
    }
}
