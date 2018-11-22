package com.cong.cong_music.activity.base;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.cong.cong_music.R;
import com.cong.cong_music.util.LogUtil;

/**
 * @author Cong
 * @date 2018/8/23
 * @description
 */
public abstract class BaseToolBarActivity extends BaseCommonActivity {

    public Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();



        toolbar = findViewById(R.id.toolbar);
        initToolbar();
        setSupportActionBar(toolbar);
        enableBackMenu();
    }

    protected abstract void initToolbar();


//    @Override
//    public void setTitle(CharSequence title) {
//
//
//        if (!TextUtils.isEmpty(title)) {
//            super.setTitle(title);
//        }
//    }

    //给Toolbar添加一个返回按钮
    protected void enableBackMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    //给Toolbar的返回按钮设置点击事件，关闭当前窗口
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
