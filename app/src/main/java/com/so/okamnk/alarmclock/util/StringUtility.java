package com.so.okamnk.alarmclock.util;

/**
 * 文字列操作のユーティリティクラス
 * Created by kato on 2016/11/14.
 */

public class StringUtility {

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
}
