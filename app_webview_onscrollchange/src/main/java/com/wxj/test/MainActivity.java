package com.wxj.test;

import android.graphics.Picture;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wxj.test.util.LogUtils;
import com.wxj.test.view.MySwipeRefreshLayout;
import com.wxj.test.view.MyWebView;

public class MainActivity extends AppCompatActivity {

	public static final String		TAG	= "MainActivity";

	private MyWebView				webView;

	private MySwipeRefreshLayout	swipeRefreshLayout;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		swipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.id_swiperefreshlayout);
		webView = (MyWebView) findViewById(R.id.id_webview);
		webView.swipeRefreshLayout = swipeRefreshLayout;
		// swipeRefreshLayout.setEnabled(false);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {

			@Override public void onPageFinished(WebView view, String url) {
				view.loadUrl("javascript:window.HTMLOUT.getContentHeight(document.getElementByTagName('html')");
				SwipeRefreshLayout.LayoutParams params = webView.getLayoutParams();
				params.height = 2000;
				webView.setLayoutParams(params);
				// webView.requestLayout();
				webView.invalidate();
				LogUtils.e("执行完毕");
			}
		});

		webView.setPictureListener(new WebView.PictureListener() {

			int previousHeight;

			@Override public void onNewPicture(WebView w, Picture p) {
				int height = w.getContentHeight();
				if (previousHeight == height) return;
				previousHeight = height;
				Log.d(TAG, "WebView height" + height);
			}
		});

		webView.loadUrl("http://www.baidu.com");

		webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

			@Override public void onScrollChanged() {
				LogUtils.e("webview的高度和内容高度是:" + webView.getHeight() + "==== " + webView.getContentHeight());
			}
		});
	}

}
