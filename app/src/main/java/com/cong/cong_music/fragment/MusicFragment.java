package com.cong.cong_music.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cong.cong_music.R;
import com.cong.cong_music.adapter.MusicUIAdapter;
import com.cong.cong_music.fragment.base.BaseCommonFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Cong
 * @date 2018/8/26
 * @description
 */
public class MusicFragment extends BaseCommonFragment {
    @BindView(R.id.tabs)
    MagicIndicator tabs;
    @BindView(R.id.vp)
    ViewPager vp;
    private MusicUIAdapter adapter;


    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, null);
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatas() {
/*
        //这里一定要调用childFragmentManager
        //getFragmentManager()所得到的是所在fragment 的父容器的管理器，
        //getChildFragmentManager()所得到的是在fragment  里面子容器的管理器。

        容易出现bug的地方
        1.Fragment嵌套Fragment要用getChildFragmentManager
        (1)问题重现
        1>Fragment放ViewPager，ViewPager里面是fragment。第一次进入没问题，再次进入ViewPager的fragment时里面内容就没了,数据丢失
        2>Fragment低频率点击切换不会发生问题，过快点击马上崩溃
        3>错误：Java.lang.IllegalArgumentException：No view found for id for fragment
        3>调用fragment的replace方法不走onDestroy()、onDestroyView()方法，无法销毁fragment
        4>在fragment中写倒计时，每次切换后倒计时越来越快的问题
        getFragmentManager到的是activity对所包含fragment的Manager，
        而如果是fragment嵌套fragment，那么就需要利用getChildFragmentManager()了。
*/
        adapter = new MusicUIAdapter(getActivity(), getChildFragmentManager());
        vp.setAdapter(adapter);

        final ArrayList<Integer> datas = new ArrayList<>();
        datas.add(0);
        datas.add(1);
        datas.add(2);
        adapter.setDatas(datas);

        //将MagicIndicator(TabLayout)和ViewPager关联起来
        CommonNavigator commonNavigator = new CommonNavigator(getMainActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.text_white));
                colorTransitionPagerTitleView.setSelectedColor(Color.WHITE);
                colorTransitionPagerTitleView.setText(adapter.getPageTitle(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        commonNavigator.setAdjustMode(true);
        tabs.setNavigator(commonNavigator);

        //把tab和ViewPager绑定在一起
        ViewPagerHelper.bind(tabs, vp);

    }
}
