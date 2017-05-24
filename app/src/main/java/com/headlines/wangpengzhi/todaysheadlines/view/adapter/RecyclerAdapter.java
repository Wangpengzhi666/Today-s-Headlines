package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.recycler.RecyclerBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.RecyclerPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/21  20:38
 * <p>
 * 思路：
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private Context context;
    private RecyclerPresenter recyclerPresenter;
    private List<RecyclerBean.ResultBean.DataBean> data = new ArrayList<>();

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setPresent(RecyclerPresenter present) {
        this.recyclerPresenter = present;
    }

    public void setData(List<RecyclerBean.ResultBean.DataBean> datas) {
        if (datas != null) {
            data.addAll(datas);
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.frg_03_secycler_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(data.get(position).getTitle());
        recyclerPresenter.setImageData(myViewHolder.imageView, data.get(position).getThumbnail_pic_s());
        recyclerPresenter.setImageData(myViewHolder.imageView, data.get(position).getThumbnail_pic_s02());
        recyclerPresenter.setImageData(myViewHolder.imageView, data.get(position).getThumbnail_pic_s03());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.recycler_image);
            this.textView = (TextView) itemView.findViewById(R.id.recycler_text);
        }
    }
}
