package com.so.okamnk.alarmclock.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarmclock.db";
    private static final int DATABASE_VERSION = 1;

    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            StringBuilder createSql = new StringBuilder();
            createSql.append("create table " + AlarmDBAdapter.TABLE_NAME + " (");
            createSql.append(AlarmDBAdapter.COL_ID + " integer primary key autoincrement not null unique, ");
            createSql.append(AlarmDBAdapter.COL_REGISTERED_DATE + " text not null, ");
            createSql.append(AlarmDBAdapter.COL_ALARM_NAME + " text not null, ");
            createSql.append(AlarmDBAdapter.COL_ALARM_TIME + " text not null, ");
            createSql.append(AlarmDBAdapter.COL_SOUND_PATH + " text not null, ");
            createSql.append(AlarmDBAdapter.COL_VOLUME_PATTERN + " integer not null, ");
            createSql.append(AlarmDBAdapter.COL_VOLUME + " integer not null, ");
            createSql.append(AlarmDBAdapter.COL_MANOR_MODE + " integer not null, ");
            createSql.append(AlarmDBAdapter.COL_HOW_TO_STOP + " integer, ");
            createSql.append(AlarmDBAdapter.COL_SNOOZE_INTERVAL + " integer, ");
            createSql.append(AlarmDBAdapter.COL_SNOOZE_NUM + " integer, ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_MONDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_MONDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_TUESDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_TUESDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_WEDNESDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_WEDNESDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_THURSDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_THURSDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_FRIDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_FRIDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_SATURDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_SATURDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_REPEAT_SUNDAY + " integer" + " check(" + AlarmDBAdapter.COL_REPEAT_SUNDAY + " IN (0, 1)) " + ", ");
            createSql.append(AlarmDBAdapter.COL_ALARM_ENABLED + " integer" + " check(" + AlarmDBAdapter.COL_ALARM_ENABLED + " IN (0, 1)) " + "not null, ");
            createSql.append(");");
            db.execSQL(createSql.toString());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.beginTransaction();
        try {
            db.execSQL("DROP TABLE IF EXISTS " + AlarmDBAdapter.TABLE_NAME);
            onCreate(db);
        } finally {
            db.endTransaction();
        }
    }
}

