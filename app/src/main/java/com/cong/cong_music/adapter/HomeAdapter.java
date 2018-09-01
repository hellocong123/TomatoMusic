package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cong.cong_music.activity.base.BaseActivity;
import com.cong.cong_music.fragment.MeFragment;
import com.cong.cong_music.fragment.MusicFragment;
import com.cong.cong_music.fragment.VideoFragment;

/**
 * @author Cong
 * @date 2018/8/26
 * @description
 */
public class HomeAdapter extends BaseFragmentPagerAdapter<Integer> {


    public HomeAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    //生成对应的三个Fragment
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MeFragment.newInstance();
        } else if (position == 1) {
            return MusicFragment.newInstance();
        } else {
            return VideoFragment.newInstance();
        }
    }
}
