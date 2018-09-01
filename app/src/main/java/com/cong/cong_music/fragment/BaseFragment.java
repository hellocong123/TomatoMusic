package com.cong.cong_music.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cong.cong_music.Consts;
import com.cong.cong_music.activity.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Cong
 * @date 2018/8/21
 * @description
 */
public abstract class BaseFragment extends Fragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews();
        initStyles();
        initDatas();
        initListener();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void startActivityExtraId(Class<?> clazz, String id) {
        Intent intent = new Intent(getActivity(), clazz);
        if (!TextUtils.isEmpty(id)) {
            intent.putExtra(Consts.USER_ID, id);
        }
        startActivity(intent);
    }

    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getActivity(), clazz));
        getActivity().finish();
    }

    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    public BaseActivity getMainActivity() {
        return (BaseActivity) getActivity();
    }

    //设置用户可见的提示
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisibleToUser();
        } else {
            onInvisibleToUser();
        }
    }

    //对用户可见
    protected void onInvisibleToUser() {

    }

    protected void onVisibleToUser() {

    }

    protected abstract View getLayoutView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 找控件
     */
    protected void initViews() {

    }

    /**
     * 动态设置样式，颜色，宽高，背景
     */
    protected void initStyles() {
    }

    /**
     * 设置数据
     */
    protected void initDatas() {
    }

    /**
     * 绑定监听器
     */
    protected void initListener() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
