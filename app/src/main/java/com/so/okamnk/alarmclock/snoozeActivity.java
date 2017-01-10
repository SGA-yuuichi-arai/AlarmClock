package com.so.okamnk.alarmclock;

import android.app.Activity;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Created by masa nomoto on 2016/11/21.
 */

public class SnoozeActivity extends AppCompatActivity {

    Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        question = Question.createRandomQuestion();
    }


    private String getFormatingNowTime(Calendar calendar) {
        return calendar.get(Calendar.HOUR) + "時" + calendar.get(Calendar.MINUTE) + "分";
    }

    private String getFormatingRemainingTime(){
        return "あと00分00秒";
    }

    private String getFormatingQuestion(int firstNum, int secondNum){
        return firstNum + "+" + secondNum + "=";
    }

}