package com.headlines.wangpengzhi.todaysheadlines.application;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:53
 * <p>
 * 思路：
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
