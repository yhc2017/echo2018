package com.echo.quick.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.echo.quick.utils.LogUtils;

import java.io.IOException;

/**
 * 文件名：AudioPlayerService.java
 * 创建人：Hello周少侠
 * 创建时间：2018/8/25 9:45
 * 类描述：一个用来播放音频的后台服务
 *
 * 修改人：
 * 修改时间：
 * 修改内容：
 *
**/


public class AudioPlayerService extends Service {

    private MediaPlayer mediaPlayer = null;

    public AudioPlayerService() {
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /** * 我们这里执行服务启动都要做的操作 */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("执行服务");
        String path = intent.getStringExtra("path");
        playing(path);
        return super.onStartCommand(intent, flags, startId);
    }

    public void play(final String path) throws IOException {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // 通过异步的方式装载媒体资源
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    mediaPlayer.start();
                    LogUtils.d("开始播放");
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    try {
                        mediaPlayer.setDataSource(path);
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
    }

    public void playing(String path){
        try {
            play(path);
        } catch (IOException e) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            e.printStackTrace();
        }
    }

    /** * 服务销毁时的回调 */
    @Override
    public void onDestroy() {
        System.out.println("onDestroy invoke");
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
