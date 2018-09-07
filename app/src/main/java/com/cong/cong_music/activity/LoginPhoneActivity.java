package com.cong.cong_music.activity;


import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.cong.cong_music.R;
import com.cong.cong_music.User;
import com.cong.cong_music.activity.base.BaseCommonActivity;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.Session;
import com.cong.cong_music.bean.event.LoginSuccessEvent;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.StringUtil;
import com.cong.cong_music.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPhoneActivity extends BaseCommonActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.et_phone)
    EditText et_Phone;
    @BindView(R.id.et_password)
    EditText et_Password;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_login_phone);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }


    @OnClick(R.id.bt_login)
    public void onClick() {

        String phone = et_Phone.getText().toString();
        if (StringUtils.isBlank(phone)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_phone);
            return;
        }

        if (!StringUtil.isPhone(phone)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_error_phone);
            return;
        }

        String password = et_Password.getText().toString();
        if (StringUtils.isBlank(password)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_password);
            return;
        }

        if (!StringUtil.isPassword(password)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_error_password_format);
            return;
        }

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setType(User.TYPE_PHONE);

        RetrofitUtils.getInstance().login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<DetailResponse<Session>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
//                        super.onSucceeded(data);

                        //登录成功
                        loginSucceed(data.getData());
                    }
                });


    }

    //登陆完成后，保存相关信息，并跳转到主界面
    private void loginSucceed(Session data) {

        sp.setToken(data.getToken());
        sp.setUserId(data.getId());
        sp.setIMToken(data.getIm_token());
        startActivityAfterFinishThis(MainActivity.class);

        //登录完成也要发布一个消息到登录/注册界面，通知可以关闭Activity了
        EventBus.getDefault().post(new LoginSuccessEvent());


    }
}
