package com.cong.cong_music.activity.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cong.cong_music.Consts;
import com.cong.cong_music.R;

import butterknife.ButterKnife;

/**
 * @author Cong
 * @date 2018/8/12
 * @description
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        initDatas();
        initListener();

    }

    /**
     * 找控件
     */
    protected void initViews() {
    }

    protected <T extends View> T findView(int id) {
        return findViewById(id);
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


    //跳传到传进来的Activity
    protected void startActivity(Class<?> clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    //跳传到传进来的Activity，并关闭当前的
    protected void startActivityAfterFinishThis(Class<?> clazz) {
        startActivity(new Intent(getActivity(), clazz));
        finish();
    }

    //跳传到传进来的Activity，并给这个Activity传入一个参数为ID的标识
    protected void startActivityExtraId(Class<?> clazz, String id) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(Consts.KEY_ID, id);
        startActivity(intent);
    }

    //跳传到传进来的Activity，并给这个Activity传入一个参数为String的标识
    protected void startActivityExtraString(Class<?> clazz, String string) {
        Intent intent = new Intent(getActivity(), clazz);
        intent.putExtra(Consts.STRING, string);
        startActivity(intent);
    }

    protected BaseActivity getActivity() {
        return this;
    }

    private ProgressDialog progressDialog;

    //显示一个Loading加载框
    public void showLoading() {
        showLoading(getResources().getString(R.string.loading));
    }

    public void showLoading(String message) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("提示");
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    //显示一个Loading加载框，要传入一个Id的
    public void showLoading(int resId) {
        showLoading(getResources().getString(resId));
    }

    //隐藏加载框
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }
}
