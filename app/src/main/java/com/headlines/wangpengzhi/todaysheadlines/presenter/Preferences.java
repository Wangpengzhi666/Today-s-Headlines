package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/14  11:34
 * <p>
 * 思路：
 */


public class Preferences {

    private static final String shared_name="user_guide";
    private static SharedPreferences sp;

    public static String getString(Context context, String key,
                                   String defaultValues) {
        SharedPreferences sp = context.getSharedPreferences(shared_name,
                context.MODE_PRIVATE);
        return sp.getString(key, defaultValues);
    }

    public static void setString(Context context, String key, String Values) {
        SharedPreferences sp = context.getSharedPreferences(shared_name,
                context.MODE_PRIVATE);
        sp.edit().putString(key, Values).commit();
    }
}
