package com.so.okamnk.alarmclock;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;
import com.so.okamnk.alarmclock.util.AlarmMediaPlayer;

import java.io.IOException;

public class AlarmService extends Service {

    private AlarmMediaPlayer mAlarmMediaPlayer;
    private Vibrator mVibrator;

    private Handler mVolumeChanger;
    private float mVolume;            // 徐々に音量を上げるときに使う

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        AlarmDBAdapter alarmDBAdapter = new AlarmDBAdapter(getApplicationContext());
        AlarmEntity entity = (AlarmEntity) intent.getSerializableExtra(Define.ALARM_ENTITY);
        boolean isPreview = intent.getBooleanExtra(Define.IS_PREVIEW_KEY, false);

        if (isPreview == false) {
            if (alarmDBAdapter.getAlarm(entity.getAlarmId()) == null) {
                return START_NOT_STICKY;
            }
        }

        mAlarmMediaPlayer = AlarmMediaPlayer.getInstance();
        try {
            mVolume = entity.getSoundVolume() / 100.0f;

            mAlarmMediaPlayer.getMediaPlayer().setDataSource(entity.getSoundPath());
            mAlarmMediaPlayer.getMediaPlayer().setAudioStreamType(AudioManager.STREAM_ALARM);
            mAlarmMediaPlayer.getMediaPlayer().setLooping(true);
            mAlarmMediaPlayer.getMediaPlayer().setVolume(mVolume, mVolume);
            mAlarmMediaPlayer.getMediaPlayer().prepare();
        } catch (IOException e) {
            Log.d("AlarmActivity", e.getMessage());
        }

        // バイブレーション
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // 音量徐々に大きくする
        if (entity.getSoundMode() == Define.SOUND_MODE_LARGE_SLOWLY) {
            mVolumeChanger = new Handler();

            final int DELAY_MS = 4000;

            mVolumeChanger.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mVolume += 0.05f;
                    if (mVolume > 1.0f) {
                        mVolume = 1.0f;
                    }
                    mAlarmMediaPlayer.getMediaPlayer().setVolume(mVolume, mVolume);
                    mVolumeChanger.postDelayed(this, DELAY_MS);
                }
            }, DELAY_MS);

        } else {
            mVolumeChanger = null;
        }

        // 再生
        updateAlarm(entity);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAlarmMediaPlayer.getMediaPlayer().stop();
        mVibrator.cancel();
    }

    /**
     * アラーム再生状態を更新する。
     */
    private void updateAlarm(AlarmEntity entity) {
        // 再生するかどうかを計算
        boolean playSound = false;
        boolean playVibration = false;
        if (entity.getManorMode() == Define.MANOR_MODE_FOLLOW_OS) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            int ringerMode = audioManager.getRingerMode();
            switch (ringerMode) {
                case AudioManager.RINGER_MODE_SILENT:
                    playSound = false;
                    playVibration = false;
                    break;
                case AudioManager.RINGER_MODE_VIBRATE:
                    playSound = false;
                    playVibration = true;
                    break;
                case AudioManager.RINGER_MODE_NORMAL:
                    playSound = true;
                    playVibration = false;
                    break;
            }
        } else {
            playSound = true;
            playVibration = false;
        }

        if (playSound) {
            if (mAlarmMediaPlayer.getMediaPlayer().isPlaying() == false) {
                mAlarmMediaPlayer.getMediaPlayer().start();
            }
        } else {
            if (mAlarmMediaPlayer.getMediaPlayer().isPlaying()) {
                mAlarmMediaPlayer.getMediaPlayer().pause();
            }
        }

        if (playVibration) {
            long vibratePattern[] = {500, 500};
            mVibrator.vibrate(vibratePattern, 0);
        } else {
            mVibrator.cancel();
        }
    }

}
