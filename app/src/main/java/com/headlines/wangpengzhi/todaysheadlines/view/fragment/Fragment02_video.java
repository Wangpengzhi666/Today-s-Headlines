package com.headlines.wangpengzhi.todaysheadlines.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.headlines.wangpengzhi.todaysheadlines.R;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/10  14:26
 * <p>
 * 思路：
 */


public class Fragment02_video extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_02_video,container,false);
    }
}
