package com.so.okamnk.alarmclock.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * アラームアプリのユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class AlarmUtility {
    /**
     * 指定時刻と現在時刻を比較し、これから訪れる日時をCalendarで返す
     *
     * @param hour   時の指定
     * @param minute 分の指定
     * @return 指定時刻が現在時刻より前：本日の指定時刻<br>
     * 指定時刻が現在時刻を以降：翌日の指定時刻
     */
    public static Calendar getNearCalender(int hour, int minute) {
        Calendar nowDateCalender = Calendar.getInstance();
        Calendar argDateCalender = Calendar.getInstance();
        argDateCalender.set(nowDateCalender.get(Calendar.YEAR), nowDateCalender.get(Calendar.MONTH),
                nowDateCalender.get(Calendar.DATE), hour, minute, 0);

        if (argDateCalender.compareTo(nowDateCalender) < 0) {
            // 月末のDATE+1も、これで次月の1日になることを確認済み
            argDateCalender.set(Calendar.DATE, argDateCalender.get(Calendar.DATE) + 1);
            return argDateCalender;
        } else {
            return argDateCalender;
        }
    }

    /**
     * 指定時刻と現在時刻を比較し、これから訪れる直近の日時をCalendarで返す
     *
     * @param hour          時の指定
     * @param minute        分の指定
     * @param dayOfWeekList 曜日(日=1、月=2、火=3、水=4、木=5、金=6、土=7)のリスト
     * @return これから訪れる直近の日時
     */
    public static Calendar getNearCalender(int hour, int minute, List<Integer> dayOfWeekList) {
        ArrayList<Calendar> calenderList = new ArrayList<Calendar>();
        for (Integer dayOfWeek : dayOfWeekList) {
            Calendar calendar = getNearCalender(hour, minute, dayOfWeek);
            calenderList.add(calendar);
        }

        // ソートして先頭(一番若い日)を返す
        Collections.sort(calenderList);
        return calenderList.get(0);
    }

    /**
     * 指定時刻と現在時刻を比較し、これから訪れる直近の日時をCalendarで返す
     *
     * @param hour      時の指定
     * @param minute    分の指定
     * @param dayOfWeek 曜日の指定(日=1、月=2、火=3、水=4、木=5、金=6、土=7)
     * @return これから訪れる直近の日時
     */
    public static Calendar getNearCalender(int hour, int minute, int dayOfWeek) {
        Calendar nowDateCalender = Calendar.getInstance();
        Calendar argDateCalender = Calendar.getInstance();

        // 今週の指定曜日(SUN=1 ～ SAT=7)
        argDateCalender.set(nowDateCalender.get(Calendar.YEAR), nowDateCalender.get(Calendar.MONTH),
                nowDateCalender.get(Calendar.DATE), hour, minute, 0);
        argDateCalender.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        if (argDateCalender.compareTo(nowDateCalender) < 0) {
            argDateCalender.set(Calendar.DATE, argDateCalender.get(Calendar.DATE) + 7);
            return argDateCalender;
        } else {
            return argDateCalender;
        }
    }

    /**
     * 指定ミリ秒の間、処理を停止する<br>
     * 本関数は、Exceptionを隠蔽していますので、デバッグの時のみご利用ください
     *
     * @param milliSec ミリ秒
     */
    @Deprecated
    public static void threadSleep(long milliSec) {
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            // エラー出力のみ行う
            e.printStackTrace();
        }
    }
}
