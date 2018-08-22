package com.cong.cong_music.activity;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.cong.cong_music.R;
import com.cong.cong_music.util.LogUtil;
import com.cong.cong_music.util.PackageUtil;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends BaseCommonActivity {


//    private MyHandler myHandler;


    @Override
    protected void initViews() {
        super.initViews();
        setContentView(R.layout.activity_splash);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void initDatas() {


        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                next();
            }
        };

        timer.schedule(task, 3000);

//        myHandler = new  MyHandler(this);
//        myHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                myHandler.sendEmptyMessage(-1);
//            }
//        },3000);

    }

    @Override
    protected void initListener() {

    }


    //    private boolean mShowGuide;
//    private boolean mShowLogin;
//    private MyHandler mHandler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//
//        //去除状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//
//
//
//        init();
//    }
//
//    private void init() {
//
//        mHandler = new MyHandler(this);
//
//
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                mHandler.sendEmptyMessage(0);
//            }
//        }, 3000);
//
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        mHandler.removeMessages(0);
//    }
//
//    public boolean isShowGuide() {
//        return mShowGuide;
//    }
//
//    public boolean isShowLogin() {
//        return mShowLogin;
//    }
//
//
//    class MyHandler extends Handler {
//        WeakReference<Activity> mWeakReference;
//        public MyHandler(Activity activity) {
//            mWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            final Activity activity = mWeakReference.get();
//            if (activity != null) {
//                if (msg.what == -1) {
//
//                }
//            }
//        }
//    }

    private void next() {

        if (isShowGuide()) {
            //跳传到引导界面，并毕当前Activity
            startActivityAfterFinishThis(GuideActivity.class);
        } else if (sp.isLogin()) {
            startActivityAfterFinishThis(MainActivity.class);
        } else {
            startActivityAfterFinishThis(LoginActivity.class);
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
