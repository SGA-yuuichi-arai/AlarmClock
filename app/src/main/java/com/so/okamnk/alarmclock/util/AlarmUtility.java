package com.so.okamnk.alarmclock.util;

import android.content.Context;
import android.content.Intent;

import com.so.okamnk.alarmclock.AlarmService;

/**
 * サービス開始/停止のユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class AlarmUtility {
    static Intent intent = null;
    static Context context = null;

    public static void startAlarmService(Context con, String path, int soundMode, int manorMode) {
        intent = new Intent(con, AlarmService.class);
        context = con;
        context.startService(intent);
    }

    public static void stopAlarmService() {
        context.stopService(intent);
    }
}
