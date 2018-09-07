package com.cong.cong_music.activity;

import android.support.design.widget.AppBarLayout;
import android.widget.EditText;

import com.cong.cong_music.R;
import com.cong.cong_music.User;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
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


public class RegisterActivity extends BaseToolBarActivity {


    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.et_nickname)
    EditText et_Nickname;
    @BindView(R.id.et_phone)
    EditText et_Phone;
    @BindView(R.id.et_password)
    EditText et_Password;
    private String nickname;
    private String phone;
    private String password;



    @Override
    protected void initViews() {

        setContentView(R.layout.activity_register);

        super.initViews(); //调用一次父类的方法，获取到Toolbar并设置
    }

    @Override
    protected void initDatas() {
        enableBackMenu();
    }

    @Override
    protected void initListener() {


    }

    @OnClick(R.id.bt_register)
    public void onClick() {


        //从EditText保存用户注册使用的参数,并放到User对象里面

        nickname = et_Nickname.getText().toString();
        //isBlank如果是true，就说明是空的，显示请输入昵称
        if (StringUtils.isBlank(nickname)) {
            //能够防止重复显示Toast
            ToastUtil.showSortToast(getActivity(), R.string.enter_nickname);//昵称不能包含空格
            return;
        }

        //用户名不能包含空格
        if (nickname.contains(" ")) {
            //更复杂的，建议用正则表达式
            ToastUtil.showSortToast(getActivity(), R.string.nickname_space);//昵称不能包含空格
            return;
        }

        phone = et_Phone.getText().toString();
        if (StringUtils.isBlank(phone)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_phone);//请输入手机号
            return;
        }

        //StringUtil工具类里面使用正则来判断是否是一个手机号
        if (!StringUtil.isPhone(phone)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_error_phone);//手机号格式错误
            return;
        }

        password = et_Password.getText().toString();
        if (StringUtils.isBlank(password)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_password);//请输入密码
            return;
        }

        if (!StringUtil.isPassword(password)) {
            ToastUtil.showSortToast(getActivity(), R.string.hint_error_password_format);//密码格式错误
            return;
        }

        //请求网络注册
        requestRegister();


    }

    private void requestRegister() {


        User user = new User();
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);
        user.setType(User.TYPE_PHONE);//登录类型：用于标识是手机号登录还是QQ登录

        RetrofitUtils.getInstance().register(user)
                .subscribeOn(Schedulers.io())//上面调用请求的时候使用子线程
                .observeOn(AndroidSchedulers.mainThread())//请求成功的回调返回主线程
                .subscribe(new HttpListener<DetailResponse<Session>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
//                        super.onSucceeded(data);
                        //注册成功，请求登录
                        requestLogin(data.getData());
                    }
                });

    }

    //注册成功直接请求Login网络，完成登录到app
    private void requestLogin(Session data) {

        User user = new User();
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);
        user.setType(User.TYPE_PHONE);//登录类型：用于标识是手机号登录还是QQ登录

        //请求网络
        RetrofitUtils.getInstance().login(user)
                .subscribeOn(Schedulers.io())//上面调用请求的时候使用子线程
                .observeOn(AndroidSchedulers.mainThread())//请求成功的回调返回主线程
                .subscribe(new HttpListener<DetailResponse<Session>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
//                        super.onSucceeded(data);
                        //登录成功
                        loginSucceed(data.getData());

                    }
                });

    }

    //登录成功，保存用户数据到SP,并进入Main主界面
    private void loginSucceed(Session data) {
        sp.setToken(data.getToken());
        sp.setIMToken(data.getIm_token());
        sp.setUserId(data.getId());
        //跳转到主界面，并关闭当前页面
        startActivityAfterFinishThis(MainActivity.class);
        //发布登陆成功信息，让登陆/注册界面好自动关闭,将给定的事件发布到事件总线
        EventBus.getDefault().post(new LoginSuccessEvent());

    }


}
