package com.so.okamnk.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.so.okamnk.alarmclock.AlarmActivity;

/**
 * アラームアプリのアラームレシーバクラス
 * Created by arai on 2016/11/20.
 */

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * レシーバ起動時に呼ばれる　AlarmActivityを起動させる
     *
     * @param context コンテキスト
     * @param intent  起動元から受け渡されるintent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        startAlarmActivity(context, intent);
    }

    /**
     * AlarmActivityを起動させる
     *
     * @param context コンテキスト
     */
    private void startAlarmActivity(Context context, Intent intent) {

        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.putExtra("isPreview", false);
        activityIntent.putExtra("alarmID", intent.getIntExtra("alarmID", 0));
        context.startActivity(activityIntent);
    }
}
