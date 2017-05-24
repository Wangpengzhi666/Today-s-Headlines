package com.headlines.wangpengzhi.todaysheadlines.view.iview;

import com.headlines.wangpengzhi.todaysheadlines.model.recycler.RecyclerBean;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/21  20:27
 * <p>
 * 思路：
 */


public interface IRecyclerView extends IMvpView{
    void callBackF1ListViewData(RecyclerBean recyclerBean);
}
