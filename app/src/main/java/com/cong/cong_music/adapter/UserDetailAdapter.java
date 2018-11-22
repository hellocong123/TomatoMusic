package com.cong.cong_music.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.cong.cong_music.fragment.user.AboutUserFragment;
import com.cong.cong_music.fragment.recommend.FeedFragment;
import com.cong.cong_music.fragment.user.UserDetailMusicFragment;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class UserDetailAdapter extends BaseFragmentPagerAdapter<Integer> {

    private String[] str = {"音乐", "动态", "关于我"};

    private String userId;

    public UserDetailAdapter(Context context, FragmentManager fm) {
        super(context, fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UserDetailMusicFragment.newInstance();
        } else if (position == 1) {
            return FeedFragment.newInstance();
        } else {
            return AboutUserFragment.newInstance();
        }

    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }
}
