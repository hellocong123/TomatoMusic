package com.cong.cong_music.activity;



import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cong.cong_music.R;
import com.cong.cong_music.activity.base.BaseToolBarActivity;
import com.cong.cong_music.views.LyricView;
import com.cong.cong_music.views.RecordThumbView;
import com.cong.cong_music.views.RecordView;

public class MusicPlayerActivity extends BaseToolBarActivity {

    private ImageView iv_loop_model;
    private ImageView iv_album_bg;
    private ImageView iv_play_control;
    private ImageView iv_play_list;
    private ImageView iv_previous;
    private ImageView iv_next;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private SeekBar sb_progress;
    private RecordThumbView rt;
    private ImageView iv_download;
    private RecordView rv;
    private LinearLayout lyric_container;
    private RelativeLayout rl_player_container;
    private SeekBar sb_volume;
    private LyricView lv;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_music_player);
        super.initViews();

        iv_download = findViewById(R.id.iv_download);
        iv_album_bg = findViewById(R.id.iv_album_bg);
        iv_loop_model = findViewById(R.id.iv_loop_model);
        iv_play_control = findViewById(R.id.iv_play_control);
        rt = findViewById(R.id.rt);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_end_time = findViewById(R.id.tv_end_time);
        sb_progress = findViewById(R.id.sb_progress);
        iv_next = findViewById(R.id.iv_next);
        iv_previous = findViewById(R.id.iv_previous);
        iv_play_list = findViewById(R.id.iv_play_list);
        rv = findViewById(R.id.rv);
        lyric_container = findViewById(R.id.lyric_container);
        rl_player_container = findViewById(R.id.rl_player_container);
        sb_volume = findViewById(R.id.sb_volume);
        lv = findViewById(R.id.lv);

//        enableBackMenu();
    }

    @Override
    protected void initToolbar() {
        toolbar.setTitle("音乐播放");
    }
}
