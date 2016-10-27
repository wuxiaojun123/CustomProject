package com.wxj.customview.webview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.wxj.customview.R;
import com.wxj.customview.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-9-7.
 */
public class WebViewActivity extends Activity implements View.OnClickListener{

    public static final String ASSETS_PATH = "file:///android_asset/";

    @BindView(R.id.btn_open)
    Button btn_open;
    @BindView(R.id.id_webview)
    WebView id_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        id_webview.setWebChromeClient(new MyWebChromeClient());
        id_webview.setWebViewClient(new MyWebviewClient());
        id_webview.loadUrl(ASSETS_PATH+"test.html");
    }

    @OnClick({R.id.btn_open})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_open:

                break;

        }
    }

    private class MyWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            LogUtils.e("onProgressChanged");
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            LogUtils.e("onCreateWindow");
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

    }

    private class MyWebviewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }


}
