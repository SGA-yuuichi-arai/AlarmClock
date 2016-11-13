package com.so.okamnk.alarmclock.util;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmRegistHelper {

    public enum RegistReturnCode {
        RET_SUCCEEDED,
        RET_ERROR_EXIST,
        RET_ERROR_UNKNOWN
    }

    interface OnAlarmRegistHelperListener {
        void onRegistration(int alarmID, RegistReturnCode returnCode);

        void onCompletion();
    }

    private static AlarmRegistHelper instance;
    private Handler handler;

    private AlarmRegistHelper() {
        handler = new Handler();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (handler != null) {
            handler.removeCallbacks(null);
            handler = null;
        }
    }

    public static AlarmRegistHelper getInstance() {
        if (instance == null) {
            instance = new AlarmRegistHelper();
        }
        return instance;
    }

    public void registAsync(ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

    }

    public void unregistAsync(ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

    }

    public void regist(ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

    }

    public void unregist(ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

    }

    public boolean isRegistered(AlarmEntity alarmEntity) {
        return false;
    }

}
