package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.widget.ImageView;

import com.google.gson.Gson;
import com.headlines.wangpengzhi.todaysheadlines.model.recycler.RecyclerBean;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.HttpUtils;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IRecyclerView;

import org.xutils.x;

import java.util.HashMap;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/20  10:21
 * <p>
 * 思路：
 */


public class RecyclerPresenter extends BasePresenter<IRecyclerView>{

    public void getListViewData() {

        String url = "http://result.eolinker.com/k2BaduF2a6caa275f395919a66ab1dfe4b584cc60685573?uri=gn";
        HashMap<String, String> hashMap = new HashMap<>();

        HttpUtils.getdata(url, new HttpUtils.CallbackVideoData<String>() {
            @Override
            public void callback(String s) {
                Gson gson = new Gson();
                RecyclerBean t = gson.fromJson(s, RecyclerBean.class);
                getMvpView().callBackF1ListViewData(t);
//
//                if (getMvpView() != null) {
//                    getMvpView().callbackStr(t);
//                } else {
//                    Log.e("HomePresenter","请调用 attachView ");
//                }
            }
        });
    }


    public void setImageData(ImageView imageView, String url){
        x.image().bind(imageView,url);
    }
}
