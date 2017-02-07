package com.so.okamnk.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.so.okamnk.alarmclock.util.AlarmEntity;

public class AlarmActivity extends AppCompatActivity {
    private TextView mTextAlarmName;
    private TextView mTextQuestion;
    private EditText mEditAnswer;
    private Button mButtonPass;
    private Button mButtonAnswer;

    private boolean mIsPreview;
    private AlarmEntity mAlarmEntity;

    private Question mQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        TextView textAlarmName = (TextView) findViewById(R.id.text_alarmName);
        TextView textQuestion = (TextView) findViewById(R.id.text_question);
        EditText editAnswer = (EditText) findViewById(R.id.edit_answer);
        Button buttonPass = (Button) findViewById(R.id.button_pass);
        Button buttonSnooze = (Button) findViewById(R.id.button_snooze);
        Button buttonAnswer = (Button) findViewById(R.id.button_answer);

        mTextAlarmName = textAlarmName;
        mTextQuestion = textQuestion;
        mEditAnswer = editAnswer;
        mButtonPass = buttonPass;
        mButtonAnswer = buttonAnswer;

        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPass();
            }
        });
        buttonSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSnooze();
            }
        });
        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswer();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // ソフトキーボードで決定押したとき回答扱いにする
        mEditAnswer.setOnKeyListener(new View.OnKeyListener() {
                                         @Override
                                         public boolean onKey(View v, int keyCode, KeyEvent event) {
                                             if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                                 onAnswer();
                                                 return true;
                                             }
                                             return false;
                                         }
                                     }
        );

        Intent intent = getIntent();
        boolean isPreview = intent.getBooleanExtra(Define.IS_PREVIEW_KEY, false);
        AlarmEntity alarmEntity = (AlarmEntity) intent.getSerializableExtra(Define.ALARM_ENTITY);

        onInit(isPreview, alarmEntity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void onInit(boolean isPreview, AlarmEntity alarmEntity) {
        mIsPreview = isPreview;
        mAlarmEntity = alarmEntity;

        // 問題とコントロール
        mTextAlarmName.setText(mAlarmEntity.getAlarmName());
        if (mAlarmEntity.getStopMode() == Define.STOP_MODE_ADDITION) {
            mQuestion = Question.createRandomQuestion();

            mTextQuestion.setText(mQuestion.getQuestionString());
            mEditAnswer.setText("");
            mButtonAnswer.setText("回答");

            mTextQuestion.setVisibility(View.VISIBLE);
            mEditAnswer.setVisibility(View.VISIBLE);
            mButtonPass.setVisibility(View.VISIBLE);
        } else {
            mQuestion = null;

            mTextQuestion.setText("");
            mEditAnswer.setText("");
            mButtonAnswer.setText("停止");

            mTextQuestion.setVisibility(View.GONE);
            mEditAnswer.setVisibility(View.GONE);
            mButtonPass.setVisibility(View.GONE);
        }

        startAlarm();
    }

    void onPass() {
        mQuestion = Question.createRandomQuestion();
        mTextQuestion.setText(mQuestion.getQuestionString());

        mEditAnswer.setText("");
    }

    void onSnooze() {
        stopAlarm();

        Intent intent = new Intent(getApplicationContext(), SnoozeActivity.class);
        intent.putExtra(Define.IS_PREVIEW_KEY, mIsPreview);
        intent.putExtra(Define.ALARM_ENTITY, mAlarmEntity);
        startActivity(intent);

        // この画面にはもう戻れないように終了させる
        finish();
    }

    void onAnswer() {
        if (mAlarmEntity.getStopMode() == Define.STOP_MODE_ADDITION) {
            String answer = mEditAnswer.getText().toString();

            if (mQuestion.isCorrectAnswer(answer)) {
                stopAlarm();
                finish();
            } else {
                Toast.makeText(this, "不正解です。", Toast.LENGTH_SHORT).show();
                mEditAnswer.setText("");
            }
        } else {
            stopAlarm();
            finish();
        }
    }

    public void startAlarm() {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Define.IS_PREVIEW_KEY, mIsPreview);
        intent.putExtra(Define.ALARM_ENTITY, mAlarmEntity);
        context.startService(intent);
    }

    public void stopAlarm() {
        Context context = getApplicationContext();
        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
        context.stopService(intent);
    }
}
