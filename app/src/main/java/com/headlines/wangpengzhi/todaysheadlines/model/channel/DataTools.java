package com.headlines.wangpengzhi.todaysheadlines.model.channel;

import android.content.Context;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/26  09:52
 * <p>
 * 思路：
 */


public class DataTools {

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
