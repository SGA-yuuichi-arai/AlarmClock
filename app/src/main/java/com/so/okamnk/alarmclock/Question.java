package com.so.okamnk.alarmclock;

import java.util.Random;

public class Question {
    private String mQuestion;
    private String mAnswer;

    public static Question createRandomQuestion() {
        // 「a + b = c」 という問題を作る
        Random random = new Random();
        int a = random.nextInt(90) + 10;
        int b = random.nextInt(90) + 10;
        int c = a + b;

        Question obj = new Question();
        obj.mQuestion = "" + a + "+" + b + "=";
        obj.mAnswer = "" + c;
        return obj;
    }

    public String getQuestionString() {
        return mQuestion;
    }

    public boolean isCorrectAnswer(String answer) {
        return mAnswer.equals(answer);
    }
}
