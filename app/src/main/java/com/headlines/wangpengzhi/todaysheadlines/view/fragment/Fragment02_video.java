package com.headlines.wangpengzhi.todaysheadlines.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.event.TestEvent;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.VideoRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:26
 * <p>
 * 思路：
 */


public class Fragment02_video extends Fragment {

    private RecyclerView video_recycler;
    private List<String> list = new ArrayList<>();
    private VideoRecyclerView videoRecyclerView;
    private LinearLayoutManager ll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_02_video,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
        
    }


    private void initView() {

        video_recycler = (RecyclerView) getView().findViewById(R.id.video_recyclerView);

        videoRecyclerView = new VideoRecyclerView(getActivity());
        videoRecyclerView.setList(list);

        ll = new LinearLayoutManager(getActivity());
        video_recycler.setAdapter(videoRecyclerView);
        video_recycler.setLayoutManager(ll);


    }

    private void initData() {
        for (int i = 0; i <10 ; i++) {
            list.add("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=221"+i+"1&editionType=default&source=ucloud");
        }
    }











    //夜间模式的EventBus
    private int noget = 0;
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
