package com.cong.cong_music.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cong.cong_music.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class MyCommonNavigatorAdapter extends CommonNavigatorAdapter {


    private final ArrayList<Integer> datas;
    private final UserDetailAdapter adapter;
    private final ViewPager vp;

    public MyCommonNavigatorAdapter(ArrayList<Integer> datas, UserDetailAdapter adapter, ViewPager vp) {
        this.datas = datas;
        this.adapter = adapter;
        this.vp = vp;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    //显示Title的时候调用
    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
        colorTransitionPagerTitleView.setNormalColor(R.color.text_white);
        colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
        colorTransitionPagerTitleView.setText(adapter.getPageTitle(index));
        colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击到哪个TabLayout的时候，ViewPager就要滚动过去
                vp.setCurrentItem(index);
            }
        });
        return colorTransitionPagerTitleView;
    }

    //显示的是Title下
    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(Color.WHITE);
        return indicator;
    }
}
