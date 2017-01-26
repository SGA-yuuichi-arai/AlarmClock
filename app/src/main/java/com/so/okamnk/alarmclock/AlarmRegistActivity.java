package com.so.okamnk.alarmclock;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;
import com.so.okamnk.alarmclock.util.AlarmRegistHelper;
import com.so.okamnk.alarmclock.util.StringUtility;

import java.util.ArrayList;
import java.util.Calendar;

import static com.so.okamnk.alarmclock.Define.ALARM_ENTITY;
import static com.so.okamnk.alarmclock.Define.ALARM_ID_KEY;
import static com.so.okamnk.alarmclock.Define.IS_EDITABLE_KEY;
import static com.so.okamnk.alarmclock.Define.IS_PREVIEW_KEY;
import static com.so.okamnk.alarmclock.Define.STOP_MODE_ADDITION;
import static com.so.okamnk.alarmclock.Define.STOP_MODE_TAP;

/**
 * Created by araiyuuichi on 2016/11/11.
 */


// public class AlarmRegistActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AlarmRegistHelper.OnAlarmRegistHelperListener {
public class AlarmRegistActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    public static ImageButton imageButton_back, imageButton_preview, imageButton_fix;
    private EditText editText_alarmName;
    private TimePicker timePicker_alarmTime;
    private TextView textView_alarmValue, textView_volumeValue;
    private SeekBar seekBar;
    private Spinner spinner_pattern, spinner_release, spinner_interval, spinner_times, spinner_mannerMode;
    private ToggleButton toggleButton_monday, toggleButton_tuesday, toggleButton_wednesday, toggleButton_thursday, toggleButton_friday, toggleButton_saturday, toggleButton_sunday;

    private boolean isEdit = true; //「編集」と「追加」を判別する
    private AlarmEntity alarmEntity;
    private int alarmId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_alarm_regist);

        alarmEntity = new AlarmEntity();

        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(this);

        imageButton_preview = (ImageButton) findViewById(R.id.imageButton_preview);
        imageButton_preview.setOnClickListener(this);

        imageButton_fix = (ImageButton) findViewById(R.id.imageButton_fix);
        imageButton_fix.setOnClickListener(this);

        editText_alarmName = (EditText) findViewById(R.id.editText_alarmName);

        timePicker_alarmTime = (TimePicker) findViewById(R.id.timePicker_alarmTime);

        textView_alarmValue = (TextView) findViewById(R.id.textView_alarmValue);
        textView_alarmValue.setClickable(true);
        textView_alarmValue.setOnClickListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar_volume);
        seekBar.setMax(100);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i("onStartTrackingTouch()",
                        String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                Log.i("onProgressChanged()",
                        String.valueOf(progress) + ", " + String.valueOf(fromTouch));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("onStopTrackingTouch()",
                        String.valueOf(seekBar.getProgress()));

                int seekBarProgress = seekBar.getProgress();
                textView_volumeValue.setText(((Integer) seekBarProgress).toString());
                alarmEntity.setSoundVolume(seekBarProgress);
            }
        });

        // 音量の表示
        textView_volumeValue = (TextView) findViewById(R.id.textView_volumeValue);

        // パターンのSpinner
        spinner_pattern = new Spinner(this);
        setSpinnerListener(spinner_pattern, R.id.spinner_pattern, R.array.pattern);
        // アラーム解除
        spinner_release = new Spinner(this);
        setSpinnerListener(spinner_release, R.id.spinner_release, R.array.release);
        // 間隔
        spinner_interval = new Spinner(this);
        setSpinnerListener(spinner_interval, R.id.spinner_interval, R.array.interval);
        // 回数
        spinner_times = new Spinner(this);
        setSpinnerListener(spinner_times, R.id.spinner_times, R.array.times);
        // マナーモード
        spinner_mannerMode = new Spinner(this);
        setSpinnerListener(spinner_mannerMode, R.id.spinner_mannerMode, R.array.mannerMode);

        //Toggle
        toggleButton_monday = (ToggleButton) findViewById(R.id.toggleButton_monday);
        toggleButton_monday.setOnCheckedChangeListener(this);
        toggleButton_tuesday = (ToggleButton) findViewById(R.id.toggleButton_tuesday);
        toggleButton_tuesday.setOnCheckedChangeListener(this);
        toggleButton_wednesday = (ToggleButton) findViewById(R.id.toggleButton_wednesday);
        toggleButton_wednesday.setOnCheckedChangeListener(this);
        toggleButton_thursday = (ToggleButton) findViewById(R.id.toggleButton_thursday);
        toggleButton_thursday.setOnCheckedChangeListener(this);
        toggleButton_friday = (ToggleButton) findViewById(R.id.toggleButton_friday);
        toggleButton_friday.setOnCheckedChangeListener(this);
        toggleButton_saturday = (ToggleButton) findViewById(R.id.toggleButton_saturday);
        toggleButton_saturday.setOnCheckedChangeListener(this);
        toggleButton_sunday = (ToggleButton) findViewById(R.id.toggleButton_sunday);
        toggleButton_sunday.setOnCheckedChangeListener(this);

        //alarmId = getIntent().getIntExtra(ALARM_ID_KEY, 0);
        //alarmEntity = (AlarmEntity) getIntent().getSerializableExtra(ALARM_ENTITY);
        isEdit = getIntent().getBooleanExtra(IS_EDITABLE_KEY, true);

        if (isEdit) {
            alarmEntity = (AlarmEntity) getIntent().getSerializableExtra(ALARM_ENTITY);
            displayViewEdit();
        } else {
            displayViewAdd();
        }
    }

    /**
     * スピナーとリスナーの登録
     *
     * @param spinner
     * @param resId
     * @param arrayId
     */
    private void setSpinnerListener(final Spinner spinner, final int resId, final int arrayId) {

        final String[] list;

        list = getResources().getStringArray(arrayId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapter);
        spinner.setFocusable(false);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ((Spinner) findViewById(resId)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinner.isFocusable() == false) {
                    spinner.setFocusable(true);
                    return;
                } else {

                    Log.i("Spinner", "call OnItemSelectedListener");
                    String str = getResources().getStringArray(arrayId)[position];
                    Log.i("Spinner", str);
                    Log.i("Spinner", ((Integer) position).toString());

                    switch (resId) {

                        case R.id.spinner_pattern:
                            alarmEntity.setSoundMode(position);
                            break;
                        case R.id.spinner_release:
                            alarmEntity.setStopMode(position);
                            break;
                        case R.id.spinner_interval:
                            alarmEntity.setSnoozeInterval(position);
                            break;
                        case R.id.spinner_times:
                            alarmEntity.setSnoozeNum(position);
                            break;
                        case R.id.spinner_mannerMode:
                            alarmEntity.setManorMode(position);
                            break;
                        default:
                            break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * ボタンとTextViewのイベント取得
     *
     * @param v
     */
    public void onClick(View v) {

        Log.i("Button", "call OnClickListener");

        switch (v.getId()) {

            case R.id.imageButton_back:
                cancelAlarmRegister();
                break;

            case R.id.imageButton_preview:
                int stop_mode = alarmEntity.getStopMode();
                transitionToAlarmCancel(stop_mode, imageButton_preview);
                break;

            case R.id.imageButton_fix:
                determineFixedButton(imageButton_fix);
                break;

            case R.id.textView_alarmValue:
                transitionToAlarmSelect(textView_alarmValue);
                break;

        }

    }

    /**
     * トグルボタンのイベント取得
     *
     * @param buttonView
     * @param isChecked
     */
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Log.i("ToggleButton", "call OnCheckdChangeListener");

        switch (buttonView.getId()) {

            case R.id.toggleButton_monday:
                alarmEntity.setRepeatMonday(isChecked);
                break;

            case R.id.toggleButton_tuesday:
                alarmEntity.setRepeatTuesday(isChecked);
                break;

            case R.id.toggleButton_wednesday:
                alarmEntity.setRepeatWednesday(isChecked);
                break;

            case R.id.toggleButton_thursday:
                alarmEntity.setRepeatThrsday(isChecked);
                break;

            case R.id.toggleButton_friday:
                alarmEntity.setRepeatFriday(isChecked);
                break;

            case R.id.toggleButton_saturday:
                alarmEntity.setRepeatSaturday(isChecked);
                break;

            case R.id.toggleButton_sunday:
                alarmEntity.setRepeatSunday(isChecked);
                break;

            default:
                break;

        }
    }

    /**
     * アラーム追加の場合にデフォルト値を表示させる
     */
    private void displayViewAdd() {

        alarmEntity.setAlarmId(0);
        editText_alarmName.setText("アラーム");

        Uri uriActual = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
        textView_alarmValue = (TextView) findViewById(R.id.textView_alarmValue);
        String soundPath = uriActual.getPath();
        textView_alarmValue.setText(soundPath); //デフォルトのアラーム音
        alarmEntity.setSoundPath(soundPath);

        RingtoneManager manager = new RingtoneManager(getApplicationContext());
        manager.setType(RingtoneManager.TYPE_RINGTONE); //着信音

        seekBar.setProgress(50);
        String seekBarValue = ((Integer) seekBar.getProgress()).toString();

        textView_volumeValue.setText(seekBarValue);

        spinner_pattern.setSelection(0); //デフォルト値＝「普通」
        spinner_release.setSelection(0); //デフォルト値＝「タップ」
        spinner_interval.setSelection(4); //デフォルト値＝「15分」
        spinner_times.setSelection(0); //デフォルト値＝「1回」
        spinner_mannerMode.setSelection(0); //デフォルト値＝「バイブレーション」

        toggleButton_monday.setChecked(true);
        //toggleButton_monday.setBackgroundColor(R.color.colorToggleButtonOn);
        toggleButton_tuesday.setChecked(true);
        toggleButton_wednesday.setChecked(true);
        toggleButton_thursday.setChecked(true);
        toggleButton_friday.setChecked(true);
        toggleButton_saturday.setChecked(true);
        toggleButton_sunday.setChecked(true);

        alarmEntity.setAlarmEnabled(true);

    }

    /**
     * アラーム編集の場合にAlarmEntityの値を表示させる
     */
    private void displayViewEdit() {

        /*
        AlarmDBAdapter adapter = new AlarmDBAdapter(getApplicationContext());
        alarmEntity = adapter.getAlarm(alarmId);
        */

        editText_alarmName.setText(alarmEntity.getAlarmName());

        // 「hh:mm」の5文字で取得
        String alarmTime = alarmEntity.getAlarmTime();

        // https://blog.tagbangers.co.jp/2015/11/07/android-timepicker-gethour-getminute
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int hour = Integer.parseInt(alarmTime.substring(0, 1));
            int minute = Integer.parseInt(alarmTime.substring(3, 4));
            timePicker_alarmTime.setHour(hour);
            timePicker_alarmTime.setMinute(minute);
        } else {
            Integer hour = Integer.valueOf(alarmTime.substring(0, 1));
            Integer minute = Integer.valueOf(alarmTime.substring(3, 4));
            timePicker_alarmTime.setCurrentHour(hour);
            timePicker_alarmTime.setCurrentMinute(minute);
        }

        textView_alarmValue = (TextView) findViewById(R.id.textView_alarmValue);
        textView_alarmValue.setText(alarmEntity.getSoundPath()); //アラーム音

        seekBar.setProgress(alarmEntity.getSoundVolume());
        String seekBarValue = ((Integer) seekBar.getProgress()).toString();
        textView_volumeValue.setText(seekBarValue);

        spinner_pattern.setSelection(alarmEntity.getSoundMode());
        spinner_release.setSelection(alarmEntity.getStopMode());
        spinner_interval.setSelection(alarmEntity.getSnoozeInterval());
        spinner_times.setSelection(alarmEntity.getSnoozeNum());
        spinner_mannerMode.setSelection(alarmEntity.getManorMode());

        toggleButton_monday.setChecked(alarmEntity.getRepeatMonday());
        toggleButton_tuesday.setChecked(alarmEntity.getRepeatTuesday());
        toggleButton_wednesday.setChecked(alarmEntity.getRepeatWednesday());
        toggleButton_thursday.setChecked(alarmEntity.getRepeatThrsday());
        toggleButton_friday.setChecked(alarmEntity.getRepeatFriday());
        toggleButton_saturday.setChecked(alarmEntity.getRepeatSaturday());
        toggleButton_sunday.setChecked(alarmEntity.getRepeatSunday());

    }

    /**
     * プレビューを表示
     * アラーム解除画面へ遷移する
     *
     * @param stopmode
     * @param v
     */
    public void transitionToAlarmCancel(int stopmode, View v) {

        boolean isPreview = false;

        alarmEntity = setAlarmEntity(alarmEntity);

        Intent intent = new Intent(AlarmRegistActivity.this, AlarmActivity.class);

        if (stopmode == STOP_MODE_TAP) {
            isPreview = false;
        } else if (stopmode == STOP_MODE_ADDITION) {
            isPreview = true;
        }

        intent.putExtra(ALARM_ID_KEY, alarmEntity.getAlarmId());
        intent.putExtra(ALARM_ENTITY, alarmEntity);
        intent.putExtra(IS_PREVIEW_KEY, isPreview);
        startActivity(intent);
    }

    /**
     * アラーム音選択画面へ遷移
     *
     * @param v
     */
    public void transitionToAlarmSelect(View v) {

        alarmEntity = setAlarmEntity(alarmEntity);

        Intent intent = new Intent(AlarmRegistActivity.this, SoundListActivity.class);
        intent.putExtra(ALARM_ID_KEY, alarmEntity.getAlarmId());
        intent.putExtra(ALARM_ENTITY, alarmEntity);
        startActivity(intent);
    }

    public boolean determineFixedButton(View v) {

        alarmEntity = setAlarmEntity(alarmEntity);
        AlarmDBAdapter adapter = new AlarmDBAdapter(getApplicationContext());
        AlarmEntity entityAdapter = adapter.saveAlarm(alarmEntity);
        //if (alarmEntity.equals(entityAdapter)) {
        if (alarmEntity.getAlarmTime().equals(entityAdapter.getAlarmTime())) {
        } else {
            showDialog("データベース登録失敗", "データベースの登録に失敗しました");
            return false;
        }

        ArrayList<AlarmEntity> entities = new ArrayList<>();
        entities.add(alarmEntity);

        AlarmRegistHelper helper = AlarmRegistHelper.getInstance();
        helper.regist(getApplicationContext(), entities, null); //nullでいいのか不明

        boolean isRegistered = helper.isRegistered(this, alarmEntity);
        if (isRegistered) {
        } else {
            showDialog("アラーム登録失敗", "アラームの登録に失敗しました");
            return false;
        }

        cancelAlarmRegister();

        return true;

    }

    private void cancelAlarmRegister() {

        alarmEntity = null;
        startActivity(new Intent(AlarmRegistActivity.this, AlarmListActivity.class));

    }

    /**
     * TimePickerの値を「hh:mm」のStringにする
     *
     * @param picker
     * @return
     */
    private String getTimePicker(TimePicker picker) {

        int hour;
        int minute;
        String str;

        // https://blog.tagbangers.co.jp/2015/11/07/android-timepicker-gethour-getminute
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hour = picker.getHour();
            minute = picker.getMinute();
        } else {
            hour = picker.getCurrentHour();
            minute = picker.getCurrentMinute();
        }

        str = String.valueOf(hour) + ':' + String.valueOf(minute);
        return str;
    }

    /**
     * UIの状態を取得してAlarmEntityにsetする
     *
     * @param entity
     * @return
     */
    private AlarmEntity setAlarmEntity(AlarmEntity entity) {

        entity.setAlarmId(alarmId);

        String resistDate = StringUtility.convertCalendarToString(Calendar.getInstance());
        Log.i("ResistDate", resistDate);
        entity.setRegistDate(resistDate);

        Editable alarmName = editText_alarmName.getText();
        entity.setAlarmName(alarmName.toString());

        String alarmTime = getTimePicker(timePicker_alarmTime);
        entity.setAlarmTime(alarmTime);

        entity.setSoundPath(textView_alarmValue.getText().toString());

        entity.setSoundMode(spinner_pattern.getSelectedItemPosition());

        entity.setSoundVolume(seekBar.getProgress());

        entity.setManorMode(spinner_mannerMode.getSelectedItemPosition());

        entity.setStopMode(spinner_release.getSelectedItemPosition());

        entity.setSnoozeInterval(spinner_interval.getSelectedItemPosition());

        entity.setSnoozeNum(spinner_times.getSelectedItemPosition());

        entity.setRepeatMonday(toggleButton_monday.isChecked());

        entity.setRepeatTuesday(toggleButton_tuesday.isChecked());

        entity.setRepeatWednesday(toggleButton_wednesday.isChecked());

        entity.setRepeatThrsday(toggleButton_thursday.isChecked());

        entity.setRepeatFriday(toggleButton_friday.isChecked());

        entity.setRepeatSaturday(toggleButton_saturday.isChecked());

        entity.setRepeatSunday(toggleButton_sunday.isChecked());

        return entity;
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();

    }
    /*
    @Override
    public void onRegistration(int alarmID, AlarmRegistHelper.RegistReturnCode returnCode) {
    }

    @Override
    public void onCompletion() {
    }
    */

}