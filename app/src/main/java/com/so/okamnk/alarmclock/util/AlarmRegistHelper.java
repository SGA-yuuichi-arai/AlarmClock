package com.so.okamnk.alarmclock.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.so.okamnk.alarmclock.receiver.AlarmReceiver;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * アラームアプリのアラーム登録、解除クラス
 * Created by arai on 2016/11/20.
 */

public class AlarmRegistHelper {

    private final int WEEKLY_INTERVAL = 7 * 24 * 60 * 60 * 1000;

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

    /**
     * コンストラクタ（非公開）ハンドラの初期化
     */
    private AlarmRegistHelper() {
        handler = new Handler();
    }


    /**
     * ファイナライザ　ハンドラの終了処理
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        if (handler != null) {
            handler.removeCallbacks(null);
            handler = null;
        }
    }

    /**
     * AlarmRegistHelperのインスタンスの取得
     *
     * @return AlarmRegistHelperインスタンス<br>
     */
    public static AlarmRegistHelper getInstance() {
        if (instance == null) {
            instance = new AlarmRegistHelper();
        }
        return instance;
    }

    /**
     * 非同期でアラーム登録を行う
     *
     * @param context       コンテキスト
     * @param alarmEntities 登録したいAlarmEntityのリスト
     * @param listener      登録処理の結果を受け取るリスナー
     */
    public void registAsync(final Context context, final ArrayList<AlarmEntity> alarmEntities, final OnAlarmRegistHelperListener listener) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                regist(context, alarmEntities, listener);
            }
        });
    }

    /**
     * 非同期でアラーム登録解除を行う
     *
     * @param context       コンテキスト
     * @param alarmEntities 解除したいAlarmEntityのリスト
     * @param listener      解除処理の結果を受け取るリスナー
     */
    public void unregistAsync(final Context context, final ArrayList<AlarmEntity> alarmEntities, final OnAlarmRegistHelperListener listener) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                unregist(context, alarmEntities, listener);
            }
        });
    }

    /**
     * 同期でアラーム登録を行う
     *
     * @param context       コンテキスト
     * @param alarmEntities 登録したいAlarmEntityのリスト
     * @param listener      登録処理の結果を受け取るリスナー
     */
    public void regist(Context context, ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

        for (AlarmEntity entity : alarmEntities) {

            if (isRepeat(entity)) {

                if (entity.getRepeatSunday()) {
                    setRepeatAlarm(context, entity, 1, listener);
                }
                if (entity.getRepeatMonday()) {
                    setRepeatAlarm(context, entity, 2, listener);
                }
                if (entity.getRepeatTuesday()) {
                    setRepeatAlarm(context, entity, 3, listener);
                }
                if (entity.getRepeatWednesday()) {
                    setRepeatAlarm(context, entity, 4, listener);
                }
                if (entity.getRepeatThrsday()) {
                    setRepeatAlarm(context, entity, 5, listener);
                }
                if (entity.getRepeatFriday()) {
                    setRepeatAlarm(context, entity, 6, listener);
                }
                if (entity.getRepeatSaturday()) {
                    setRepeatAlarm(context, entity, 7, listener);
                }

            } else {
                setAlarm(context, entity, listener);
            }
        }

        if (listener != null) {
            listener.onCompletion();
        }

    }

    /**
     * 同期でアラーム登録解除を行う
     *
     * @param context       コンテキスト
     * @param alarmEntities 解除したいAlarmEntityのリスト
     * @param listener      解除処理の結果を受け取るリスナー
     */
    public void unregist(Context context, ArrayList<AlarmEntity> alarmEntities, OnAlarmRegistHelperListener listener) {

        for (AlarmEntity entity : alarmEntities) {

            if (isRepeat(entity)) {

                if (entity.getRepeatSunday()) {
                    cancelRepeatAlarm(context, entity, 1, listener);
                }
                if (entity.getRepeatMonday()) {
                    cancelRepeatAlarm(context, entity, 2, listener);
                }
                if (entity.getRepeatTuesday()) {
                    cancelRepeatAlarm(context, entity, 3, listener);
                }
                if (entity.getRepeatWednesday()) {
                    cancelRepeatAlarm(context, entity, 4, listener);
                }
                if (entity.getRepeatThrsday()) {
                    cancelRepeatAlarm(context, entity, 5, listener);
                }
                if (entity.getRepeatFriday()) {
                    cancelRepeatAlarm(context, entity, 6, listener);
                }
                if (entity.getRepeatSaturday()) {
                    cancelRepeatAlarm(context, entity, 7, listener);
                }

            } else {
                cancelAlarm(context, entity, listener);
            }
        }

        if (listener != null) {
            listener.onCompletion();
        }
    }

    /**
     * アラームの登録状態を判定する
     *
     * @param context コンテキスト
     * @param entity  判定したいAlarmEntity
     * @return true = 登録済み, false = 未登録
     */
    public boolean isRegistered(Context context, AlarmEntity entity) {

        Intent intent = new Intent(context, AlarmReceiver.class);

        if (isRepeat(entity)) {

            if (entity.getRepeatSunday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 1);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatMonday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 2);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatTuesday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 3);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatWednesday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 4);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatThrsday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 5);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatFriday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 6);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }
            if (entity.getRepeatSaturday()) {
                intent.setType(entity.getAlarmId() + entity.getRegistDate() + 7);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
                if (pendingIntent == null) {
                    return false;
                }
            }

        } else {
            intent.setType(entity.getAlarmId() + entity.getRegistDate());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, -1, intent, PendingIntent.FLAG_NO_CREATE);
            if (pendingIntent == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * AlarmEntityに繰り返しが設定されているかを判定する
     *
     * @param entity 判定したいAlarmEntity
     * @return true = 繰り返しが設定済み, false = 繰り返しが未設定
     */
    private boolean isRepeat(AlarmEntity entity) {
        if (entity.getRepeatSunday()
                && entity.getRepeatMonday()
                && entity.getRepeatThrsday()
                && entity.getRepeatTuesday()
                && entity.getRepeatFriday()
                && entity.getRepeatSaturday()
                && entity.getRepeatWednesday()) {
            return true;
        }

        return false;
    }

    /**
     * アラーム登録処理(繰り返しなし)
     *
     * @param context  コンテキスト
     * @param entity   登録したいAlarmEntity
     * @param listener 登録処理の結果を受け取るリスナー
     */
    private void setAlarm(Context context, AlarmEntity entity, OnAlarmRegistHelperListener listener) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setType(entity.getAlarmId() + entity.getRegistDate());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        try {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Date alarmDate = StringUtility.convertStringToDate(entity.getAlarmTime());

            Calendar calendar = AlarmClockUtility.getNearCalender(alarmDate.getHours(), alarmDate.getMinutes());
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            if (listener != null) {
                listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_SUCCEEDED);
            }
        } catch (ParseException e) {
            if (listener != null) {
                listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_ERROR_UNKNOWN);
            }
        }
    }

    /**
     * アラーム登録処理(繰り返しあり)
     *
     * @param context  コンテキスト
     * @param entity   登録したいAlarmEntity
     * @param listener 登録処理の結果を受け取るリスナー
     */
    private void setRepeatAlarm(Context context, AlarmEntity entity, int dayOfWeek, OnAlarmRegistHelperListener listener) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setType(entity.getAlarmId() + entity.getRegistDate() + dayOfWeek);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        try {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Date alarmDate = StringUtility.convertStringToDate(entity.getAlarmTime());
            Calendar calendar = AlarmClockUtility.getNearCalender(alarmDate.getHours(), alarmDate.getMinutes(), dayOfWeek);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), WEEKLY_INTERVAL, pendingIntent);
            if (listener != null) {
                listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_SUCCEEDED);
            }
        } catch (ParseException e) {
            if (listener != null) {
                listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_ERROR_UNKNOWN);
            }
        }
    }

    /**
     * アラーム登録解除処理(繰り返しなし)
     *
     * @param context  コンテキスト
     * @param entity   登録したいAlarmEntity
     * @param listener 解除処理の結果を受け取るリスナー
     */
    private void cancelAlarm(Context context, AlarmEntity entity, OnAlarmRegistHelperListener listener) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setType(entity.getAlarmId() + entity.getRegistDate());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent.cancel();
        am.cancel(pendingIntent);
        if (listener != null) {
            listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_SUCCEEDED);
        }
    }

    /**
     * アラーム登録解除処理(繰り返しあり)
     *
     * @param context  コンテキスト
     * @param entity   解除したいAlarmEntity
     * @param listener 解除処理の結果を受け取るリスナー
     */
    private void cancelRepeatAlarm(Context context, AlarmEntity entity, int dayOfWeek, OnAlarmRegistHelperListener listener) {

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setType(entity.getAlarmId() + entity.getRegistDate() + dayOfWeek);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent.cancel();
        am.cancel(pendingIntent);
        if (listener != null) {
            listener.onRegistration(entity.getAlarmId(), RegistReturnCode.RET_SUCCEEDED);
        }
    }
}
