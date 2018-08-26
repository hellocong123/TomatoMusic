package com.cong.cong_music.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseCommonActivity;
import com.cong.cong_music.bean.event.LoginSuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterLoginActivity extends BaseCommonActivity {


    @BindView(R.id.bt_login)
    Button bt_Login;
    @BindView(R.id.bt_register)
    Button bt_Register;
    @BindView(R.id.tv_enter)
    TextView tv_Enter;
    @BindView(R.id.iv_login_qq)
    ImageView iv_LoginQq;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_register_login);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        //注册一个给定的订阅服务器用来接收事件，这里接收了关闭当前界面
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }


    @OnClick({R.id.bt_login, R.id.bt_register, R.id.tv_enter, R.id.iv_login_qq})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_login://手机号登录
                login();
                break;

            case R.id.bt_register://注册
                register();
                break;

            case R.id.tv_enter://游客体验进入Main
                enter();
                break;

            case R.id.iv_login_qq://使用QQ登录
                loginQQ();
                break;
        }
    }

    private void login() {
        startActivityAfterFinishThis(LoginPhoneActivity.class);
    }

    private void register() {
        startActivity(RegisterActivity.class);

    }

    private void enter() {
        startActivity(MainActivity.class);
    }

    private void loginQQ() {

    }


    //发送事件之后就会调用这个方法，LoginSuccessEvent就是注册时的事件，然后finish()关闭页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginSuccessEvent(LoginSuccessEvent event) {
        //连接融云服务器
//        ((App)getApplication()).imConnect();
        finish();
    }

    @Override
    protected void onDestroy() {
        //从所有事件类注销给定订阅服务器
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
