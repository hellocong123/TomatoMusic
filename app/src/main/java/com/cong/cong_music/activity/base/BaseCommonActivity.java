package com.cong.cong_music.activity.base;

import com.cong.cong_music.util.OrmUtil;
import com.cong.cong_music.util.SharedPreferencesUtil;

/**
 * @author Cong
 * @date 2018/8/21
 * @description
 */
public  class BaseCommonActivity extends BaseActivity{


    protected SharedPreferencesUtil sp;
    protected OrmUtil orm;
//    private FloatingLayoutManager floatingLayoutManager;


    @Override
    protected void initViews() {

        sp = SharedPreferencesUtil.getInstance(getApplicationContext());
        orm = OrmUtil.getInstance(getApplicationContext());
//        floatingLayoutManager = MusicPlayerService.getFloatingLayoutManager(getApplicationContext());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!ServiceUtil.isBackgroundRunning(getApplicationContext())) {
//            //如果当前程序在前台，就尝试隐藏桌面歌词
//            floatingLayoutManager.tryHide();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (ServiceUtil.isBackgroundRunning(getApplicationContext())) {
//            //如果当前程序在后台，就显示桌面歌词
//            floatingLayoutManager.tryShow();
//        }
//    }

}
