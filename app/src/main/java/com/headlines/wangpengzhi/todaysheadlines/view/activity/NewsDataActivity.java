package com.headlines.wangpengzhi.todaysheadlines.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/15  13:34
 * <p>
 * 思路：
 */


public class NewsDataActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webView = new WebView(this);
        setContentView(webView);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        webView.loadUrl(url);

    }
}
