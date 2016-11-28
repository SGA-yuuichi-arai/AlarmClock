package com.so.okamnk.alarmclock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * 文字列操作のユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class StringUtility {

    private static String DATE_FOMAT = "yyyy/MM/dd HH:mm:ss";

    /**
     * 指定文字列が、nullもしくは空文字かの判定します
     *
     * @param str 指定文字列
     * @return nullか空文字であればtrueを、そうでない場合はfalseを返す
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.isEmpty() == true) {
            return true;
        }
        return false;
    }

    /**
     * 文字列で表現された日付(yyyy/MM/dd HH:mm:ss)を、Calendar型に変換します<br>
     * フォーマットが異なる場合、ParseExceptionをthrowします
     *
     * @param dateStr 文字列の日付
     * @return Calendar型の指定日時
     * @throws ParseException
     */
    public static Calendar convertStringToCalendar(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FOMAT, Locale.JAPAN);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(dateStr));
        return calendar;
    }

    /**
     * Calendar型の日時を文字列(yyyy/MM/dd HH:mm:ss)に変換します
     *
     * @param calendar Calendar型の指定日時
     * @return yyyy/MM/dd HH:mm:ssフォーマットの文字列
     */
    public static String convertCalendarToString(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FOMAT, Locale.JAPAN);
        return formatter.format(calendar.getTime());
    }
}
