package com.so.okamnk.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmRegistHelper;

import java.util.ArrayList;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(intent.getAction())) {

            // アラームの再設定
            AlarmDBAdapter alarmDBAdapter = new AlarmDBAdapter(context);
            ArrayList alarms = (ArrayList) alarmDBAdapter.getAll();
            AlarmRegistHelper alarmRegistHelper = AlarmRegistHelper.getInstance();
            alarmRegistHelper.unregistAsync(context, alarms, null);
            alarmRegistHelper.registAsync(context, alarms, null);

        } else if (Intent.ACTION_PACKAGE_FULLY_REMOVED.equals(intent.getAction())) {

            // アラームの削除
            AlarmDBAdapter alarmDBAdapter = new AlarmDBAdapter(context);
            ArrayList alarms = (ArrayList) alarmDBAdapter.getAll();
            AlarmRegistHelper alarmRegistHelper = AlarmRegistHelper.getInstance();
            alarmRegistHelper.unregistAsync(context, alarms, null);
            alarmDBAdapter.deleteAll();

        }
    }
}
