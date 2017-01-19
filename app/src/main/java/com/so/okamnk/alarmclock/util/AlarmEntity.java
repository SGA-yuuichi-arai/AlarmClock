package com.so.okamnk.alarmclock.util;

import java.io.Serializable;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmEntity implements Serializable {
    
    private int alarmId = 0;
    private String registDate = "";
    private String alarmName = "";
    private String alarmTime = "";
    private String soundPath = "";
    private int soundMode = 0;
    private int soundVolume = 0;
    private int manorMode = 0;
    private int stopMode = 0;
    private int snoozeInterval = 0;
    private int snoozeNum = 0;
    private boolean repeatSunday = false;
    private boolean repeatMonday = false;
    private boolean repeatTuesday = false;
    private boolean repeatWednesday = false;
    private boolean repeatThrsday = false;
    private boolean repeatFriday = false;
    private boolean repeatSaturday = false;
    private boolean alarmEnabled = false;

    /**
     * デフォルトコンストラクタ
     */
    public AlarmEntity() {
    }

    /**
     * 全てのアラーム情報が登録できるコンストラクタ
     *
     * @param alarmId
     * @param registDate
     * @param alarmName
     * @param alarmTime
     * @param soundPath
     * @param soundMode
     * @param soundVolume
     * @param manorMode
     * @param stopMode
     * @param snoozeInterval
     * @param snoozeNum
     * @param repeatSunday
     * @param repeatMonday
     * @param repeatTuesday
     * @param repeatWednesday
     * @param repeatThrsday
     * @param repeatFriday
     * @param repeatSaturday
     * @param alarmEnabled
     */
    public AlarmEntity(int alarmId, String registDate, String alarmName, String alarmTime, String soundPath, int soundMode,
                       int soundVolume, int manorMode, int stopMode, int snoozeInterval, int snoozeNum, boolean repeatSunday,
                       boolean repeatMonday, boolean repeatTuesday, boolean repeatWednesday, boolean repeatThrsday,
                       boolean repeatFriday, boolean repeatSaturday, boolean alarmEnabled) {
        this.alarmId = alarmId;
        this.registDate = registDate;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.soundPath = soundPath;
        this.soundMode = soundMode;
        this.soundVolume = soundVolume;
        this.manorMode = manorMode;
        this.stopMode = stopMode;
        this.snoozeInterval = snoozeInterval;
        this.snoozeNum = snoozeNum;
        this.repeatSunday = repeatSunday;
        this.repeatMonday = repeatMonday;
        this.repeatTuesday = repeatTuesday;
        this.repeatWednesday = repeatWednesday;
        this.repeatThrsday = repeatThrsday;
        this.repeatFriday = repeatFriday;
        this.repeatSaturday = repeatSaturday;
        this.alarmEnabled = alarmEnabled;
    }

    /**
     * NULLを許可しないカラムをセットできるコンストラクタ
     *
     * @param alarmId
     * @param registDate
     * @param alarmName
     * @param alarmTime
     * @param soundPath
     * @param soundMode
     * @param soundVolume
     * @param manorMode
     * @param alarmEnabled
     */
    public AlarmEntity(int alarmId, String registDate, String alarmName, String alarmTime, String soundPath, int soundMode,
                       int soundVolume, int manorMode, boolean alarmEnabled) {
        this.alarmId = alarmId;
        this.registDate = registDate;
        this.alarmName = alarmName;
        this.alarmTime = alarmTime;
        this.soundPath = soundPath;
        this.soundMode = soundMode;
        this.soundVolume = soundVolume;
        this.manorMode = manorMode;
        this.alarmEnabled = alarmEnabled;
    }

    /**
     * 全てのアラーム情報を、文字列に整形して、1行で返します。
     *
     * @return 整形した文字列
     */
    public String toString() {
        String formatingString = "";
        formatingString = "alarmId=" + formatingString + String.valueOf(this.alarmId);
        formatingString = ", registDate=" + formatingString + this.registDate;
        formatingString = ", alarmName=" + formatingString + this.alarmName;
        formatingString = ", alarmTime=" + formatingString + this.alarmTime;
        formatingString = ", soundPath=" + formatingString + this.soundPath;
        formatingString = ", soundMode=" + formatingString + String.valueOf(this.soundMode);
        formatingString = ", soundVolume=" + formatingString + String.valueOf(this.soundVolume);
        formatingString = ", manorMode=" + formatingString + String.valueOf(this.manorMode);
        formatingString = ", stopMode=" + formatingString + String.valueOf(this.stopMode);
        formatingString = ", snoozeInterval=" + formatingString + String.valueOf(this.snoozeInterval);
        formatingString = ", snoozeNum=" + formatingString + String.valueOf(this.snoozeNum);
        formatingString = ", repeatSunday=" + formatingString + String.valueOf(this.repeatSunday);
        formatingString = ", repeatMonday=" + formatingString + String.valueOf(this.repeatMonday);
        formatingString = ", repeatTuesday=" + formatingString + String.valueOf(this.repeatTuesday);
        formatingString = ", repeatWednesday=" + formatingString + String.valueOf(this.repeatWednesday);
        formatingString = ", repeatThrsday=" + formatingString + String.valueOf(this.repeatThrsday);
        formatingString = ", repeatFriday=" + formatingString + String.valueOf(this.repeatFriday);
        formatingString = ", repeatSaturday=" + formatingString + String.valueOf(this.repeatSaturday);
        formatingString = ", alarmEnabled=" + formatingString + String.valueOf(this.alarmEnabled);
        return formatingString;
    }


    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getAlarmId() {
        return this.alarmId;
    }

    public void setRegistDate(String registDate) {
        this.registDate = registDate;
    }

    public String getRegistDate() {
        return this.registDate;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmName() {
        return this.alarmName;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmTime() {
        return this.alarmTime;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return this.soundPath;
    }

    public void setSoundMode(int soundMode) {
        this.soundMode = soundMode;
    }

    public int getSoundMode() {
        return this.soundMode;
    }

    public void setSoundVolume(int soundVolume) {
        this.soundVolume = soundVolume;
    }

    public int getSoundVolume() {
        return this.soundVolume;
    }

    public void setManorMode(int manorMode) {
        this.manorMode = manorMode;
    }

    public int getManorMode() {
        return this.manorMode;
    }

    public void setStopMode(int stopMode) {
        this.stopMode = stopMode;
    }

    public int getStopMode() {
        return this.stopMode;
    }

    public void setSnoozeInterval(int snoozeInterval) {
        this.snoozeInterval = snoozeInterval;
    }

    public int getSnoozeInterval() {
        return this.snoozeInterval;
    }

    public void setSnoozeNum(int snoozeNum) {
        this.snoozeNum = snoozeNum;
    }

    public int getSnoozeNum() {
        return this.snoozeNum;
    }

    public void setRepeatSunday(boolean repeatSunday) {
        this.repeatSunday = repeatSunday;
    }

    public boolean getRepeatSunday() {
        return this.repeatSunday;
    }

    public void setRepeatMonday(boolean repeatMonday) {
        this.repeatMonday = repeatMonday;
    }

    public boolean getRepeatMonday() {
        return this.repeatMonday;
    }

    public void setRepeatTuesday(boolean repeatTuesday) {
        this.repeatTuesday = repeatTuesday;
    }

    public boolean getRepeatTuesday() {
        return this.repeatTuesday;
    }

    public void setRepeatWednesday(boolean repeatWednesday) {
        this.repeatWednesday = repeatWednesday;
    }

    public boolean getRepeatWednesday() {
        return this.repeatWednesday;
    }

    public void setRepeatThrsday(boolean repeatThrsday) {
        this.repeatThrsday = repeatThrsday;
    }

    public boolean getRepeatThrsday() {
        return this.repeatThrsday;
    }

    public void setRepeatFriday(boolean repeatFriday) {
        this.repeatFriday = repeatFriday;
    }

    public boolean getRepeatFriday() {
        return this.repeatFriday;
    }

    public void setRepeatSaturday(boolean repeatSaturday) {
        this.repeatSaturday = repeatSaturday;
    }

    public boolean getRepeatSaturday() {
        return this.repeatSaturday;
    }

    public void setAlarmEnabled(boolean alarmEnabled) {
        this.alarmEnabled = alarmEnabled;
    }

    public boolean getAlarmEnabled() {
        return this.alarmEnabled;
    }
}
