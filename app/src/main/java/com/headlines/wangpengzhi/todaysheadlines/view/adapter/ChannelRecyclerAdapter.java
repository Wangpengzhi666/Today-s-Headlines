package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.channel.ItemBean;
import com.headlines.wangpengzhi.todaysheadlines.view.activity.Channel;
import com.headlines.wangpengzhi.todaysheadlines.view.iview.ItemTouchHelperAdapter;

import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/26  09:54
 * <p>
 * 思路：
 */


public class ChannelRecyclerAdapter extends RecyclerView.Adapter<ChannelRecyclerAdapter.MyViewHolder> implements ItemTouchHelperAdapter{

    private Context context;
    //数据
    private List<ItemBean> list;
    //编辑按钮的状态
    private int type;
    private int RecyclerView_Width;

    public ChannelRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setRecyclerView_Width(int recycleview_width, List<ItemBean> list, int type){
        RecyclerView_Width = recycleview_width;
        this.list = list;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        MyViewHolder vh = new MyViewHolder(root);
        return vh;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.channel_tv.setText(list.get(position).getText());
        //根据上边传回来的值设置判断删除图片的显示
        if (type == 1){
            holder.delete_tv.setVisibility(View.GONE);
        }else if (type == 0){
            //判断集合的前两个不设置删除按钮
            if (position == 0 || position == 1) {
                holder.delete_tv.setVisibility(View.GONE);
            } else if (list.get(position).isSelect()) {
                holder.delete_tv.setVisibility(View.VISIBLE);
            } else {
                holder.delete_tv.setVisibility(View.GONE);
            }
            ViewGroup.LayoutParams params = holder.channel_rl.getLayoutParams();
            params.width = RecyclerView_Width;
            holder.channel_rl.setLayoutParams(params);

            holder.delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type == 0) {
                        ItemBean positionItemBean = Channel.MYRECYCLERADAPTER.getItem(position);
                        positionItemBean.setSelect(false);
                        Channel.MYRECYCLERADAPTER.deleteItem(position);
                        Channel.UNSELECTADAPTER.addItem(positionItemBean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //注意:这里最少有一个,因为有多了一个添加按钮
        return null == list ? 0 : list.size();
    }

    //移动处理
    @Override
    public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (type == 0) {
            int fromPosition = source.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (toPosition == 0 || toPosition == 1) {

            } else {
                if (fromPosition < list.size() && toPosition < list.size()) {
                    //交换数据位置
//                        Collections.swap(list, fromPosition, toPosition);
                    ItemBean itemBean = list.get(fromPosition);
                    list.remove(fromPosition);
                    list.add(toPosition, itemBean);
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
            list.remove(position); //移除数据
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
        return list.get(position);
    }

    public void addItem(ItemBean channel) {
        list.add(channel);
        notifyDataSetChanged();
    }

    public void allSelect() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelect(true);
        }
        notifyDataSetChanged();
    }

    public void addItem(int position, ItemBean channel) {
        list.add(position, channel);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

}
