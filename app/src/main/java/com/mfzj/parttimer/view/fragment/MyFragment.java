package com.mfzj.parttimer.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.LoginActivity;
import com.mfzj.parttimer.view.activity.SettingActivity;
import com.mfzj.parttimer.view.activity.UserDataActivity;
import com.mfzj.parttimer.widget.ItemMenu;
import com.mfzj.parttimer.widget.ItemView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
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
    @BindView(R.id.itemmenu_item1)
    ItemMenu itemMenu_item1;
    @BindView(R.id.itemmenu_item2)
    ItemMenu itemMenu_item2;
    @BindView(R.id.itemmenu_item3)
    ItemMenu itemMenu_item3;
    @BindView(R.id.itemmenu_item4)
    ItemMenu itemMenu_item4;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        //加载头像
        Glide.with(getContext())
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542425119&di=37abff6014019236f1d1fadc954913ea&imgtype=jpg&er=1&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F692eb742gw1ewi4mr5bgqj20h00h0dik.jpg")
                .into(civ_userhead);
    }

    /**
     * ItemMenu的点击事件
     * @param view
     */
    @OnClick({R.id.itemmenu_item1,R.id.itemmenu_item2,R.id.itemmenu_item3,R.id.itemmenu_item4})
    public void ClickItemMenu(View view){
        switch(view.getId()){
            case R.id.itemmenu_item1:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemmenu_item2:
                ToastUtils.setOkToast(getContext(),"menu点击有效2");
                break;
            case R.id.itemmenu_item3:
                ToastUtils.setOkToast(getContext(),"menu点击有效3");
                break;
            case R.id.itemmenu_item4:
                ToastUtils.setOkToast(getContext(),"menu点击有效4");
                break;
                default:break;
        }
    }

    /**
     * ItemView的点击事件
     * @param view
     */
    @OnClick({R.id.rl_userdata,R.id.itemview_item1,R.id.itemview_item2,R.id.itemview_item3,R.id.itemview_item4,R.id.itemview_item5,R.id.itemview_item6,R.id.itemview_item7,R.id.itemview_item8})
    public void ClickItemView(View view){
        switch (view.getId()){
            case R.id.rl_userdata:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item1:
                startActivity(new Intent(getContext(), UserDataActivity.class));
                break;
            case R.id.itemview_item2:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item3:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item4:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item5:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item6:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item7:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.itemview_item8:
                //跳转到设置界面
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
                default:
                    break;
        }
    }

    @Override
    public void initData() {

    }

}
