package com.headlines.wangpengzhi.todaysheadlines.view.iview;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/12  13:47
 * <p>
 * 思路：
 */


public interface ITabView<T> extends IMvpView{
    void callbackStr(T t);
    void callbackErr(String errMsg,int errCode);
}
