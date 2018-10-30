package com.example.test.uwa_parking;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by zhe184324 on 2018/9/14.
 */

public class map extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview;
        webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        try
        {
            webview.loadUrl("http://106.14.213.85:3000/");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        setContentView(webview);
    }
}