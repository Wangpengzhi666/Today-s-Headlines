package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.headlines.wangpengzhi.todaysheadlines.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/26  21:06
 * <p>
 * 思路：
 */


public class VideoRecyclerView extends RecyclerView.Adapter<VideoRecyclerView.VideoRecyclerHolder> {

    private Context context;
    private List<String> list = new ArrayList<>();

    public VideoRecyclerView(Context context) {
        this.context = context;
    }

    public void setList(List<String> list){
        this.list.addAll(list);
    }

    @Override
    public VideoRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.frg_02_video_item, null);

        VideoRecyclerHolder videoRecyclerHolder = new VideoRecyclerHolder(view);
        return videoRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(VideoRecyclerHolder holder, int position) {

       holder.jcVideoPlayer.setUp(list.get(position), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");

            Glide.with(context).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(holder.jcVideoPlayer.thumbImageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class VideoRecyclerHolder extends RecyclerView.ViewHolder{

        private JCVideoPlayerStandard jcVideoPlayer;

        public VideoRecyclerHolder(View itemView) {
            super(itemView);
            this.jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.video_recycler_JC);
        }
    }
}
