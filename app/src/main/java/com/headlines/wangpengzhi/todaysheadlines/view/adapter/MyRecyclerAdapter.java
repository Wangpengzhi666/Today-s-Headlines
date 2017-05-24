package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.frg01_xlist.Frg_01_xlistBean;

import org.xutils.x;

import java.util.ArrayList;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/18  20:44
 * <p>
 * 思路：
 */


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.RecyclerViewHolder> {
    private Context context;
    private ArrayList<Frg_01_xlistBean.ResultBean.DataBean> list = new ArrayList<>();

    public MyRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(context).
                inflate(R.layout.frg_03_secycler_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.text.setText(list.get(position).getDate());
        x.image().bind(holder.image,list.get(position).getThumbnail_pic_s02());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView text;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.recycler_image);
            this.text = (TextView) itemView.findViewById(R.id.recycler_text);
        }
    }
}
