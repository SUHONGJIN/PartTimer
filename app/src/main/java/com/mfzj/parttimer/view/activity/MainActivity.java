package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.view.fragment.HomeFragment;
import com.mfzj.parttimer.view.fragment.MessageFragment;
import com.mfzj.parttimer.view.fragment.MyFragment;
import com.mfzj.parttimer.view.fragment.PostFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager fragmentManager;
    @BindView(R.id.rb_home)
    RadioButton rb_home;
    @BindView(R.id.rb_post)
    RadioButton rb_post;
    @BindView(R.id.rb_message)
    RadioButton rb_message;
    @BindView(R.id.rb_my)
    RadioButton rb_my;
    @BindView(R.id.rg_bottom)
    RadioGroup rg_bottom;
    @BindView(R.id.fl_content)
    FrameLayout fl_content;
    private Fragment fragmentHome, fragmentPost, fragmentMessage, fragmentMy;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //注册监听
        rg_bottom.setOnCheckedChangeListener(this);
        //初始化默认第一个RadioButton为选中状态
        rb_home.setChecked(true);
    }

    @Override
    public void initData() {}

    /**
     * 切换fragment
     * @param radioGroup
     * @param i
     */

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (i) {
            case R.id.rb_home:
                if (rb_home != null) {
                    fragmentHome = new HomeFragment();
                    transaction.add(R.id.fl_content, fragmentHome);
                } else {
                    transaction.show(fragmentHome);
                }
                break;
            case R.id.rb_post:
                if (rb_post != null) {
                    fragmentPost = new PostFragment();
                    transaction.add(R.id.fl_content, fragmentPost);
                } else {
                    transaction.show(fragmentPost);
                }
                break;
            case R.id.rb_message:
                if (rb_message != null) {
                    fragmentMessage = new MessageFragment();
                    transaction.add(R.id.fl_content, fragmentMessage);
                } else {
                    transaction.show(fragmentMessage);
                }
                break;
            case R.id.rb_my:
                if (rb_my != null) {
                    fragmentMy = new MyFragment();
                    transaction.add(R.id.fl_content, fragmentMy);
                } else {
                    transaction.show(fragmentMy);
                }
                break;
        }
        transaction.commit(); //事务提交
    }


    /**
     * 隐藏fragment
     *
     * @param transaction
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (fragmentPost != null) {
            transaction.hide(fragmentPost);
        }
        if (fragmentMessage != null) {
            transaction.hide(fragmentMessage);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }
    }

}