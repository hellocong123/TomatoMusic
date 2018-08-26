package com.cong.cong_music.activity;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.User;
import com.cong.cong_music.activity.base.BaseCommonActivity;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.UserUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseCommonActivity implements View.OnClickListener {

    private ImageView iv_avatar;//头像
    private TextView tv_nickname;//昵称
    private TextView tv_description;//简介
    private DrawerLayout drawer_layout;//抽屉布局
    private View iv_music;
    private View iv_recommend;
    private View iv_video;
    private View ll_settings;
    private View ll_my_friend;
    private View ll_message_container;
    private Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_main);

        drawer_layout = findView(R.id.drawer_layout);
        //主界面里的Toolbar
        toolbar = findView(R.id.toolbar);
        //drawer布局里的头像、昵称、简介
        iv_avatar = findViewById(R.id.iv_avatar);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_description = findViewById(R.id.tv_description);
        //主界面里的音乐、推荐、视频，这三个都是在Toolbar里，控制着ViewPager
        iv_music = findViewById(R.id.iv_music);
        iv_recommend = findViewById(R.id.iv_recommend);
        iv_video = findViewById(R.id.iv_video);
        //drawer布局里的设置、我的朋友、我的消息
        ll_settings = findViewById(R.id.ll_settings);
        ll_my_friend = findViewById(R.id.ll_my_friend);
        ll_message_container = findViewById(R.id.ll_message_container);

        iv_avatar.setOnClickListener(this);
        iv_music.setOnClickListener(this);
        iv_recommend.setOnClickListener(this);
        iv_video.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
        ll_my_friend.setOnClickListener(this);
        ll_message_container.setOnClickListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();//同步状态
    }


    @Override
    protected void initDatas() {
        super.initDatas();



        showUserInfo();
    }

    private void showUserInfo() {

        //用户信息这部分，进来是看不到的，所以可以延后初始化
        if (sp.isLogin()) {
            //已经登录：调用用户信息接口，返回用户信息加载到界面
            RetrofitUtils.getInstance().userDetail(sp.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new HttpListener<DetailResponse<User>>(getActivity()) {
                        @Override
                        public void onSucceeded(DetailResponse<User> data) {
                            super.onSucceeded(data);
                            showData(data.getData());
                        }
                    });

        } else {
            //没有登录：显示默认的头像和昵称
            UserUtil.showNotLoginUser(getActivity(), iv_avatar, tv_nickname, tv_description);
        }
    }

    private void showData(User data) {

        //将显示用户信息放到单独的类中，是为了重用，因为在用户详情界面会用到
        UserUtil.showUser(getActivity(), data, iv_avatar, tv_nickname, tv_description);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_settings:
                startActivity(SettingsActivity.class);
                closeDrawer();
                break;
            case R.id.iv_music:
//                vp.setCurrentItem(0, true);
                break;
            case R.id.iv_recommend:
//                vp.setCurrentItem(1, true);
                break;
            case R.id.iv_video:
//                vp.setCurrentItem(2, true);
                break;
            case R.id.iv_avatar:
                avatarClick();
                closeDrawer();
                break;
            case R.id.ll_my_friend:
//                startActivity(MyFriendActivity.class);
                closeDrawer();
                break;
            case R.id.ll_message_container:
//                startActivity(MessageActivity.class);
                closeDrawer();
                break;
            default:
                //如果当前界面没有处理，就调用父类的方法
//                super.onClick(v);
                break;
        }
    }



    private void avatarClick() {
        closeDrawer();
        if (sp.isLogin()){
            startActivityExtraId(UserDetailActivity.class, sp.getUserId());
        }else {
            startActivity(LoginPhoneActivity.class);
        }

    }

    private void closeDrawer() {
        drawer_layout.closeDrawer(Gravity.START);
    }


    @Override
    protected void initListener() {
        super.initListener();
    }


}
