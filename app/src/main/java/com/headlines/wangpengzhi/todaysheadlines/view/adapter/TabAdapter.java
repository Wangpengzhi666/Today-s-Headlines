package com.headlines.wangpengzhi.todaysheadlines.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.headlines.wangpengzhi.todaysheadlines.model.tab.TabBean;
import com.headlines.wangpengzhi.todaysheadlines.presenter.TabPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/12  10:18
 * <p>
 * 思路：
 */


public class TabAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> frglist;
    private List<TabBean.ResultBean.DateBean> tablist;

    public TabAdapter(FragmentManager fm, List<Fragment> frglist, List<TabBean.ResultBean.DateBean> tablist) {
        super(fm);
        this.frglist = frglist;
        this.tablist = tablist;
    }

    @Override
    public Fragment getItem(int position) {
        return frglist.get(position);
    }

    @Override
    public int getCount() {
        return frglist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tablist.size() > 0) {
            String title = tablist.get(position).getTitle();
            return title;
        } else {
            return "www";
        }

    }

//
//    public void setData(List<TabBean.ResultBean.DateBean> tablist) {
//        if (tablist != null) {
//            this.tablist.addAll(tablist);
//        }
//    }
}
