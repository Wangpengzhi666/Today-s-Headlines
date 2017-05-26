package com.headlines.wangpengzhi.todaysheadlines.view.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.event.TestEvent;
import com.headlines.wangpengzhi.todaysheadlines.model.recycler.RecyclerBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.RecyclerPresenter;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.RecyclerAdapter;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.IRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:28
 * <p>
 * 思路：
 */


public class Fragment03_Microbes extends Fragment implements IRecyclerView,SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout sw;
    private RecyclerView rv;
    private RecyclerAdapter adapter;
    private LinearLayoutManager llm;
    private GridLayoutManager glm;
    private StaggeredGridLayoutManager sglm;
    private RecyclerPresenter recyclerPresenter;
    private int noget = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_03_microbes,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        sw = (SwipeRefreshLayout) getView().findViewById(R.id.sw);
        rv = (RecyclerView) getView().findViewById(R.id.rv);
        initData();
    }

    private void initData() {
        sw.setOnRefreshListener(this);

        //渐变动画
        AlphaAnimation a = new AlphaAnimation(0f, 1f);
        a.setDuration(3000);
        sw.setAnimation(a);
        //设置加载圈的颜色
        sw.setColorSchemeColors(Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE);

        //横向.纵向布局(现行管理器，支持横向、纵向。)
        llm = new LinearLayoutManager(getActivity());
        //Grid式布局(网格布局管理器)
        glm = new GridLayoutManager(getActivity(), 3);
        //瀑布布局（瀑布就式布局管理器）           参数(有几行或者几列，模式是横向还是纵向)
        sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);


        //RecyclerView用哪个布局
        rv.setLayoutManager(sglm);
        //获得适配器
        adapter = new RecyclerAdapter(getActivity());
        rv.setAdapter(adapter);
        //RecyclerView的分割线
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);
                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View childAt = parent.getChildAt(i);
                    int left = childAt.getLeft();
                    int top = childAt.getTop();
                    int right = childAt.getRight();
                    int bottom = childAt.getBottom();
                    c.drawRect(left, bottom, right, bottom+5, paint);
                    c.drawRect(left,top,left+5,bottom,paint);
                    c.drawRect(right + 5,top,right,bottom,paint);
                }
            }
        });

        recyclerPresenter = new RecyclerPresenter();
        recyclerPresenter.attachView(this);
        recyclerPresenter.getListViewData();
        adapter.setPresent(recyclerPresenter);


//        setListener();


    }

    @Override
    public void onRefresh() {
        sw.setRefreshing(true);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sw.setRefreshing(false);
                    }
                });
            }
        }.start();
    }


    @Override
    public void callBackF1ListViewData(RecyclerBean recyclerBean) {

        List<RecyclerBean.ResultBean.DataBean> data = recyclerBean.getResult().getData();
        adapter.setData(data);
        //刷新适配器
        adapter.notifyDataSetChanged();

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
