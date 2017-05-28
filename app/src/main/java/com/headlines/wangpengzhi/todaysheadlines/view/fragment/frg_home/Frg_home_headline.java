package com.headlines.wangpengzhi.todaysheadlines.view.fragment.frg_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.event.TestEvent;
import com.headlines.wangpengzhi.todaysheadlines.model.frg01_xlist.Frg_01_xlistBean;
import com.headlines.wangpengzhi.todaysheadlines.model.home.HomeBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.Frg01_XListViewPresenter;
import com.headlines.wangpengzhi.todaysheadlines.presenter.HomePresenter;
import com.headlines.wangpengzhi.todaysheadlines.view.activity.NewsDataActivity;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.MyxListView;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IHomeView;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IViewByF1_f1;
import com.limxing.xlistview.view.XListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/12  09:34
 * <p>
 * 思路：
 */


public class Frg_home_headline extends Fragment implements IViewByF1_f1<Frg_01_xlistBean> {

    private XListView headline_xlv;
    private MyxListView myxListView;
    private HomePresenter homePresenter;
    private Frg01_XListViewPresenter presenter;
    private MyxListView adapter;
    private String urll;
    private List<Frg_01_xlistBean.ResultBean.DataBean> list;
    private int noget = 0;

//    public Frg_home_headline(String url) {
//        this.urll = url;
//    }

    public static Frg_home_headline getInstance(String url) {
        Frg_home_headline frg_home_headline = new Frg_home_headline();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        frg_home_headline.setArguments(bundle);
        return frg_home_headline;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        urll = getArguments().getString("url");
        return inflater.inflate(R.layout.frg_home_headline, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
//        initData();
    }

    private void initView() {
        headline_xlv = (XListView) getView().findViewById(R.id.home_headline_xlv);
        headline_xlv.setPullRefreshEnable(true);
        headline_xlv.setXListViewListener(new XListView.IXListViewListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                headline_xlv.stopRefresh(true);
            }

            //上拉加载更多
            @Override
            public void onLoadMore() {
                headline_xlv.stopLoadMore();
            }
        });

        getListViewData();
    }

    private void getListViewData() {

        presenter = new Frg01_XListViewPresenter();
        presenter.attachView(this);
        adapter = new MyxListView(getActivity());
        adapter.setPresenter(presenter);
        headline_xlv.setAdapter(adapter);
        presenter.getListViewData(Frg_01_xlistBean.class, this.urll);
    }


    @Override
    public void callBackF1ListViewData(Frg_01_xlistBean frg_01_xlistBean) {
        adapter.setListViewData(frg_01_xlistBean);
        adapter.notifyDataSetChanged();
        list = frg_01_xlistBean.getResult().getData();
        headline_xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsDataActivity.class);
                intent.putExtra("url", list.get(position - 1).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(TestEvent test) {
        noget = test.Noget();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }
}
