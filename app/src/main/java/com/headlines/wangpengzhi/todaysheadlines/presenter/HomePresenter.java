package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.HttpUtils;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IHomeView;

import org.xutils.x;
import java.util.HashMap;

/**
 * Created by cj on 2017/5/9.
 */

public class HomePresenter extends BasePresenter<IHomeView> {
    private String url = "http://api.expoon.com/AppNews/getNewsList/type/2/p/1";
    private HashMap<String, String> hashMap = new HashMap<>();


    public void getImageFormServer(ImageView imageView, String url) {
        x.image().bind(imageView, url);
    }

    public <T> void getHomeDataFromServer(final Class<T> cla) {
        HttpUtils.getdata(url, new HttpUtils.CallbackVideoData<String>() {
            @Override
            public void callback(String s) {
                Gson gson = new Gson();
                T t = gson.fromJson(s, cla);
                getMvpView().callbackStr(t);
//
//                if (getMvpView() != null) {
//                    getMvpView().callbackStr(t);
//                } else {
//                    Log.e("HomePresenter","请调用 attachView ");
//                }
            }
        });
    }
}
