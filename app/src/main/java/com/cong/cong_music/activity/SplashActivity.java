package com.cong.cong_music.activity;


import android.view.WindowManager;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseCommonActivity;
import com.cong.cong_music.util.PackageUtil;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends BaseCommonActivity {

    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_splash);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void initDatas() {

        //定义一个定时器任务，延时3秒跳到主界面
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                next();
            }
        };

        timer.schedule(task, 300);


    }

    @Override
    protected void initListener() {

    }


    private void next() {

        if (isShowGuide()) {                                                //根据版本号判断是否登录
            startActivityAfterFinishThis(GuideActivity.class);
        } else if (sp.isLogin()) {                                          //根据保存的Token是否为空判断是否已经登录
            startActivityAfterFinishThis(MainActivity.class);
        } else {
            startActivityAfterFinishThis(RegisterLoginActivity.class);
        }
    }


    /**
     * 比如：
     *      当前版本号为1，我们使用SP保存一个Boolean值key(1)为版本号，Value值为true，所以第一次进入的时候是true，就进到引导页
     *  在进入了引导页后，我们在引导使用SP保存一个 key(1)为版本号，Value就设置为false，所以再进入来的时候，SP的值已经改成false了
     *  如果有更新了版本，就再设置一个版本的true值
     */
    public boolean isShowGuide() {
        return sp.getBoolean(String.valueOf(PackageUtil.getVersionCode(getApplicationContext())), true);
    }
}
