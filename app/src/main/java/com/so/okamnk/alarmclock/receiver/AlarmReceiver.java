package com.so.okamnk.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.so.okamnk.alarmclock.AlarmActivity;
import com.so.okamnk.alarmclock.Define;
import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;

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
        AlarmDBAdapter dbAdapter = new AlarmDBAdapter(context);
        AlarmEntity entity = dbAdapter.getAlarm(intent.getIntExtra(Define.ALARM_ID_KEY, 0));
        if (entity != null) {
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra(Define.IS_PREVIEW_KEY, false);
            activityIntent.putExtra(Define.ALARM_ENTITY, entity);
            context.startActivity(activityIntent);
        }
    }
}
