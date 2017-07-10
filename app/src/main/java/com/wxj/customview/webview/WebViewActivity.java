package com.wxj.customview.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.wxj.customview.R;
import com.wxj.customview.utils.LogUtils;
import com.wxj.customview.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-9-7.
 */
public class WebViewActivity extends Activity implements View.OnClickListener {

    public static final String ASSETS_PATH = "file:///android_asset/";

    @BindView(R.id.btn_open)
    Button btn_open;
    @BindView(R.id.btn_start_download)
    Button btn_start_download;
    @BindView(R.id.id_webview)
    WebView id_webview;
    //进度
    private int progress = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progress < 100) {
                progress += 1;
                btn_open.setText("当前进度是" + progress);



                id_webview.loadUrl("javascript:setDownloadProgress("+progress+","+id+",'"+type+"')");




                Message message = mHandler.obtainMessage();
                message.what = 1;
                mHandler.sendMessageDelayed(message, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        init();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void init() {
        id_webview.setWebContentsDebuggingEnabled(true);
        id_webview.getSettings().setJavaScriptEnabled(true);
        id_webview.getSettings().setDomStorageEnabled(true);

        id_webview.addJavascriptInterface(new JSInterface(), "NativeInterface");

        id_webview.setWebChromeClient(new MyWebChromeClient());
        id_webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        id_webview.loadUrl("http://redisserver1.chinacloudapp.cn:30003/#/home/index");
    }

    @OnClick({R.id.btn_open, R.id.btn_start_download})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_open:

                break;
            case R.id.btn_start_download:
                String text = btn_start_download.getText().toString();
                if (TextUtils.equals("开始下载", text)) {
                    mHandler.sendEmptyMessage(1);
                    btn_start_download.setText("暂停下载");
                } else if (TextUtils.equals("暂停下载", text)) {
                    mHandler.removeMessages(1);
                    btn_start_download.setText("开始下载");
                }

                break;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

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

    private int id;
    private String type;

    private class JSInterface {

        @JavascriptInterface
        public void download(String url,int versionCode,String packName,int id,String type){
            LogUtils.e("信息是"+url+"id="+id);
            type = type;
            id = id;
            ToastUtils.showToast(WebViewActivity.this,url+id+type);
        }

    }

}
