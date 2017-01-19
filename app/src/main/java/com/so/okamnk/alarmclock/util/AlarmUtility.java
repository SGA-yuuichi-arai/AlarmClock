package com.so.okamnk.alarmclock.util;

import android.content.Context;
import android.content.Intent;

import com.so.okamnk.alarmclock.AlarmService;
import com.so.okamnk.alarmclock.Define;

/**
 * サービス開始/停止のユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class AlarmUtility {
    public static void startAlarmService(Context context, int alarmId) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Define.ALARM_ID_KEY, alarmId);
        context.startService(intent);
    }

    public static void stopAlarmService(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        context.stopService(intent);
    }
}
