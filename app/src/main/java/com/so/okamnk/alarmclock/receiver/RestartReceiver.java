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

public class RestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            // アラームの再設定
            AlarmDBAdapter alarmDBAdapter = new AlarmDBAdapter(context);
            ArrayList alarms = (ArrayList) alarmDBAdapter.getAll();
            AlarmRegistHelper alarmRegistHelper = AlarmRegistHelper.getInstance();
            alarmRegistHelper.unregistAsync(context, alarms, null);
            alarmRegistHelper.registAsync(context, alarms, null);

        }
    }
}
