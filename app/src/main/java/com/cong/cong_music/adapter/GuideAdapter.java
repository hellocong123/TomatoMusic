package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cong.cong_music.fragment.GuideFragment;

/**
 * @author Cong
 * @date 2018/8/21
 * @description
 */
public class GuideAdapter extends BaseFragmentPagerAdapter<Integer> {

    public GuideAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 根据position生成fragment
     */
    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(getData(position));
    }
}
