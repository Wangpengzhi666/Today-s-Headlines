package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.headlines.wangpengzhi.todaysheadlines.R;
import com.headlines.wangpengzhi.todaysheadlines.model.frg01_xlist.Frg_01_xlistBean;
import com.headlines.wangpengzhi.todaysheadlines.model.home.HomeBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.Frg01_XListViewPresenter;
import com.headlines.wangpengzhi.todaysheadlines.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/12  10:48
 * <p>
 * 思路：
 */


public class MyxListView extends BaseAdapter {

    private Frg01_XListViewPresenter presenter;
    private Context mcontext;
    private List<Frg_01_xlistBean.ResultBean.DataBean> list = new ArrayList<>();
    private static final int TYPE1 = 0;
    private static final int TYPE2 = 1;
    private static final int TYPE3 = 2;


    public MyxListView(Context context) {
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Frg_01_xlistBean.ResultBean.DataBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        String s1 = list.get(position).getThumbnail_pic_s();
        String s2 = list.get(position).getThumbnail_pic_s02();
        String s3 = list.get(position).getThumbnail_pic_s03();
        if (s1 != null && s2!=null && s3!=null){
            return TYPE1;
        }else if(s1 != null && s2!=null){
            return TYPE2;
        }else {
            return TYPE3;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        switch (type){
            case TYPE1:
                ViewHolder viewHolder;
                if (convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = View.inflate(mcontext, R.layout.frg_01_listview_item1,null);
                    viewHolder.imageView1 = (ImageView) convertView.findViewById(R.id.f1_f1_lvImageView);
                    viewHolder.imageView2 = (ImageView) convertView.findViewById(R.id.f1_f1_lvImageView2);
                    viewHolder.imageView3 = (ImageView) convertView.findViewById(R.id.f1_f1_lvImageView3);
                    viewHolder.textView1 = (TextView) convertView.findViewById(R.id.f1_f1_lvTextView);
                    viewHolder.textView2 = (TextView) convertView.findViewById(R.id.f1_f1_lvTextView2);
                    convertView.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                presenter.setImageData(viewHolder.imageView1,list.get(position).getThumbnail_pic_s());
                presenter.setImageData(viewHolder.imageView2,list.get(position).getThumbnail_pic_s02());
                presenter.setImageData(viewHolder.imageView3,list.get(position).getThumbnail_pic_s03());
                viewHolder.textView1.setText(getItem(position).getTitle());
                viewHolder.textView2.setText(getItem(position).getDate());
                break;

            case TYPE2:
                ViewHolder2 viewHolder2;
                if (convertView == null){
                    viewHolder2 = new ViewHolder2();
                    convertView = View.inflate(mcontext, R.layout.frg_01_listview_item2,null);
                    viewHolder2.imageView = (ImageView) convertView.findViewById(R.id.f1_f2_lvImageView);
                    viewHolder2.textView1 = (TextView) convertView.findViewById(R.id.f1_f2_lvTextView);
                    viewHolder2.textView2 = (TextView) convertView.findViewById(R.id.f1_f2_lvTextView2);
                    convertView.setTag(viewHolder2);
                }else{
                    viewHolder2 = (ViewHolder2) convertView.getTag();
                }
                presenter.setImageData(viewHolder2.imageView,list.get(position).getThumbnail_pic_s());
                viewHolder2.textView1.setText(getItem(position).getTitle());
                viewHolder2.textView2.setText(getItem(position).getDate());

                break;
            case TYPE3:
                ViewHolder3 viewHolder3;
                if (convertView == null){
                    viewHolder3 = new ViewHolder3();
                    convertView = View.inflate(mcontext, R.layout.frg_01_listview_item3,null);
                    viewHolder3.imageView = (ImageView) convertView.findViewById(R.id.f1_f3_lvImageView);
                    viewHolder3.textView1 = (TextView) convertView.findViewById(R.id.f1_f3_lvTextView);
                    viewHolder3.textView2 = (TextView) convertView.findViewById(R.id.f1_f3_lvTextView2);
                    convertView.setTag(viewHolder3);
                }else{
                    viewHolder3 = (ViewHolder3) convertView.getTag();
                }
                presenter.setImageData(viewHolder3.imageView,list.get(position).getThumbnail_pic_s());
                viewHolder3.textView1.setText(getItem(position).getTitle());
                viewHolder3.textView2.setText(getItem(position).getDate());
                break;
        }

        return convertView;
    }

    class ViewHolder{
        TextView textView1;
        TextView textView2;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
    }
    class ViewHolder2{
        TextView textView1;
        TextView textView2;
        ImageView imageView;
    }
    class ViewHolder3{
        TextView textView1;
        TextView textView2;
        ImageView imageView;
    }
    //设置presenter
    public void setPresenter(Frg01_XListViewPresenter guoji) {
        this.presenter = guoji;
    }


    public void setListViewData(Frg_01_xlistBean guoji) {
        List<Frg_01_xlistBean.ResultBean.DataBean> data = guoji.getResult().getData();
        if (data != null){
            list = data;
        }
    }
}

