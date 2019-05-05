package com.mfzj.parttimer.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mfzj.parttimer.R;
import com.mfzj.parttimer.adapter.AdvertisingBannerViewHolder;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.bean.Advertising;
import com.mfzj.parttimer.bean.User;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.AboutActivity;
import com.mfzj.parttimer.view.activity.AuthenticationActivity;
import com.mfzj.parttimer.view.activity.CollectActivity;
import com.mfzj.parttimer.view.activity.FeedBackActivity;
import com.mfzj.parttimer.view.activity.LoginActivity;
import com.mfzj.parttimer.view.activity.MyDataActivity;
import com.mfzj.parttimer.view.activity.MyResumeActivity;
import com.mfzj.parttimer.view.activity.MyStateActivity;
import com.mfzj.parttimer.view.activity.WalletActivity;
import com.mfzj.parttimer.view.activity.postjob.MyPostPartTimerActivity;
import com.mfzj.parttimer.view.activity.setting.SettingActivity;
import com.mfzj.parttimer.widget.ItemMenu;
import com.mfzj.parttimer.widget.ItemView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_motto)
    TextView tv_motto;
    @BindView(R.id.iv_vip)
    ImageView iv_vip;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.aBanner)
    MZBannerView aBanner;
    private static  final int INFO_CODE=1;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView(View view) {
        getAdvertising();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                getAdvertising();
                refreshlayout.finishRefresh(1000);
            }
        });

    }

    /**
     * 获取广告轮播图后台数据
     */
    private void getAdvertising() {
        BmobQuery<Advertising> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Advertising>() {
            @Override
            public void done(final List<Advertising> list, BmobException e) {
                if (e == null) {
                    // 设置数据
                    aBanner.setPages(list, new MZHolderCreator<AdvertisingBannerViewHolder>() {
                        @Override
                        public AdvertisingBannerViewHolder createViewHolder() {
                            return new AdvertisingBannerViewHolder();
                        }
                    });
                    aBanner.setIndicatorVisible(false);
                    aBanner.setDelayedTime(3000);
                    aBanner.start();
                } else {
                    ToastUtils.setOkToast(getContext(), "请检查网络！");
                }

            }
        });

    }

    /**
     * ItemMenu的点击事件
     * @param view
     */
    @OnClick({R.id.itemmenu_item1,R.id.itemmenu_item2,R.id.itemmenu_item3,R.id.itemmenu_item4})
    public void ClickItemMenu(View view){
        switch(view.getId()){
            case R.id.itemmenu_item1:
                if (BmobUser.isLogin()) {
                    Intent intent=new Intent(getContext(),MyStateActivity.class);
                    intent.putExtra("tag",0);
                    startActivity(intent);
                }else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }

                break;
            case R.id.itemmenu_item2:
                if (BmobUser.isLogin()) {
                    Intent intent=new Intent(getContext(),MyStateActivity.class);
                    intent.putExtra("tag",1);
                    startActivity(intent);
                }else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
                break;
            case R.id.itemmenu_item3:
                if (BmobUser.isLogin()) {
                    Intent intent=new Intent(getContext(),MyStateActivity.class);
                    intent.putExtra("tag",2);
                    startActivity(intent);
                }else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
                break;
            case R.id.itemmenu_item4:
                if (BmobUser.isLogin()) {
                    Intent intent=new Intent(getContext(),MyStateActivity.class);
                    intent.putExtra("tag",3);
                    startActivity(intent);
                }else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(),LoginActivity.class));
                }
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
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    Intent intent = new Intent(getContext(), MyDataActivity.class);
                    startActivityForResult(intent,INFO_CODE);
                } else {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item1:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), MyResumeActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item2:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), CollectActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item3:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), AuthenticationActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item4:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), WalletActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item5:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), FeedBackActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            case R.id.itemview_item6:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.itemview_item7:
                //判断用户是否登录
                if (BmobUser.isLogin()) {
                    startActivity(new Intent(getContext(), MyPostPartTimerActivity.class));
                } else {
                    ToastUtils.setOkToast(getContext(),"登录体验更多内容~");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

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
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            //加载头像
            if (user.getAvatar() != null){
                Glide.with(getContext())
                        .load(user.getAvatar())
                        .into(civ_userhead);
            }
            //用户名
            tv_username.setText(user.getUsername());
            if (user.getNick()!=null){
                tv_username.setText(user.getNick());
            }
            //用户座右铭
            tv_motto.setText("没有签名，哪来的个性。");
            if (user.getMotto()!=null){
                tv_motto.setText(user.getMotto());
            }
            //用户身份标识
            //iv_vip.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==INFO_CODE){
            //刷新用户信息
            User user = BmobUser.getCurrentUser(User.class);
            //加载头像
            if (user.getAvatar() != null){
                Glide.with(getContext())
                        .load(user.getAvatar())
                        .into(civ_userhead);
            }
            //获取用户名
            if (user.getNick() == null && user.getUsername() != null) {
                tv_username.setText(user.getUsername());
            } else if (user.getNick() != null) {
                tv_username.setText(user.getNick());
            }
            //获取座右铭
            if (user.getMotto() != null) {
                tv_motto.setText(user.getMotto());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        aBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        aBanner.start();//开始轮播
    }

}
