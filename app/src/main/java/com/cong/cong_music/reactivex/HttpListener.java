package com.cong.cong_music.reactivex;

import android.text.TextUtils;

import com.cong.cong_music.activity.base.BaseActivity;
import com.cong.cong_music.bean.response.BaseResponse;
import com.cong.cong_music.util.ToastUtil;


/**
 * Created by smile on 03/03/2018.
 */

public class HttpListener<T extends BaseResponse> extends AbsObserver<T> {

    private final BaseActivity activity;

    public HttpListener(BaseActivity activity) {
        this.activity=activity;
    }

    public void onSucceeded(T data) {

    }

    //异常统一处理
    public void onFailed(T t, Throwable e) {

        if (activity != null) {
            activity.hideLoading();
        }
        if (e != null) {
            //代表出现请求异常，在这个类里面作统一处理
            new ExceptHandler(activity).handle(e);
        } else {
            if (t != null && !TextUtils.isEmpty(t.getMessage())) {
                ToastUtil.showSortToast(activity, t.getMessage());
            } else {
                ToastUtil.showSortToast(activity, "未知错误,请稍后再试!");
            }

        }
    }

    public boolean isSuccess(T t) {
        return t.getStatus()==null;
    }

    //复写父类下一步的方法
    @Override
    public void onNext(T t) {
        super.onNext(t);
        if (isSuccess(t)) {
            onSucceeded(t);
        } else {
            //请求网络失败或请求出现异常的时候
            onFailed(t,null);
        }
    }

    //复写父类错误方法
    @Override
    public void onError(Throwable e) {
        super.onError(e);
        onFailed(null,e);
    }

}
