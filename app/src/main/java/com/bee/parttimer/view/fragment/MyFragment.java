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
import com.bee.parttimer.view.activity.UserDataActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyFragment extends Fragment {
    @BindView(R.id.rl_userdata)
    RelativeLayout rl_userdata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.rl_userdata)
    public void click(View view){
        startActivity(new Intent(getContext(), UserDataActivity.class));
    }
}
