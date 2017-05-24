package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.widget.ImageView;

import com.google.gson.Gson;
import com.headlines.wangpengzhi.todaysheadlines.model.frg01_xlist.Frg_01_xlistBean;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.HttpUtils;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IViewByF1_f1;

import org.xutils.x;

import java.util.HashMap;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/15  11:09
 * <p>
 * 思路：
 */


public class Frg01_XListViewPresenter extends BasePresenter<IViewByF1_f1>{

    public <T>void getListViewData(final Class<T> tClass, String urll) {

        String url = "http://result.eolinker.com/k2BaduF2a6caa275f395919a66ab1dfe4b584cc60685573?uri=" + urll;
        HashMap<String, String> hashMap = new HashMap<>();
        {
            HttpUtils.getdata(url, new HttpUtils.CallbackVideoData<String>() {
                @Override
                public void callback(String s) {
                    Gson gson = new Gson();
                    T t = gson.fromJson(s, tClass);
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
    }

    public void setImageData(ImageView imageView, String url){
        x.image().bind(imageView,url);
    }


}
