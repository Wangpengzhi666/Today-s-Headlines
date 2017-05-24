package com.headlines.wangpengzhi.todaysheadlines.view.iview;

/**
 * Created by qizepu on 2017/5/11.
 */

public interface IViewByF1_f1<T> extends IMvpView{

    //回掉主界面fragment1 listview的适配内容
    void callBackF1ListViewData(T t);

}
