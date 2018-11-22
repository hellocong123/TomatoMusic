package com.cong.cong_music.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;

public class VideoDetailActivity extends BaseToolBarActivity {


    @Override
    protected void initViews() {
        setContentView(R.layout.activity_video_detail);
        super.initViews();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("视频");
    }
}
