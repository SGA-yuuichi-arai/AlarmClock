package com.so.okamnk.alarmclock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;
import com.so.okamnk.alarmclock.util.AlarmMediaPlayer;

public class AlarmActivity extends AppCompatActivity {
    private TextView mTextAlarmName;
    private TextView mTextQuestion;
    private EditText mEditAnswer;
    private Button mButtonPass;
    private Button mButtonAnswer;

    AlarmEntity.STOP_MODE mStopMode;
    private Question mQuestion;

    private AlarmMediaPlayer mMediaPlayer;
    private Vibrator mVibrator;
    private int mVolume;
    AlarmEntity.SOUND_MODE mSoundMode;
    AlarmEntity.MANOR_MODE mManorMode;

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

        // TODO : onInitは誰が呼ぶ？alaramIDは誰が渡してくる？
        // TODO : プレビューの時どうやってデータ受けとる？
        onInit(getApplicationContext(), 0);
    }

    void onInit(Context context, int alarmID) {
        // アラーム情報
        String alarmName = "アラーム名";
        AlarmEntity.STOP_MODE stopMode = AlarmEntity.STOP_MODE.TAP;
        String soundPath = "";
        AlarmEntity.SOUND_MODE soundMode = AlarmEntity.SOUND_MODE.NORMAL;
        int soundVolume = 1;
        AlarmEntity.MANOR_MODE manorMode = AlarmEntity.MANOR_MODE.FOLLOW_OS;

        if (alarmID == 0) {     // TODO : DBが使えるようになったらこっちのパスは消す
            alarmName = "アラームテスト";
            stopMode = AlarmEntity.STOP_MODE.TAP;
            soundPath = "";
            soundMode = AlarmEntity.SOUND_MODE.NORMAL;
            manorMode = AlarmEntity.MANOR_MODE.FOLLOW_OS;
        } else {
            AlarmDBAdapter db = new AlarmDBAdapter(context);
            AlarmEntity alarmEntity = db.getAlarm(alarmID);
            alarmEntity.getAlarmName();

            alarmName = alarmEntity.getAlarmName();
            //stopMode = alarmEntity.getStopMode();         // TODO : enum型にしてもらう
            soundPath = alarmEntity.getSoundPath();
            //soundMode = alarmEntity.getSoundMode();       // TODO : enum型にしてもらう
            soundVolume = alarmEntity.getSoundVolume();
            //manorMode = alarmEntity.getManorMode();       // TODO : enum型にしてもらう
        }


        mStopMode = stopMode;

        // コントロール
        mTextAlarmName.setText(alarmName);
        if (mStopMode == AlarmEntity.STOP_MODE.ADDITION) {
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

        // 再生
        playAlarm();
    }

    void onPass() {
        mQuestion = Question.createRandomQuestion();
        mTextQuestion.setText(mQuestion.getQuestionString());

        mEditAnswer.setText("");
    }

    void onSnooze() {
        stopAlarm();

        Intent intent = new Intent(getApplicationContext(), SnoozeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);      // TODO : 落ちる？
    }

    void onAnswer() {
        if (mStopMode == AlarmEntity.STOP_MODE.ADDITION) {
            String answer = mEditAnswer.getText().toString();

            if (mQuestion.isCorrectAnswer(answer)) {
                stopAlarm();
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("不正解です");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        } else {
            stopAlarm();
            finish();
        }
    }


    /**
     * アラームを再生する。
     * マナーモード変更があった場合は本メソッドを呼び出すことで、再生内容が更新される。
     */
    private void playAlarm() {
        // 現在の状態
        boolean isPlayingSound = false;
        boolean isPlayingVibration = false;

        // 再生するべきかどうか
        boolean shouldPlaySound = false;
        boolean shouldPlayVibration = false;

        // mMediaPlayer = AlarmMediaPlayer.getInstance();
        // mMediaPlayer.play(soundPath);

        // 徐々に音量アップはタイマーイベントでやるっぽい。

    }

    /**
     * アラームを停止する
     */
    private void stopAlarm() {

    }
}
