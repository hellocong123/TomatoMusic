package com.cong.cong_music.manager.impl;

import android.content.Context;
import android.media.MediaPlayer;

import com.cong.cong_music.bean.Song;
import com.cong.cong_music.listener.OnMusicPlayerListener;
import com.cong.cong_music.manager.MusicPlayerManager;

/**
 * @author Cong
 * @date 2018/9/11
 * @description
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {

    private static MusicPlayerManagerImpl manager;
    private final Context context;

    /**
     * 媒体播放器m
     */
    private MediaPlayer player;
    public static synchronized MusicPlayerManager getInstance(Context context) {
        if (manager == null) {
            manager = new MusicPlayerManagerImpl(context);
        }
        return manager;
    }

    private MusicPlayerManagerImpl(Context context) {
        this.context = context;
        player = new MediaPlayer();
        initListener();
    }

    private void initListener() {

    }


    @Override
    public void play(String uri, Song data) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void seekTo(int progress) {

    }

    @Override
    public void addOnMusicPlayerListener(OnMusicPlayerListener listener) {

    }

    @Override
    public void removeOnMusicPlayerListener(OnMusicPlayerListener listener) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setLooping(boolean looping) {

    }
}
