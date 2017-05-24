package com.headlines.wangpengzhi.todaysheadlines.view.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/15  14:47
 * <p>
 * 思路：
 */


public class BaseUiListener implements IUiListener {
    private Context context;
    private UserInfo mUserInfo;
    private Tencent mTencent;
    private TextView textView;
    private ImageView imageView;

    public BaseUiListener(Context context, UserInfo mUserInfo, Tencent mTencent, TextView textView, ImageView imageView) {
        this.context = context;
        this.mUserInfo = mUserInfo;
        this.mTencent = mTencent;
        this.textView = textView;
        this.imageView = imageView;
    }

    public BaseUiListener() {
    }

    @Override
    public void onComplete(Object response) {
        Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
        Log.e("tag", "response:" + response);
        JSONObject obj = (JSONObject) response;
        try {
            String openID = obj.getString("openid");
            String accessToken = obj.getString("access_token");
            String expires = obj.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(accessToken,expires);
            QQToken qqToken = mTencent.getQQToken();
            mUserInfo = new UserInfo(context,qqToken);
            mUserInfo.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object response) {

                    JSONObject object= (JSONObject) response;

                    try {
                        String figureurl_1 = object.getString("figureurl_qq_2");
                        String nickname = object.getString("nickname");
                        textView.setText(nickname);
                        x.image().bind(imageView,figureurl_1);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.e("tag","登录成功"+response.toString());
                }

                @Override
                public void onError(UiError uiError) {
                    Log.e("tag","登录失败"+uiError.toString());
                }

                @Override
                public void onCancel() {
                    Log.e("tag","登录取消");

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();

    }
}
