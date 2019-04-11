package com.mfzj.parttimer.view.activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.mfzj.parttimer.R;
import com.mfzj.parttimer.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WebDetailsActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_share)
    ImageView iv_share;
    @BindView(R.id.mWebView)
    WebView mWebView;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_web_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_title.setText("详情页面");
        loadWeb();
    }

    @Override
    public void initData() {

    }
    private void loadWeb() {
        String url = (String) getIntent().getExtras().get("url");
        if (url != null) {
            mWebView.loadUrl(url);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //加载开始
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //加载结束
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //加载页面的服务器出错时调用
                mWebView.setVisibility(View.GONE);
                //ll_error_state.setVisibility(View.VISIBLE);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    @OnClick({R.id.iv_back,R.id.iv_share})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_share:

                break;
            default:
                break;
        }
    }

}
