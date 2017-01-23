package com.so.okamnk.alarmclock;

/**
 * Created by yoshihide on 2017/01/19.
 */

public class Define {

    // Intentで渡すKey文字列
    // alarmIDのKey文字列
    public static final String ALARM_ID_KEY = "alarmId";
    // isPreviewのKey文字列
    public static final String IS_PREVIEW_KEY = "isPreview";
    // AlarmEntityのKey文字列
    public static final String ALARM_ENTITY = "alarmEntity";

    // 各モードの定義
    // 音量パターン
    public static final int SOUND_MODE_NORMAL = 0;
    public static final int SOUND_MODE_LARGE_SLOWLY = 1;
    // マナーモード
    public static final int MANOR_MODE_FOLLOW_OS = 0;
    public static final int MANOR_MODE_INDEPENDENT = 1;
    // アラーム解除方法
    public static final int STOP_MODE_TAP = 0;
    public static final int STOP_MODE_ADDITION = 1;
}
