package com.cong.cong_music.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
import com.cong.cong_music.bean.event.LogoutSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingsActivity extends BaseToolBarActivity implements View.OnClickListener {


    @BindView(R.id.bt_logout)
    Button bt_logout;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_settings);
        super.initViews();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("设置");
    }


    @Override
    protected void initDatas() {
        enableBackMenu();

    }

    @Override
    protected void initListener() {
        bt_logout.setOnClickListener(this);
    }

    @OnClick(R.id.bt_logout)
    public void onClick(View v) {

        //注销
        sp.logout();
        //发布退出登陆的信息，因为首页要根据登陆状态显示
        EventBus.getDefault().post(new LogoutSuccessEvent());

        finish();


    }

}
