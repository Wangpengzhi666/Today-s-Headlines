package com.headlines.wangpengzhi.todaysheadlines.view.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.channel.DataTools;
import com.headlines.wangpengzhi.todaysheadlines.model.channel.ItemBean;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.ChannelRecyclerAdapter;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.RecyclerItemClickListener;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/26  16:47
 * <p>
 * 思路：
 */


public class Channel extends Activity{

    private List<ItemBean> mineData = new ArrayList<>();
    private List<ItemBean> otherData = new ArrayList<>();
    private RecyclerView mylike;
    private RecyclerView unselected;
    private int screenWidth;
    private int recycleview_width;
    public static ChannelRecyclerAdapter myRecyclerAdapter;
    public static ChannelRecyclerAdapter unselectadapter;
    private SimpleItemTouchHelperCallback mycallback;
    private SimpleItemTouchHelperCallback unCallback;
    private ItemTouchHelper mytouchHelper;
    private ItemTouchHelper untouchHelper;
    private TextView edit;
    boolean isediting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        screenWidth = dm.widthPixels;
        recycleview_width = (screenWidth - DataTools.dip2px(Channel.this, 60)) / 3;

        initData();
        initView();
        setItem();
    }


    private void initData() {
        for (int i = 0; i < 15; i++) {
            mineData.add(new ItemBean("贼帅" + i, false));
        }
        for (int i = 15; i < 30; i++) {
            otherData.add(new ItemBean("唉" + i, false));
        }

    }
    private void initView() {
        mylike = (RecyclerView) findViewById(R.id.recycler_mylike);

        myRecyclerAdapter = new ChannelRecyclerAdapter(Channel.this, mineData, 0);

        myRecyclerAdapter.setRecyclerView_Width(recycleview_width);

        mylike.setAdapter(myRecyclerAdapter);

        mylike.setLayoutManager(new GridLayoutManager(this,3));

        mycallback = new SimpleItemTouchHelperCallback(myRecyclerAdapter);
        mytouchHelper = new ItemTouchHelper(mycallback);
        mytouchHelper.attachToRecyclerView(mylike);



        unselected = (RecyclerView) findViewById(R.id.recycler_Unselected);

        unselectadapter = new ChannelRecyclerAdapter(Channel.this, otherData, 0);

        unselectadapter.setRecyclerView_Width(recycleview_width);


        unselected.setAdapter(unselectadapter);

        unselected.setLayoutManager(new GridLayoutManager(this,3));

        unCallback = new SimpleItemTouchHelperCallback(unselectadapter);
        untouchHelper = new ItemTouchHelper(unCallback);
        untouchHelper.attachToRecyclerView(unselected);

    }

    private void setItem() {


        //编辑按钮
        edit = (TextView) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isediting) {
                    edit.setText("编辑");
                    for (int i = 0; i < mineData.size(); i++) {
                        mineData.get(i).setSelect(false);
                    }
                } else {
                    edit.setText("完成");
                    for (int i = 0; i < mineData.size(); i++) {
                        mineData.get(i).setSelect(true);
                    }
                }
                isediting = !isediting;
                myRecyclerAdapter.notifyDataSetChanged();

            }
        });


        mylike.addOnItemTouchListener(new RecyclerItemClickListener(Channel.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RecyclerView.ViewHolder childViewViewHolder) {

            }

            @Override
            public void onLongClick(View view, int posotion, RecyclerView.ViewHolder childViewViewHolder) {
                if (posotion != 0 && posotion != 1) {
//                    for (int i = 0; i < mineData.size(); i++) {
//                        mineData.get(i).setSelect(true);
//                    }
//                    isediting = true;
//                    edit.setText("完成");
//                    mineAdapter.allSelect();
                    mytouchHelper.startDrag(childViewViewHolder);
                }

            }
        }));


        unselected.addOnItemTouchListener(new RecyclerItemClickListener(Channel.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, RecyclerView.ViewHolder childViewViewHolder) {

                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {
                    TextView newTextView = (TextView) view.findViewById(R.id.channel_tv);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final ItemBean channel = unselectadapter.getItem(position);
                    if (isediting) {
                        channel.setSelect(true);
                    } else {
                        channel.setSelect(false);
                    }
                    myRecyclerAdapter.addItem(channel);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                RecyclerView.LayoutManager mine_layoutManager = mylike.getLayoutManager();
                                if (mine_layoutManager instanceof LinearLayoutManager) {
                                    LinearLayoutManager linearManager = (LinearLayoutManager) mine_layoutManager;
                                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                                    mylike.getChildAt(lastItemPosition).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation);
                                    unselectadapter.deleteItem(position);
                                }
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
            }

            @Override
            public void onLongClick(View view, int posotion, RecyclerView.ViewHolder childViewViewHolder) {

            }
        }));


    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(cache);
        return iv;
    }

    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation
    ) {

        int[] initLocation = new int[2];
        moveView.getLocationInWindow(initLocation);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            private boolean isMove;

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
                isMove = false;
            }
        });
    }

    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }


}

