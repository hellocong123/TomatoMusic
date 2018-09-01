package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.cong.cong_music.fragment.AboutUserFragment;
import com.cong.cong_music.fragment.FMFragment;
import com.cong.cong_music.fragment.FeedFragment;
import com.cong.cong_music.fragment.RecommendFragment;
import com.cong.cong_music.fragment.UserDetailMusicFragment;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class MusicUIAdapter extends BaseFragmentPagerAdapter<Integer> {

    private static String[] str = {"推荐", "朋友", "电台"};

    public MusicUIAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return RecommendFragment.newInstance();
        } else if (position == 1) {
            return FeedFragment.newInstance();
        } else {
            return FMFragment.newInstance();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }
}
