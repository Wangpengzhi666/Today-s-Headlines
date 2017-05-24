package com.headlines.wangpengzhi.todaysheadlines.model.utils;

import android.content.Context;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.presenter.Preferences;
import com.headlines.wangpengzhi.todaysheadlines.view.activity.MainActivity;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/14  11:36
 * <p>
 * 思路：
 */


public class UiUtils {


    //获取主题
    public static int getAppTheme(Context ctx) {
        String value = Preferences.getString(ctx, "activity_theme", "1");
        switch (Integer.valueOf(value)) {
            case 1:
                return R.style.AppTheme;//白色主题
            case 2:
                return R.style.AppTheme_Black;
            default:
                return R.style.AppTheme;//默认白色
        }
    }


    //切换主题
    //当然也可以使用资源ID来进行标记
    public static void switchAppTheme( Context ctx){
        String value = Preferences.getString(ctx, "activity_theme", "1");
        switch (Integer.valueOf(value)){
            case 1:
                Preferences.setString(ctx, "activity_theme", "2");
                break;
            case 2:
                Preferences.setString(ctx, "activity_theme", "1");
                break;
            default:
                Preferences.setString(ctx, "activity_theme", "1");
                break;
        }
    }
}
