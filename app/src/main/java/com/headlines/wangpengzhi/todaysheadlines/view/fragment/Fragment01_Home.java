package com.headlines.wangpengzhi.todaysheadlines.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.event.TestEvent;
import com.headlines.wangpengzhi.todaysheadlines.model.tab.TabBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.TabPresenter;
import com.headlines.wangpengzhi.todaysheadlines.view.activity.Channel;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.TabAdapter;
import com.headlines.wangpengzhi.todaysheadlines.view.fragment.frg_home.Frg_home_headline;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.ITabView;
import com.limxing.xlistview.view.XListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:20
 * <p>
 * 思路：
 */


public class Fragment01_Home extends Fragment implements ITabView<TabBean>{

    private XListView home_xlv;
    private TabLayout home_tab;
    private ViewPager home_vp;
    private List<TabBean.ResultBean.DateBean> tabData;
    private TabAdapter tabAdapter;
    private int index = 0;
    private TabPresenter tabPresenter;
    private List<Fragment> list = new ArrayList();
    private int noget = 0;
    private TextView channel_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_01_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        home_tab = (TabLayout) getView().findViewById(R.id.home_tab);
        home_vp = (ViewPager) getView().findViewById(R.id.home_vp);
        channel_btn = (TextView) getView().findViewById(R.id.channel_btn);
        channel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Channel.class);
                startActivity(intent);
            }
        });
    }



    private void initData() {


        tabPresenter = new TabPresenter();
        tabPresenter.attachView(this);
        tabPresenter.getTabDataFromServer(TabBean.class,noget);


//
//        home_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(home_tab));
//        home_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//                index = tab.getPosition();
//                if(home_vp.getCurrentItem() != index){
//                    home_vp.setCurrentItem(index);
//                }
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    @Override
    public void callbackStr(TabBean tabBean) {


        tabData = tabBean.getResult().getDate();
        for (int i = 0; i < tabData.size(); i++) {
            home_tab.addTab(home_tab.newTab().setText(tabData.get(i).getTitle()));
            list.add(new Frg_home_headline(tabData.get(i).getUri()));
        }
        setTabAdapter();

    }
    public void setTabAdapter(){
        tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager(), list,tabData);
        home_vp.setAdapter(tabAdapter);
        home_tab.setupWithViewPager(home_vp);
        home_tab.setTabsFromPagerAdapter(tabAdapter);
    }

    @Override
    public void callbackErr(String errMsg, int errCode) {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(TestEvent test){
        noget = test.Noget();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }






}
