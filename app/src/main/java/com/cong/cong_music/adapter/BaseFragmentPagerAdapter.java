package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cong
 * @date 2018/8/21
 * @description getItem
 */
public abstract class BaseFragmentPagerAdapter<T> extends FragmentPagerAdapter {

    protected final Context context;
    protected final List<T> datas = new ArrayList<T>();

    public BaseFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    public T getData(int position) {
        return datas.get(position);
    }

    //获取ViewPager要显示的List集合数据
    public void setDatas(List<T> data) {
        if (data != null && data.size() > 0) {
            datas.clear();
            datas.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void addDatas(List<T> data) {
        if (data != null && data.size() > 0) {
            datas.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

}
