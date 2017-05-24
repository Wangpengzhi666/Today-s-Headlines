package com.headlines.wangpengzhi.todaysheadlines.model.utils;

import android.os.Handler;
import android.os.Message;



import org.xutils.common.Callback;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cj on 2017/5/10.
 */

public class HttpUtils {


    private static CallbackVideoData mcallbackVideoData ;


    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String json = (String) msg.obj;
                    mcallbackVideoData.callback(json);
                    break;

                default:
                    break;
            }
        }
    };


    public static void getdata(String uri,CallbackVideoData callbackVideoData) {
        mcallbackVideoData = callbackVideoData;

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = null;
                try {
                    string = response.body().string();
                    Message message = Message.obtain();
                    message.obj = string;
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                /*Gson gson = new Gson();
                T t = gson.fromJson(string, cla);
                callBackData.callBack(t);*/
            }

        });

    }





















//    public static <T>void getTestData(String url,HashMap<String,String> hashMap, final CallbackVideoData callbackVideoData, final Class<T> clazz) {
//        RequestParams requestParams = new RequestParams();
//        requestParams.setUri(url);
//        if (hashMap != null) {
//            Iterator<String> iterator = hashMap.keySet().iterator();
//            while (iterator.hasNext()){
//                String key = iterator.next();
//                String value = hashMap.get(key);
//                requestParams.addQueryStringParameter(key, value);
//            }
//        }
//       // requestParams.addQueryStringParameter("token","1234");
//        x.http().get(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//                Gson gson = new Gson();
//                T t = gson.fromJson(result,clazz);
//                callbackVideoData.callback(t);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }

    public interface CallbackVideoData<T>{
        void callback(T t);
    }
}
