package com.xuyinshuang.webview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText extWangZhi;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        extWangZhi = (EditText) findViewById(R.id.txtWangZhi);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        webView = (WebView) findViewById(R.id.webView);

        //执行JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);


        webViewInit();
        webViewSettingInit();

    }

    private void txt() {
        String wangzhi = extWangZhi.getText().toString().trim();
        if (TextUtils.isEmpty(wangzhi)) {
            Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
        }
        //设置需要显示的网页
        webView.loadUrl(wangzhi);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHouTui:
                houtui(view);
                break;
            case R.id.btnQianJin:
                advance(view);
                break;
            case R.id.btnShuaXin:
                shuaxin(view);
                break;
            case R.id.btnSkip:
                txt();
                break;
        }

    }

    //使用webView进行一些初始化操作
    private void webViewInit() {

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //触摸焦点起作用,如果不设置则在点击网页文本输入框的时候不能弹出软键盘及一些点击事件
        webView.requestFocus();
        //该事件是指UI界面发生改变时进行监听
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //通过代码让ProgressBar显示出来
                progressBar.setVisibility(View.VISIBLE);
                //对ProgressBar设置加载进度的参数
                progressBar.setProgress(newProgress);
                if (newProgress==100){
                    //如果ProgressBar加载到100,就让他隐藏
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }


    //对网页空间进行一系列的初始化设置
    private void webViewSettingInit() {
        //得到一个WebView的设置对象,
        WebSettings setting = webView.getSettings();
        //setJavaScriptEnabled:使webView可以支持JavaScript
        setting.setJavaScriptEnabled(true);
        //setSupportZoom:使WebView允许网页缩放,记住这个方法前,要有让WebView支持JavaScript的设定,否则会不起作用
        setting.setSupportZoom(true);
        //setBlockNetworkImage: 使WebView只加载文字,而不加载图片,为用户省流量
        setting.setBlockNetworkImage(true);

    }

    //通过点击事件,对网页进行前进操作
    public void advance(View view) {
        if (webView.canGoForward()) {
            webView.goForward();  //可以前进就进行前进操作
        }else {
            Toast.makeText(MainActivity.this, "前方已无路!!", Toast.LENGTH_SHORT).show();
        }
    }

    //通过点击事件,对网页进行后退操作
    public void houtui(View view) {
        if (webView.canGoBack()) {
            webView.goBack();
        }else {
            Toast.makeText(MainActivity.this, "已经退到了尽头!!", Toast.LENGTH_SHORT).show();
        }
    }

    //通过点击事件,实现刷新
    private void shuaxin(View view) {
        //重新加载
        webView.reload();
    }

    //设置回退
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//表示返回WebView的上一个页面
            return true;
        }
        return false;
    }
}
