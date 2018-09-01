package com.cong.cong_music.activity;


import android.support.v4.view.ViewPager;
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
import com.cong.cong_music.adapter.HomeAdapter;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.event.LogoutSuccessEvent;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.reactivex.HttpListener;
import com.cong.cong_music.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseCommonActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ImageView iv_avatar;//头像
    private TextView tv_nickname;//昵称
    private TextView tv_description;//简介
    private DrawerLayout drawer_layout;//抽屉布局
    private ImageView iv_music;
    private ImageView iv_recommend;
    private ImageView iv_video;
    private View ll_settings;
    private View ll_my_friend;
    private View ll_message_container;
    private Toolbar toolbar;
    private HomeAdapter adapter;
    private ViewPager vp;

    @Override
    protected void initViews() {

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

        vp = findViewById(R.id.viewPager);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();//同步状态

        super.initViews();
    }


    @Override
    protected void initDatas() {
        super.initDatas();

        showHome();


        showUserInfo();
    }

    private void showHome() {


        adapter = new HomeAdapter(getActivity(), getSupportFragmentManager());
        vp.setAdapter(adapter);

        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(0);
        datas.add(1);
        datas.add(2);
        adapter.setDatas(datas);


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
                vp.setCurrentItem(0, true);//设置当前ViewPager页
                break;
            case R.id.iv_recommend:
                vp.setCurrentItem(1, true);//设置当前ViewPager页
                break;
            case R.id.iv_video:
                vp.setCurrentItem(2, true);//设置当前ViewPager页
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
        if (sp.isLogin()) {
            //点击头像的时候把用户ID也顺道传递过去
            startActivityExtraId(UserDetailActivity.class, sp.getUserId());
        } else {
            startActivity(LoginPhoneActivity.class);
        }

    }
    //由于多个点击事件都用到这个关闭Drawer的方法，所以把它抽取出来
    private void closeDrawer() {
        drawer_layout.closeDrawer(Gravity.START);
    }


    @Override
    protected void initListener() {
        super.initListener();

        iv_avatar.setOnClickListener(this);
        iv_music.setOnClickListener(this);
        iv_recommend.setOnClickListener(this);
        iv_video.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
        ll_my_friend.setOnClickListener(this);
        ll_message_container.setOnClickListener(this);

        vp.addOnPageChangeListener(this);
        //默认选中第二个页面，设置监听器在选择就会调用监听器，不会为什么放在initView就不能调用
        vp.setCurrentItem(1);

        EventBus.getDefault().register(this);


    }

    /*-------------------------ViewPager滑动监听三个重写方法-------------------------*/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    //选中页面的时候
    @Override
    public void onPageSelected(int position) {
        //根据被选中的Pager页设置对应的Toolbar的图片资源
        if (position == 0) {
            iv_music.setImageResource(R.drawable.ic_play_selected);//设置选中的ImageView，颜色凸出高亮
            iv_recommend.setImageResource(R.drawable.ic_music);//设置未选中的图片
            iv_video.setImageResource(R.drawable.ic_video);
        } else if (position == 1) {
            iv_music.setImageResource(R.drawable.ic_play);
            iv_recommend.setImageResource(R.drawable.ic_music_selected);
            iv_video.setImageResource(R.drawable.ic_video);
        } else {
            iv_music.setImageResource(R.drawable.ic_play);
            iv_recommend.setImageResource(R.drawable.ic_music);
            iv_video.setImageResource(R.drawable.ic_video_selected);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /*-------------------------接收设置Activity点击退出发布的事件，退出App-------------------------*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logoutSuccessEvent(LogoutSuccessEvent event) {
        showUserInfo();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
