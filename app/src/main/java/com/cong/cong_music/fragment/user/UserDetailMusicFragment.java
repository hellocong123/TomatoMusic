package com.cong.cong_music.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cong.cong_music.R;
import com.cong.cong_music.fragment.base.BaseCommonFragment;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class UserDetailMusicFragment extends BaseCommonFragment {

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, null);
    }


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        UserDetailMusicFragment fragment = new UserDetailMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
