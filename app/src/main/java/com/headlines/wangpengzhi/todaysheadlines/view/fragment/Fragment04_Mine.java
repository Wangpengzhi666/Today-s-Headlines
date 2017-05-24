package com.headlines.wangpengzhi.todaysheadlines.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.UiUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:32
 * <p>
 * 思路：
 */

public class Fragment04_Mine extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1106162312";

    public static boolean rdb_yj = false;

    private Tencent mTencent;

    private UserInfo mUserInfo;
    private LinearLayout night_ll;
    private BaseUiListener mIUiListener;
    private ImageButton yj;
    private LinearLayout inc;
    private ImageView image_avatar;
    private TextView text_avatar;
    private Button phone;

    //    private BaseUiListener mIUiListener;
//    private UserInfo mUserInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_04_mine,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { //sputils是對SharedPreferences的封裝，代碼就不上了，大家理解意思就行了

        super.onActivityCreated(savedInstanceState);

        yj = (ImageButton) getView().findViewById(R.id.yj);
        image_avatar = (ImageView) getView().findViewById(R.id.image_Avatar);
        text_avatar = (TextView) getView().findViewById(R.id.text_Avatar);

        ImageButton qq= (ImageButton) getActivity().findViewById(R.id.qq);
        ImageButton phone= (ImageButton) getActivity().findViewById(R.id.phone);


        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());


        qq.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                mIUiListener = new BaseUiListener(getActivity(),mUserInfo,mTencent,text_avatar,image_avatar);
                //all表示获取所有权限
                mTencent.login(getActivity(),"all", mIUiListener);

                getView().findViewById(R.id.inc_not_log).setVisibility(View.GONE);
                getView().findViewById(R.id.inc_log).setVisibility(View.VISIBLE);
            }
        });
        myNight();


        //手机验证
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //打开注册页面\
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            // 提交用户信息（此方法可以不调用）
                    //registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(getActivity());

            }
        });
    }






    //夜间模式的点击事件
    public void myNight(){
        yj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdb_yj = true;
                UiUtils.switchAppTheme(getActivity());
                reload();

            }
        });
    }

    private void reload() {
        Intent intent = getActivity().getIntent();
        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);



        //重置activity
        getActivity().recreate();
    }


}
