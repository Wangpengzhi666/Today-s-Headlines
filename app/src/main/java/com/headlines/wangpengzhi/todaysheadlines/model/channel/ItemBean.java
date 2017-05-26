package com.headlines.wangpengzhi.todaysheadlines.model.channel;

/**
 * @类作用:
 * @author: 王鹏智
 * @Date: 2017/5/26  09:48
 * <p>
 * 思路：
 */

public class ItemBean {

    public ItemBean(String text, boolean select) {
        this.text = text;
        this.select = select;
    }

    private  String  text;
  private  boolean  select;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
