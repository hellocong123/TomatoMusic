package com.cong.cong_music.activity;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseCommonActivity;
import com.cong.cong_music.adapter.GuideAdapter;
import com.cong.cong_music.util.PackageUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends BaseCommonActivity {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    @BindView(R.id.bt_login_or_register)
    Button bt_LoginOrRegister;
    @BindView(R.id.bt_enter)
    Button bt_Enter;
    private GuideAdapter adapter;

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void initDatas() {

        //给ViewPager设置Adapter
        adapter = new GuideAdapter(getActivity(), getSupportFragmentManager());
        vp.setAdapter(adapter);
        //绑定一个指示器到ViewPager
        indicator.setViewPager(vp);
        //注册一个观察者来接收与适配器数据更改相关的回调，也就是说indicator就可以监听ViewPager的变化
        adapter.registerDataSetObserver(indicator.getDataSetObserver());

        //添加三张图片到List集合，用来显示ViewPager
        ArrayList<Integer> datas = new ArrayList<>();
        datas.add(R.drawable.guide1);
        datas.add(R.drawable.guide2);
        datas.add(R.drawable.guide3);
        adapter.setDatas(datas);

    }

    @Override
    protected void initListener() {

    }

    //跳转 注册登录界面
    @OnClick(R.id.bt_login_or_register)
    public void bt_login_or_register() {
        setFirst();
        //点击注册登录按钮，跳转到登录界面
        startActivityAfterFinishThis(RegisterLoginActivity.class);
    }

    //以游客的身份进入
    public void bt_enter(View view) {
        setFirst();
        //点击进入按钮，跳转到Main主界面
        startActivityAfterFinishThis(MainActivity.class);
    }

    /**
     * 设置该版本号为第一次进入，下次就不会在进引导页了
     */
    private void setFirst() {
        sp.putBoolean(String.valueOf(PackageUtil.getVersionCode(getApplicationContext())),false);
    }


}
