package com.project.tki.myalarmmanager;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.project.tki.myalarmmanager.NotiManager;
import com.project.tki.myalarmmanager.R;

public class AlarmService extends Service {
    public static final String ONOFF = "ONOFF";
    private MediaPlayer mediaPlayer;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getExtras().getBoolean(ONOFF)) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.start();
            NotiManager.sendNotification(this, 1234, NotiManager.Channel.DEFAULT, "test title", "bbbody");

        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        return START_NOT_STICKY;
    }
}
