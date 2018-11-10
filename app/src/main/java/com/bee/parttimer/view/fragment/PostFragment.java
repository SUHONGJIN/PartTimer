package com.bee.parttimer.view.fragment;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.bee.parttimer.R;
import com.bee.parttimer.base.BaseFragment;
import com.bee.parttimer.utils.ToastUtils;
import com.bee.parttimer.widget.WeiboDialogUtils;

import butterknife.BindView;

public class PostFragment extends BaseFragment {
    @BindView(R.id.float_btn)
    FloatingActionButton float_btn;

    private Dialog mWeiboDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_post;
    }

    @Override
    public void initView(View view) {
        float_btn=(FloatingActionButton)view.findViewById(R.id.float_btn);
        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
                mHandler.sendEmptyMessageDelayed(1, 3000);
            }
        });
    }

    @Override
    public void initData() {

    }
}
