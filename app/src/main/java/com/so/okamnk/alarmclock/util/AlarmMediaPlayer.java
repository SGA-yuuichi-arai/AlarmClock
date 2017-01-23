package com.so.okamnk.alarmclock.util;

import android.media.MediaPlayer;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmMediaPlayer {
    private static AlarmMediaPlayer alarmMediaPlayer = null;
    private MediaPlayer mediaPlayer = null;
    // 他にも独自に保持しておきたい物(例えばsetDataSourceしたパスとか)があればここに宣言＋set/getを作成

    private AlarmMediaPlayer() {
        mediaPlayer = new MediaPlayer();
    }

    public static AlarmMediaPlayer getInstance() {
        if (alarmMediaPlayer == null) {
            alarmMediaPlayer = new AlarmMediaPlayer();
        }
        return alarmMediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
