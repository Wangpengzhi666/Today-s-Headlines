package com.headlines.wangpengzhi.todaysheadlines.view.activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.utils.UiUtils;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.BaseUiListener;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.Fragment01_Home;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.Fragment02_video;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.Fragment03_Microbes;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.Fragment04_Mine;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;


import cn.smssdk.SMSSDK;

public class MainActivity extends AppCompatActivity{

    private RadioGroup rg;
    private RadioButton rb_home;
    private RadioButton rb_video;
    private RadioButton rb_microbes;
    private RadioButton rb_mime;
    private FragmentTransaction transaction;
    private FragmentTransaction transaction1;

    private Fragment01_Home home;
    private Fragment02_video video;
    private Fragment03_Microbes microbes;
    private Fragment04_Mine mine;
    private FragmentManager manager;

    private long exitTime = 0;
    private int theme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams.FLAG_FULLSCREEN;

        //切换主题必须放在onCreate()之前
        if (savedInstanceState == null) {
            theme = UiUtils.getAppTheme(MainActivity.this);
        } else {
            theme = savedInstanceState.getInt("theme");
        }
        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SMSSDK.initSDK(this, "1de49df839a40", "8f08e8dfc865cc6128dd2f14e4b1c2eb");

        initView();
//        initData();
        initDefaultFragment();
//        setRgListener();
        setRgListener2();
    }



    private void initView() {
        rg = (RadioGroup) findViewById(R.id.rg);
        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_video = (RadioButton) findViewById(R.id.rb_video);
        rb_microbes = (RadioButton) findViewById(R.id.rb_microbes);
        rb_mime = (RadioButton) findViewById(R.id.rb_mime);
    }

    private void initData() {

        video = new Fragment02_video();
        microbes = new Fragment03_Microbes();
        mine = new Fragment04_Mine();

    }

    private void initDefaultFragment() {

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        home = new Fragment01_Home();
        transaction.add(R.id.fl,home);
        transaction.commit();
    }


    private void setRgListener2() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        select(0);
                        break;
                    case R.id.rb_video:
                        select(1);
                        break;
                    case R.id.rb_microbes:
                        select(2);
                        break;
                    case R.id.rb_mime:
                        select(3);
                        break;
                }

            }
        });

        if (Fragment04_Mine.rdb_yj){
            rb_home.setChecked(false);
            rb_mime.setChecked(true);
        }
    }


    private void select(int i) {
        FragmentManager fm = getSupportFragmentManager();  //获得Fragment管理器
        FragmentTransaction ft = fm.beginTransaction(); //开启一个事务

        hidtFragment(ft);   //隐藏Fragment的方法，先判断fragment是否为空，如果不为空则隐藏Fragment

        switch (i) {//点击切换fragment,如果fragment为空，则创建，如果不为空，就显示
            case 0:
                if (home == null) {
                    home = new Fragment01_Home();
                    ft.add(R.id.fl, home);
                } else {
                    ft.show(home);
                }
                break;
            case 1:
                if (video == null) {
                    video = new Fragment02_video();
                    ft.add(R.id.fl, video);
                } else {
                    ft.show(video);
                }
                break;
            case 2:
                if (microbes == null) {
                    microbes = new Fragment03_Microbes();
                    ft.add(R.id.fl, microbes);
                } else {
                    ft.show(microbes);
                }
                break;
            case 3:
                if (mine == null) {
                    mine = new Fragment04_Mine();
                    ft.add(R.id.fl, mine);
                } else {
                    ft.show(mine);
                }
                break;
        }
        ft.commit();   //提交事务

    }
    private void hidtFragment(FragmentTransaction fragmentTransaction) {
        if (home != null) {
            fragmentTransaction.hide(home);

        }
        if (video != null) {
            fragmentTransaction.hide(video);

        }
        if (microbes != null) {
            fragmentTransaction.hide(microbes);

        }
        if (mine != null) {
            fragmentTransaction.hide(mine);

        }

    }




















    private void setRgListener() {
        manager = getSupportFragmentManager();
        transaction1 = manager.beginTransaction();
        transaction1.add(R.id.fl,home);
        transaction1.commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){

                    case R.id.rb_home:
                        FragmentTransaction transaction1 = manager.beginTransaction();

                        transaction1.replace(R.id.fl,home);
                        transaction1.commit();

                        break;
                    case R.id.rb_video:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.fl,video);
                        transaction2.commit();
                        break;
                    case R.id.rb_microbes:
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        transaction3.replace(R.id.fl,microbes);
                        transaction3.commit();
                        break;
                    case R.id.rb_mime:
                        FragmentTransaction transaction4 = manager.beginTransaction();
                        transaction4.replace(R.id.fl,mine);
                        transaction4.commit();
                        break;
                }
            }
        });
        if (Fragment04_Mine.rdb_yj){
            rb_home.setChecked(false);
            rb_mime.setChecked(true);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }


    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode+
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        BaseUiListener mIUiListener=new BaseUiListener();
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
