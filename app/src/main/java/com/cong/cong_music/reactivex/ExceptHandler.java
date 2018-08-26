package com.cong.cong_music.reactivex;

import android.app.Activity;
import android.content.Intent;


import com.cong.cong_music.activity.base.BaseActivity;
import com.cong.cong_music.activity.RegisterLoginActivity;
import com.cong.cong_music.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * Created by smile on 2018/08/22.
 */

public class ExceptHandler  {
    private Activity activity;

    public ExceptHandler(BaseActivity activity) {
        this.activity = activity;
    }

    public void handle(Throwable e) {
        e.printStackTrace();
        //网络连接异常
        if (e instanceof ConnectException) {
            ToastUtil.showSortToast(activity, "网络好像不太好呀!");
            //网络超时异常
        }else if(e instanceof SocketTimeoutException){
            ToastUtil.showSortToast(activity, "连接超时,请稍后再试!");
            //网络请求异常
        }else if (e instanceof HttpException){
            //强制转成HttpException，用来判断是否出现了401/402/403/404等
            HttpException ex = (HttpException) e;
            if (ex.code() == 401) {
                //跳转到登陆页面
                activity.startActivity(new Intent(activity,RegisterLoginActivity.class));
                ToastUtil.showSortToast(activity, "请先登陆!");
                activity.finish();
            } else if (ex.code() == 403) {
                ToastUtil.showSortToast(activity, "你没有权限访问!");
            } else if (ex.code() == 404) {
                ToastUtil.showSortToast(activity, "你访问内容不存在!");
            } else if (ex.code() >= 500) {
                ToastUtil.showSortToast(activity, "服务器错误,请稍后再试!");
            } else {
                ToastUtil.showSortToast(activity, "未知错误,请稍后再试!");
            }
        } else{
            ToastUtil.showSortToast(activity, "未知错误,请稍后再试!");
        }
    }
}