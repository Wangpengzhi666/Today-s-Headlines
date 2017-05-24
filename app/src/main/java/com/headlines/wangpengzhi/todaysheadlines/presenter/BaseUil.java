package com.headlines.wangpengzhi.todaysheadlines.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/5/13.
 */

public class BaseUil implements IUiListener{

    private UserInfo mUserInfo;
    private Tencent mTencent;
    private Context context;
    private ImageView im2;
    private TextView te1;
    private LinearLayout lin;
    private RelativeLayout rel;

    public BaseUil() {
    }

    public BaseUil(UserInfo mUserInfo, Tencent mTencent, Context context, ImageView im2, TextView te1, LinearLayout lin, RelativeLayout rel) {
        this.mUserInfo = mUserInfo;
        this.mTencent = mTencent;
        this.context = context;
        this.im2 = im2;
        this.te1 = te1;
        this.lin = lin;
        this.rel = rel;
    }

    @Override
    public void onComplete(Object response) {
//            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
//            Log.e(TAG, "response:" + response);

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
                        String figureurl_2 = object.getString("figureurl_2");
                        String nickname = object.getString("nickname");
                        x.image().bind(im2,figureurl_2);
                        te1.setText(nickname);

                                rel.setVisibility(View.GONE);
                                lin.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG,"登录成功"+response.toString());
                }

                @Override
                public void onError(UiError uiError) {
                    Log.e(TAG,"登录失败"+uiError.toString());
                }

                @Override
                public void onCancel() {
                    Log.e(TAG,"登录取消");

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
