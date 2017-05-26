package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.headlines.wangpengzhi.todaysheadlines.model.recycler.RecyclerBean;
import com.headlines.wangpengzhi.todaysheadlines.model.tab.TabBean;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.HttpUtils;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.ITabView;

import org.greenrobot.eventbus.EventBus;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/12  13:49
 * <p>
 * 思路：
 */


public class TabPresenter extends BasePresenter<ITabView> {
    String Url = "http://result.eolinker.com/gfGTLlHc049c6b450500b16971f52bd8e83f6b2fed305ab?uri=news";

    public <T> void getTabDataFromServer(Class<T> t, final int noget) {

       HttpUtils.getdata(Url, new HttpUtils.CallbackVideoData<String>() {
            @Override
            public void callback(String s) {
                Gson gson = new Gson();
                TabBean t = gson.fromJson(s, TabBean.class);

                if (noget == 0) {
                    getMvpView().callbackStr(t);
                }

//                if (getMvpView() != null) {
//                    getMvpView().callbackStr(t);
//                } else {
//                    Log.e("HomePresenter","请调用 attachView ");
//                }
            }
        });
    }

}
