package com.so.okamnk.alarmclock;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;

import java.io.IOException;

public class AlarmActivity extends AppCompatActivity {
    private TextView mTextAlarmName;
    private TextView mTextQuestion;
    private EditText mEditAnswer;
    private Button mButtonPass;
    private Button mButtonAnswer;

    private AlarmEntity mAlarmEntity;

    private Question mQuestion;
    private MediaPlayer mMediaPlayer;
    private Vibrator mVibrator;

    private Handler mVolumeChanger;
    private float mVolume;            // 徐々に音量を上げるときに使う

    private class RingerModeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mActivity != null) {
                mActivity.updateAlarm();
            }
        }

        private void setAlarmActivity(AlarmActivity activity) {
            mActivity = activity;
        }

        private AlarmActivity mActivity = null;
    }

    private RingerModeReceiver mRingerModeReceiver;

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
                     if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                         onAnswer();
                         return true;
                     }
                     return false;
                 }
             }
        );

        mRingerModeReceiver = new RingerModeReceiver();
        mRingerModeReceiver.setAlarmActivity(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.RINGER_MODE_CHANGED");
        registerReceiver(mRingerModeReceiver, intentFilter);

        // TODO : プレビューの時どうやってデータ受けとる？
        Intent intent = getIntent();
        boolean isPreview = intent.getBooleanExtra("isPreview", false);
        int alarmID = intent.getIntExtra("alarmID", 0);
        onInit(getApplicationContext(), alarmID);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRingerModeReceiver);
        if (mVolumeChanger != null) {
            mVolumeChanger.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    // TODO : AlarmEntity側で定義してもらいたい
    static final int STOP_MODE_TAP = 0;
    static final int STOP_MODE_ADDITION = 1;
    static final int SOUND_MODE_NORMAL = 0;
    static final int SOUND_MODE_LARGE_SLOWLY = 1;
    static final int MANOR_MODE_FOLLOW_OS = 0;
    static final int MANOR_MODE_INDEPENDENT = 1;

    void onInit(Context context, int alarmID) {
        if (alarmID == 0) {
            // TODO : 以下はテストコード。DBが使えるようになったらこっちのパスは消す。
            mAlarmEntity = new AlarmEntity();
            mAlarmEntity.setAlarmName("アラームテスト");
            mAlarmEntity.setStopMode(STOP_MODE_ADDITION);
            mAlarmEntity.setSoundPath("/storage/emulated/0/Music/eine.mp3");
            mAlarmEntity.setSoundMode(SOUND_MODE_LARGE_SLOWLY);
            mAlarmEntity.setSoundVolume(50);
            mAlarmEntity.setManorMode(MANOR_MODE_FOLLOW_OS);
        } else {
            AlarmDBAdapter db = new AlarmDBAdapter(context);
            mAlarmEntity = db.getAlarm(alarmID);
        }

        // 問題とコントロール
        mTextAlarmName.setText(mAlarmEntity.getAlarmName());
        if (mAlarmEntity.getStopMode() == STOP_MODE_ADDITION) {
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

        // サウンド
        mMediaPlayer = new MediaPlayer();
        try {
            mVolume = mAlarmEntity.getSoundVolume() / 100.0f;

            mMediaPlayer.setDataSource(mAlarmEntity.getSoundPath());
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(mVolume, mVolume);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            Log.d("AlarmActivity", e.getMessage());
        }

        // バイブレーション
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // 音量徐々に大きくする
        if (mAlarmEntity.getSoundMode() == SOUND_MODE_LARGE_SLOWLY) {
            mVolumeChanger = new Handler(Looper.getMainLooper());

            final int DELAY_MS = 4000;
            mVolumeChanger.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mVolume += 0.05f;
                    if (mVolume > 1.0f) {
                        mVolume = 1.0f;
                    }
                    mMediaPlayer.setVolume(mVolume, mVolume);
                    mVolumeChanger.postDelayed(this, DELAY_MS);
                }
            }, DELAY_MS);
        }
        else {
            mVolumeChanger = null;
        }

        // 再生
        updateAlarm();
    }

    void onPass() {
        mQuestion = Question.createRandomQuestion();
        mTextQuestion.setText(mQuestion.getQuestionString());

        mEditAnswer.setText("");
    }

    void onSnooze() {
        stopAlarm();

        Intent intent = new Intent(getApplicationContext(), SnoozeActivity.class);
        intent.putExtra("alarmID", mAlarmEntity.getAlarmId());
        startActivity(intent);

        // この画面にはもう戻れないように終了させる
        finish();

        // TODO : プレビューの時もスヌーズ画面に遷移していいの？
    }

    void onAnswer() {
        if (mAlarmEntity.getStopMode() == STOP_MODE_ADDITION) {
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
     * アラーム再生状態を更新する。
     */
    private void updateAlarm() {
        // 再生するかどうかを計算
        boolean playSound = false;
        boolean playVibration = false;
        if (mAlarmEntity.getManorMode() == MANOR_MODE_FOLLOW_OS) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            int ringerMode = audioManager.getRingerMode();
            switch (ringerMode) {
                case AudioManager.RINGER_MODE_SILENT:
                    playSound = false;
                    playVibration = false;
                    break;
                case AudioManager.RINGER_MODE_VIBRATE:
                    playSound = false;
                    playVibration = true;
                    break;
                case AudioManager.RINGER_MODE_NORMAL:
                    playSound = true;
                    playVibration = false;
                    break;
            }
        } else {
            playSound = true;
            playVibration = false;
        }

        if (playSound) {
            if (mMediaPlayer.isPlaying() == false) {
                mMediaPlayer.start();
            }
        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }

        if (playVibration) {
            long vibratePattern[] = {500, 500};
            mVibrator.vibrate(vibratePattern, 0);
        } else {
            mVibrator.cancel();
        }
    }

    /**
     * アラームを停止する
     */
    private void stopAlarm() {
        mMediaPlayer.stop();
        mVibrator.cancel();
    }
}
