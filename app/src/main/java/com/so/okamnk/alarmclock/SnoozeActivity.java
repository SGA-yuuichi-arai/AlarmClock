package com.so.okamnk.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.so.okamnk.alarmclock.util.AlarmEntity;
import com.so.okamnk.alarmclock.util.AlarmUtility;
import com.so.okamnk.alarmclock.util.StringUtility;

import java.util.Calendar;

public class SnoozeActivity extends AppCompatActivity implements View.OnClickListener {

    private Question question;
    private Handler guiThreadHandler;
    private Calendar baseTimeCalender;

    private AlarmEntity alarmEntity;
    private boolean previewFlg;
    private int remainingTime;

    // UIオブジェクト
    private Button passButton;
    private Button answerButton;
    private TextView nowTimeTextView;
    private TextView remainingTimeTextView;
    private TextView questionTextView;
    private EditText anserEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);

        this.question = Question.createRandomQuestion();
        this.alarmEntity = (AlarmEntity) getIntent().getSerializableExtra(Define.ALARM_ENTITY);
        this.previewFlg = getIntent().getBooleanExtra(Define.IS_PREVIEW_KEY, false);
        this.remainingTime = alarmEntity.getSnoozeInterval();

        // 現在時刻
        baseTimeCalender = Calendar.getInstance();
        nowTimeTextView = (TextView) findViewById(R.id.snooze_text_nowTime);
        nowTimeTextView.setText(getFormatingNowTime());

        // 残り時間
        remainingTimeTextView = (TextView) findViewById(R.id.snooze_text_remainingTime);
        remainingTimeTextView.setText(getFormatingRemainingTime(this.remainingTime));

        // 問題文
        questionTextView = (TextView) findViewById(R.id.snooze_text_question);
        questionTextView.setText(question.getQuestionString());

        // 回答欄
        anserEditText = (EditText) findViewById(R.id.snooze_edit_answer);
        anserEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        anserEditText.setText("");
        anserEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    answerCalcation();
                }
                return false;
            }
        });

        // パスボタン
        passButton = (Button) findViewById(R.id.snooze_button_pass);
        passButton.setOnClickListener(this);
        // 回答ボタン
        answerButton = (Button) findViewById(R.id.snooze_button_answer);
        answerButton.setOnClickListener(this);

        if (this.alarmEntity.getStopMode() == Define.STOP_MODE_TAP) {
            questionTextView.setVisibility(View.INVISIBLE);
            anserEditText.setVisibility(View.INVISIBLE);
            passButton.setVisibility(View.INVISIBLE);
            answerButton.setText("停止");
        }

        // スレッドで、残り時間のカウントダウンと、現在時刻の更新
        this.guiThreadHandler = new Handler();
        countDownReminingTime();
    }

    private String getFormatingNowTime() {

        return baseTimeCalender.get(Calendar.HOUR_OF_DAY) + "時" + baseTimeCalender.get(Calendar.MINUTE) + "分";
    }

    private String getFormatingRemainingTime(int reminingTime) {

        if (reminingTime <= 0) {
            return "あと 0秒";
        }

        int minute = reminingTime / 60;
        int second = reminingTime % 60;
        if (minute > 0) {
            return "あと " + minute + "分 " + second + "秒";
        } else {
            return "あと " + second + "秒";
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.snooze_button_pass:
                    passQuestion();
                    break;
                case R.id.snooze_button_answer:
                    answerCalcation();
                    break;
                default:
                    break;
            }
        }
    }

    private void passQuestion() {
        // 問題を再作成
        this.anserEditText.setText("");
        this.question = Question.createRandomQuestion();
        this.questionTextView.setText(this.question.getQuestionString());
    }

    private void answerCalcation() {
        if (this.alarmEntity.getStopMode() == Define.STOP_MODE_ADDITION) {
            // 回答の確認
            String userAnserStr = this.anserEditText.getText().toString();
            if (StringUtility.isNullOrEmpty(userAnserStr) == true) {
                // 不正解と同じメッセージで
                Toast.makeText(this, "不正解です。", Toast.LENGTH_LONG).show();
                return;
            }

            if (this.question.isCorrectAnswer(userAnserStr) == false) {
                Toast.makeText(this, "不正解です。", Toast.LENGTH_LONG).show();
                this.anserEditText.setText("");
                return;
            }
        }

        // 停止
        AlarmUtility.stopAlarmService(getApplicationContext());

        finish();
    }

    public void setNowTimeTextAsync() {
        guiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                nowTimeTextView.setText(getFormatingNowTime());
            }
        });
    }

    public void setRemainingTimeTextAsync() {
        guiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                remainingTimeTextView.setText(getFormatingRemainingTime(remainingTime));
            }
        });
    }

    private void moveAlarmActivty() {
        Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
        intent.putExtra(Define.ALARM_ENTITY, this.alarmEntity);
        intent.putExtra(Define.IS_PREVIEW_KEY, this.previewFlg);
        startActivity(intent);
    }

    private void countDownReminingTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    setRemainingTimeTextAsync();
                    setNowTimeTextAsync();

                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    baseTimeCalender.add(Calendar.SECOND, 1);
                    remainingTime = remainingTime - 1;

                    if (remainingTime <= 0) {
                        break;
                    }
                }
                // インターバルをカウントし終えたら、アラーム画面に遷移する
                moveAlarmActivty();

            }
        }).start();
    }
}