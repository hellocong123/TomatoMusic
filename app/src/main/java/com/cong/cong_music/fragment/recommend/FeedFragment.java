package com.cong.cong_music.fragment.recommend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.fragment.base.BaseCommonFragment;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Cong
 * @date 2018/8/27
 * @description
 */
public class FeedFragment extends BaseCommonFragment {

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, null);
    }

    /*
     * 在好友这个Fragment里面，有可能是没有好友，所以界面就可能为空，如果是有好友的就可以使用UserId的方式获取到，
     * 但在我们这个音乐项目，可以什么都不所传，直接显示的是所有人的动态
     * */

    public static Fragment newInstance(String userId) {

        Bundle args = new Bundle();

        if (StringUtils.isNotEmpty(userId)) {
            args.putString(Consts.KEY_ID, userId);
        }

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
