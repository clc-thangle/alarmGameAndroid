package com.finalexam.alarm;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class AlarmSoundService extends Service {
    private MediaPlayer mediaPlayer;
    private String songList = null;

    private String s;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        s ="on";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        songList = intent.getStringExtra("songList");

        if (songList == null){
            mediaPlayer = MediaPlayer.create(this, R.raw.okay);
        }
        else {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(songList));
        }


        if (s.equals("on")) {
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
//        s = "off";


        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //On destory stop and release the media player
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
}
