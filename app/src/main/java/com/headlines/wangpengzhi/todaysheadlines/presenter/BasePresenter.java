package com.headlines.wangpengzhi.todaysheadlines.presenter;


import com.headlines.wangpengzhi.todaysheadlines.view.iview.IMvpView;

/**
 * Created by cj on 2017/5/9.
 */

public class BasePresenter<T extends IMvpView> {

    private T mt;

    public void attachView(T t) {
        this.mt = t;
    }

    public T getMvpView() {
        return mt;
    }
}
