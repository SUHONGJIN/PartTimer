package com.mfzj.parttimer.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseFragment;
import com.mfzj.parttimer.utils.ToastUtils;
import com.mfzj.parttimer.view.activity.SystemMessageActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * Created by SuHongJin on 2018/10/14.
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.ll_call_qq)
    LinearLayout ll_call_qq;
    @BindView(R.id.ll_system_message)
    LinearLayout ll_system_message;

    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initView(View view) {
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSmartRefreshLayout.finishRefresh(1000);
            }
        });
        ll_system_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SystemMessageActivity.class));
            }
        });
        ll_call_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String urlQQ = "mqqwpa://im/chat?chat_type=wpa&uin=166165189";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlQQ)));
                }catch (Exception e){
                    ToastUtils.setOkToast(getContext(),"您还没有安装QQ聊天呢~");
                }

            }
        });
    }

    @Override
    public void initData() {

    }
}
