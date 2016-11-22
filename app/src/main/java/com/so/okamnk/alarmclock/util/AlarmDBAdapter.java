package com.so.okamnk.alarmclock.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmDBAdapter {

    public static final String TABLE_NAME = "tbl_alarms";
    public static final String COL_ID = "alarm_id";
    public static final String COL_REGISTERED_DATE = "regist_date";
    public static final String COL_ALARM_NAME = "alarm_name";
    public static final String COL_ALARM_TIME = "alarm_time";
    public static final String COL_SOUND_PATH = "sound_path";
    public static final String COL_VOLUME_PATTERN = "sound_mode";
    public static final String COL_VOLUME = "sound_volume";
    public static final String COL_MANOR_MODE = "manor_mode";
    public static final String COL_HOW_TO_STOP = "stop_mode";
    public static final String COL_SNOOZE_INTERVAL = "snooze_interval";
    public static final String COL_SNOOZE_NUM = "snooze_num";
    public static final String COL_REPEAT_MONDAY = "repeat_monday";
    public static final String COL_REPEAT_TUESDAY = "repeat_tuesday";
    public static final String COL_REPEAT_WEDNESDAY = "repeat_wednesday";
    public static final String COL_REPEAT_THURSDAY = "repeat_thursday";
    public static final String COL_REPEAT_FRIDAY = "repeat_friday";
    public static final String COL_REPEAT_SATURDAY = "repeat_saturday";
    public static final String COL_REPEAT_SUNDAY = "repeat_sunday";
    public static final String COL_ALARM_ENABLED = "alarm_enabled";

    private Context context;
    private AlarmDBHelper dbHelper = null;

    public AlarmDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new AlarmDBHelper(this.context);
    }

    public AlarmEntity saveAlarm(AlarmEntity entity) {
        ContentValues values = new ContentValues();
        values.put(COL_ALARM_NAME, entity.getAlarmName());
        values.put(COL_ALARM_TIME, entity.getAlarmTime());
        values.put(COL_SOUND_PATH, entity.getSoundPath());
        values.put(COL_VOLUME_PATTERN, entity.getSoundMode());
        values.put(COL_VOLUME, entity.getSoundVolume());
        values.put(COL_MANOR_MODE, entity.getManorMode());
        values.put(COL_HOW_TO_STOP, entity.getStopMode());
        values.put(COL_SNOOZE_INTERVAL, entity.getSnoozeInterval());
        values.put(COL_SNOOZE_NUM, entity.getSnoozeNum());

        final int REPEAT_TRUE = 1;
        final int REPEAT_FALSE = 0;

        int repeatMonday = REPEAT_TRUE;
        if (!entity.getRepeatMonday()) {
            repeatMonday = REPEAT_FALSE;
        }

        int repeatTuesday = REPEAT_TRUE;
        if (!entity.getRepeatTuesday()) {
            repeatTuesday = REPEAT_FALSE;
        }

        int repeatWednesday = REPEAT_TRUE;
        if (!entity.getRepeatWednesday()) {
            repeatWednesday = REPEAT_FALSE;
        }

        int repeatThrsday = REPEAT_TRUE;
        if (!entity.getRepeatThrsday()) {
            repeatThrsday = REPEAT_FALSE;
        }

        int repeatFriday = REPEAT_TRUE;
        if (!entity.getRepeatFriday()) {
            repeatFriday = REPEAT_FALSE;
        }

        int repeatSaturday = REPEAT_TRUE;
        if (!entity.getRepeatSaturday()) {
            repeatSaturday = REPEAT_FALSE;
        }

        int repeatSunday = REPEAT_TRUE;
        if (!entity.getRepeatSunday()) {
            repeatSunday = REPEAT_FALSE;
        }

        int alarmEnabled = REPEAT_TRUE;
        if (!entity.getAlarmEnabled()) {
            alarmEnabled = REPEAT_FALSE;
        }

        values.put(COL_REPEAT_MONDAY, repeatMonday);
        values.put(COL_REPEAT_TUESDAY, repeatTuesday);
        values.put(COL_REPEAT_WEDNESDAY, repeatWednesday);
        values.put(COL_REPEAT_THURSDAY, repeatThrsday);
        values.put(COL_REPEAT_FRIDAY, repeatFriday);
        values.put(COL_REPEAT_SATURDAY, repeatSaturday);
        values.put(COL_REPEAT_SUNDAY, repeatSunday);
        values.put(COL_ALARM_ENABLED, alarmEnabled);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int row = entity.getAlarmId();
        try {
            if (row == 0) {
                values.put(COL_REGISTERED_DATE, getCurrentDate());
                row = (int) db.insert(TABLE_NAME, null, values);
            } else {
                db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(row)});
            }
        } finally {
            db.close();
        }

        return getAlarm(row);
    }

    public void deleteAlarm(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, COL_ID + "=" + String.valueOf(id), null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.delete(TABLE_NAME, null, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
    }

    public List<AlarmEntity> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor query = null;
        List<AlarmEntity> itemList = new ArrayList<AlarmEntity>();

        try {
            query = db.query(TABLE_NAME, null, null, null, null, null, COL_REGISTERED_DATE + " ASC");
            query.moveToFirst();
            while (!query.isAfterLast()) {
                itemList.add(getAlarmEntity(query));
                query.moveToNext();
            }
            query.close();
        } finally {
            db.close();
        }

        return itemList;
    }

    public AlarmEntity getAlarm(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor query;
        AlarmEntity entity = null;

        try {
            query = db.query(TABLE_NAME, null, COL_ID + "=" + String.valueOf(id), null, null, null, null, null);
            query.moveToFirst();
            entity = getAlarmEntity(query);
            query.close();
        } finally {
            db.close();
        }

        return entity;
    }

    public List<AlarmEntity> search(String condition) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor query;
        List<AlarmEntity> itemList = new ArrayList<AlarmEntity>();

        try {
            query = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ?", new String[]{condition});
            query.moveToFirst();
            while (!query.isAfterLast()) {
                itemList.add(getAlarmEntity(query));
                query.moveToNext();
            }
            query.close();
        } finally {
            db.close();
        }
        return itemList;
    }

    private AlarmEntity getAlarmEntity(Cursor cursor) {

        boolean repeatMonday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_MONDAY)) == 0) {
            repeatMonday = false;
        }

        boolean repeatTuesday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_TUESDAY)) == 0) {
            repeatTuesday = false;
        }

        boolean repeatWednesday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_WEDNESDAY)) == 0) {
            repeatWednesday = false;
        }

        boolean repeatThrsday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_THURSDAY)) == 0) {
            repeatThrsday = false;
        }

        boolean repeatFriday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_FRIDAY)) == 0) {
            repeatFriday = false;
        }

        boolean repeatSaturday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_SATURDAY)) == 0) {
            repeatSaturday = false;
        }

        boolean repeatSunday = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_REPEAT_SUNDAY)) == 0) {
            repeatSunday = false;
        }

        boolean alarmEnabled = true;
        if (cursor.getInt(cursor.getColumnIndex(COL_ALARM_ENABLED)) == 0) {
            alarmEnabled = false;
        }

        return new AlarmEntity(
                cursor.getInt(cursor.getColumnIndex(COL_ID)),
                cursor.getString(cursor.getColumnIndex(COL_REGISTERED_DATE)),
                cursor.getString(cursor.getColumnIndex(COL_ALARM_NAME)),
                cursor.getString(cursor.getColumnIndex(COL_ALARM_TIME)),
                cursor.getString(cursor.getColumnIndex(COL_SOUND_PATH)),
                cursor.getInt(cursor.getColumnIndex(COL_VOLUME_PATTERN)),
                cursor.getInt(cursor.getColumnIndex(COL_VOLUME)),
                cursor.getInt(cursor.getColumnIndex(COL_MANOR_MODE)),
                cursor.getInt(cursor.getColumnIndex(COL_HOW_TO_STOP)),
                cursor.getInt(cursor.getColumnIndex(COL_SNOOZE_INTERVAL)),
                cursor.getInt(cursor.getColumnIndex(COL_SNOOZE_NUM)),
                repeatSunday,
                repeatMonday,
                repeatTuesday,
                repeatWednesday,
                repeatThrsday,
                repeatFriday,
                repeatSaturday,
                alarmEnabled);
    }

    private static String getCurrentDate() {
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }
}
