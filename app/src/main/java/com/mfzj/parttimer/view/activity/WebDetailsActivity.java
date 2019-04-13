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
import cn.sharesdk.onekeyshare.OnekeyShare;

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
                showShare();
                break;
            default:
                break;
        }
    }

    private void showShare() {

        String url = (String) getIntent().getExtras().get("url");

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("蜜蜂兼职");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我发现了一个有趣的内容，快来蜜蜂兼职看看吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我发现了一个有趣的内容，快来蜜蜂兼职看看吧！");
        // 启动分享GUI
        oks.show(this);
    }
}
