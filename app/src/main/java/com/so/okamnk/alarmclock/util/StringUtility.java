package com.so.okamnk.alarmclock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 文字列操作のユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class StringUtility {

    private static String DATE_FOMAT = "yyyyMMddTHHmmss";

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
     * Date型の日時を文字列(yyyyMMddTHHmmss)に変換します
     *
     * @param date Date型の指定日時
     * @return yyyyMMddTHHmmssフォーマットの文字列
     */
    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FOMAT);
        return formatter.format(date);
    }

    /**
     * 文字列で表現された日付(yyyyMMddTHHmmss)を、Calendar型に変換します<br>
     * フォーマットが異なる場合、ParseExceptionをthrowします
     *
     * @param dateStr 文字列の日付
     * @return Calendar型の指定日時
     * @throws ParseException
     */
    public static Calendar convertStringToCalendar(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FOMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatter.parse(dateStr));
        return calendar;
    }
}
