package com.cong.cong_music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.cong.cong_music.manager.MusicPlayerManager;
import com.cong.cong_music.manager.impl.MusicPlayerManagerImpl;
import com.cong.cong_music.util.ServiceUtil;

public class MusicPlayerService extends Service {

    private static final String TAG = "MusicPlayerService";
    public static MusicPlayerManager manager;


    /**
     * 提供一个静态方法获获取Manager
     * 为什么不支持将逻辑写到Service呢？
     * 是因为操作service要么通过bindService，那么startService麻烦
     * @param context
     * @return
     */
    public static MusicPlayerManager getMusicPlayerManager(Context context) {
        if (!ServiceUtil.isServiceRunning(context)) {
            //如果当前Service没有引用就要启动它
            Intent downloadSvr = new Intent(context, MusicPlayerService.class);
            context.startService(downloadSvr);
        }
        if (MusicPlayerService.manager == null) {
            //初始化音乐播放管理器
            MusicPlayerService.manager = MusicPlayerManagerImpl.getInstance(context);
        }
        return manager;
    }

    public MusicPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
