package com.cong.cong_music.activity;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cong.cong_music.Consts;
import com.cong.cong_music.R;
import com.cong.cong_music.User;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
import com.cong.cong_music.adapter.MyCommonNavigatorAdapter;
import com.cong.cong_music.adapter.UserDetailAdapter;
import com.cong.cong_music.api.RetrofitUtils;
import com.cong.cong_music.bean.response.DetailResponse;
import com.cong.cong_music.reactivex.HttpListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserDetailActivity extends BaseToolBarActivity {


    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_info)
    TextView tv_info;
    @BindView(R.id.bt_follow)
    Button bt_follow;
    @BindView(R.id.bt_send_message)
    Button bt_send_Message;

    @BindView(R.id.tabs)
    MagicIndicator tabs;
    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.vp)
    ViewPager vp;
    private String nickname;
    private String id;
    private User user;
    private UserDetailAdapter adapter;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_user_detail);
        super.initViews();
    }

    @Override
    protected void initDatas() {

        enableBackMenu();

        //加载用户数据
        loadUserData();


    }

    /**
     * 1、联网加载用户数据
     * 跳转到用户详情界面一般是有两个情况
     * a.点击用户头像的时候，获取到用户ID，通过用户ID去请求网络，获取到对应用户数据加载到界面
     * b.在评论那里是@XX的，点击用户昵称的时候也要跳到该用户详情上面，
     */
    private void loadUserData() {
        //获取用户昵称
        nickname = getIntent().getStringExtra(Consts.NICKNAME);
        //获取用户ID
        id = getIntent().getStringExtra(Consts.KEY_ID);

        if (StringUtils.isNotEmpty(id)) {
            //如果Id，不为空，就通过Id查询
            fetchDataById(id);
        } else if (StringUtils.isNotEmpty(nickname)) {
            //通过昵称查询，主要是用在@昵称中
            fetchDataByNickname(nickname);
        } else {
            finish();
        }
    }

    //通过昵称连接网络，获取用户数据
    private void fetchDataByNickname(String nickname) {
        RetrofitUtils.getInstance().userDetailByNickname(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<DetailResponse<User>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
//                        super.onSucceeded(data);
                        showUserData(data.getData());
                    }
                });
    }

    //通过用户ID连接网络，获取用户数据
    private void fetchDataById(String id) {
        RetrofitUtils.getInstance().userDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpListener<DetailResponse<User>>(getActivity()) {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
//                        super.onSucceeded(data);
                        showUserData(data.getData());
                    }
                });
    }

    //成功获取用户数据之后，加载数据到布局和ViewPager上面
    private void showUserData(User user) {
        this.user = user;//把User保存到实例变量，因为可能其他地方还要用到
        showViewPagerUI();//设置ViewPager+TabLayout数据
        showUserInfo();//显示用户头像昵称等数据
        showFollowStatus();//显示关注的状态
    }

    //根据是否是当前用户还是其他用户来设置关注状态
    private void showFollowStatus() {

        //如果当前页面请求的userId,等于你登录的时候保存的userId，证明你看的就是自己的页面信息
        /*-----------------看的是自己的详情-----------------*/
        if (user.getId().equals(sp.getUserId())) {
            //自己，隐藏关注按钮，隐藏发送消息按钮
            bt_follow.setVisibility(View.GONE);
            bt_send_Message.setVisibility(View.GONE);
        } else {
            /*-----------------看的是别人的详情-----------------*/
            //判断我是否关注该用户
            bt_follow.setVisibility(View.VISIBLE);//显示关注按钮
            if (user.isFollowing()) {//如果用户关注了，字就设置为取消关注
                //已经关注
                bt_follow.setText("取消关注");
                bt_send_Message.setVisibility(View.VISIBLE);//关注了就可以显示发送消息按钮
            } else {
                //没有关注
                bt_follow.setText("关注");
                bt_send_Message.setVisibility(View.GONE);//没有关注就可以隐藏发送消息按钮
            }
        }

    }

    private void showUserInfo() {

        /*
         * 为什么要使用Glide获取到Bitmap呢，而不是直接显示这个ImageView呢？
         * 其实是为了可以通过这个Bitmap提取这个颜色，也就用到了这个调用版，要引用Palette包
         *
         * 代码：通过Glide下载Bitmap
         * */
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        RequestBuilder<Bitmap> bitmapRequestBuilder = Glide.with(this).asBitmap().apply(options).load(user.getAvatar());
        bitmapRequestBuilder.into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                //设置头像图片
                iv_avatar.setImageBitmap(resource);

                //通过Palette调色版，获取到resource（Bitmap）的颜色
                Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(@NonNull Palette palette) {
                        //再通过Palette获取到颜色样本
                        Palette.Swatch swatch = palette.getVibrantSwatch();
                        //如果颜色样本不为空
                        if (swatch != null) {
                            //通过颜色样本获取一个颜色的RGB
                            int rgb = swatch.getRgb();
                            //toolbar.setBackgroundColor(rgb);
                            //然后再把这个RGB颜色设置到AppBarLayout上面
                            abl.setBackgroundColor(rgb);
                            //设置状态栏
                            if (android.os.Build.VERSION.SDK_INT >= 21) {
                                Window window = getWindow();
                                window.setStatusBarColor(rgb);
                                window.setNavigationBarColor(rgb);
                            }
                        }
                    }
                });
            }
        });

        //设置用户昵称和关注\粉丝数
        tv_nickname.setText(user.getNickname());
        tv_info.setText(getResources().getString(
                R.string.user_detail_count_info,
                user.getFollowings_count(),
                user.getFollowers_count()));
    }

    private void showViewPagerUI() {

        adapter = new UserDetailAdapter(getActivity(), getSupportFragmentManager());
        adapter.setUserId(id);
        vp.setAdapter(adapter);

        final ArrayList<Integer> datas = new ArrayList<>();
        datas.add(0);
        datas.add(1);
        datas.add(2);
        adapter.setDatas(datas);


        //将TabLayout和ViewPager关联起来
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        MyCommonNavigatorAdapter navigatorAdapter = new MyCommonNavigatorAdapter(datas, adapter, vp);
        commonNavigator.setAdapter(navigatorAdapter);

        //让TabLayout自动调整模式
        commonNavigator.setAdjustMode(true);

        //Tab+ViewPager绑定
        tabs.setNavigator(commonNavigator);
        ViewPagerHelper.bind(tabs, vp);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initToolbar() {

        toolbar.setTitle("用户详情");
    }


}
