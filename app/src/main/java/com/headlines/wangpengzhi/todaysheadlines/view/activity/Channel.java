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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.channel.DataTools;
import com.headlines.wangpengzhi.todaysheadlines.model.channel.ItemBean;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.RecyclerItemClickListener;
import com.headlines.wangpengzhi.todaysheadlines.view.adapter.SimpleItemTouchHelperCallback;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.ItemTouchHelperAdapter;

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

    private ChannelRecyclerAdapter MYRECYCLERADAPTER;
    private ChannelRecyclerAdapter UNSELECTADAPTER;
    private SimpleItemTouchHelperCallback mycallback;
    private SimpleItemTouchHelperCallback unCallback;
    private ItemTouchHelper mytouchHelper;
    private ItemTouchHelper untouchHelper;
    private TextView edit;
    boolean isediting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_layout);

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
            mineData.add(new ItemBean("帅智" + i, false));
        }
        for (int i = 15; i < 30; i++) {
            otherData.add(new ItemBean("没毛病" + i, false));
        }

    }
    private void initView() {
        mylike = (RecyclerView) findViewById(R.id.recycler_mylike);

        MYRECYCLERADAPTER = new ChannelRecyclerAdapter();
        MYRECYCLERADAPTER.setlist(mineData,0);

        mylike.setAdapter(MYRECYCLERADAPTER);

        mylike.setLayoutManager(new GridLayoutManager(this,3));

        mycallback = new SimpleItemTouchHelperCallback(MYRECYCLERADAPTER);
        mytouchHelper = new ItemTouchHelper(mycallback);
        mytouchHelper.attachToRecyclerView(mylike);



        unselected = (RecyclerView) findViewById(R.id.recycler_Unselected);

        UNSELECTADAPTER = new ChannelRecyclerAdapter();

        UNSELECTADAPTER.setlist(otherData,1);

        unselected.setAdapter(UNSELECTADAPTER);

        unselected.setLayoutManager(new GridLayoutManager(this,3));

        unCallback = new SimpleItemTouchHelperCallback(UNSELECTADAPTER);
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
                MYRECYCLERADAPTER.notifyDataSetChanged();

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
                    final ItemBean channel = UNSELECTADAPTER.getItem(position);
                    if (isediting) {
                        channel.setSelect(true);
                    } else {
                        channel.setSelect(false);
                    }
                    MYRECYCLERADAPTER.addItem(channel);
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
                                    UNSELECTADAPTER.deleteItem(position);
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


    class ChannelRecyclerAdapter extends RecyclerView.Adapter<ChannelRecyclerAdapter.MyViewHolder> implements ItemTouchHelperAdapter {
        //数据
        private List<ItemBean> mlist;
        private int type;

        public void setlist (List<ItemBean> list, int type) {
            this.mlist  = list;
            this.type = type;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(Channel.this).inflate(R.layout.item_list,parent,false);
            MyViewHolder vh = new MyViewHolder(root);
            return vh;
        }



        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.channel_tv.setText(mlist.get(position).getText());
            //根据上边传回来的值设置判断删除图片的显示
            if (type == 1){
                holder.delete_tv.setVisibility(View.GONE);
            }else if (type == 0){
                //判断集合的前两个不设置删除按钮
                if (position == 0 || position == 1) {
                    holder.delete_tv.setVisibility(View.GONE);
                } else if (mlist.get(position).isSelect()) {
                    holder.delete_tv.setVisibility(View.VISIBLE);
                } else {
                    holder.delete_tv.setVisibility(View.GONE);
                }
                ViewGroup.LayoutParams params = holder.channel_rl.getLayoutParams();
                params.width = recycleview_width;
                holder.channel_rl.setLayoutParams(params);

                holder.delete_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 0) {
                            ItemBean positionItemBean = MYRECYCLERADAPTER.getItem(position);
                            positionItemBean.setSelect(false);
                            MYRECYCLERADAPTER.deleteItem(position);
                            UNSELECTADAPTER.addItem(positionItemBean);
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            //注意:这里最少有一个,因为有多了一个添加按钮
            return null == mlist ? 0 : mlist.size();
        }

        //移动处理
        @Override
        public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (type == 0) {
                int fromPosition = source.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (toPosition == 0 || toPosition == 1) {

                } else {
                    if (fromPosition < mlist.size() && toPosition < mlist.size()) {
                        //交换数据位置
//                        Collections.swap(list, fromPosition, toPosition);
                        ItemBean itemBean = mlist.get(fromPosition);
                        mlist.remove(fromPosition);
                        mlist.add(toPosition, itemBean);
                        //刷新位置交换
                        notifyItemMoved(fromPosition, toPosition);
                    }
                }
                //移动过程中移除view的放大效果
                onItemClear(source);
            }
        }

        //移除
        @Override
        public void onItemDissmiss(RecyclerView.ViewHolder source) {
            if (type == 0) {
                int position = source.getAdapterPosition();
                mlist.remove(position); //移除数据
                notifyItemRemoved(position);//刷新数据移除
            }
        }

        //放大
        @Override
        public void onItemSelect(RecyclerView.ViewHolder viewHolder) {
            if (type == 0) {
                //当拖拽选中时放大选中的view
                int position = viewHolder.getAdapterPosition();
                viewHolder.itemView.setScaleX(1.2f);
                viewHolder.itemView.setScaleY(1.2f);
            }
        }

        //恢复
        @Override
        public void onItemClear(RecyclerView.ViewHolder viewHolder) {
            //拖拽结束后恢复view的状态
            if (type == 0) {
                viewHolder.itemView.setScaleX(1.0f);
                viewHolder.itemView.setScaleY(1.0f);
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView channel_tv;
            private TextView delete_tv;
            private RelativeLayout channel_rl;

            public MyViewHolder(View itemView) {
                super(itemView);
                channel_tv = (TextView) itemView.findViewById(R.id.channel_tv);
                delete_tv = (TextView) itemView.findViewById(R.id.delete_tv);
                channel_rl = (RelativeLayout) itemView.findViewById(R.id.channel_rl);
            }
        }



        public ItemBean getItem(int position) {
            return mlist.get(position);
        }

        public void addItem(ItemBean channel) {
            mlist.add(channel);
            notifyDataSetChanged();
        }

        public void allSelect() {
            for (int i = 0; i < mlist.size(); i++) {
                mlist.get(i).setSelect(true);
            }
            notifyDataSetChanged();
        }

        public void addItem(int position, ItemBean channel) {
            mlist.add(position, channel);
            notifyDataSetChanged();
        }

        public void deleteItem(int position) {
            mlist.remove(position);
            notifyDataSetChanged();
        }

    }

}

