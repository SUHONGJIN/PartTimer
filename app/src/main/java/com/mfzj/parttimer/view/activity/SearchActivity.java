package com.mfzj.parttimer.view.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;
import com.mfzj.parttimer.utils.ToastUtils;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.mSearchView)
    SearchView mSearchView;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ToastUtils.setOkToast(SearchActivity.this,"没有找到关于"+s+"的内容");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
