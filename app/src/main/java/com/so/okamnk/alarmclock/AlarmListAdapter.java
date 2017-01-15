package com.so.okamnk.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.so.okamnk.alarmclock.util.AlarmEntity;

/**
 * Created by araiyuuichi on 2016/12/17.
 */

public class AlarmListAdapter extends ArrayAdapter<AlarmEntity> {

    public interface OnAlarmListAdapterListener {
        void onClickEdit(AlarmEntity entity);

        void onClickDelete(AlarmEntity entity);

        void onClickPreview(AlarmEntity entity);
    }

    private LayoutInflater inflater;
    private OnAlarmListAdapterListener listener;

    public AlarmListAdapter(Context context, OnAlarmListAdapterListener listener) {
        super(context, R.layout.alarm_list_item);

        this.listener = listener;

        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View v, ViewGroup vg) {

        if (v == null) {
            v = inflater.inflate(R.layout.alarm_list_item, vg, false);
        }
        final AlarmEntity entity = getItem(position);

        ToggleButton enabledButton = (ToggleButton) v.findViewById(R.id.enabled_button);
        TextView alarmNameText = (TextView) v.findViewById(R.id.alarm_name_text);
        TextView alarmTimeText = (TextView) v.findViewById(R.id.alarm_time_text);
        TextView mondayText = (TextView) v.findViewById(R.id.monday_text);
        TextView tuesdayText = (TextView) v.findViewById(R.id.tuesday_text);
        TextView wednesdayText = (TextView) v.findViewById(R.id.wednesday_text);
        TextView thursdayText = (TextView) v.findViewById(R.id.thursday_text);
        TextView fridayText = (TextView) v.findViewById(R.id.friday_text);
        TextView saturdayText = (TextView) v.findViewById(R.id.saturday_text);
        TextView sundayText = (TextView) v.findViewById(R.id.sunday_text);

        enabledButton.setChecked(entity.getAlarmEnabled());
        alarmNameText.setText(entity.getAlarmName());
        alarmTimeText.setText(entity.getAlarmTime());
        mondayText.setEnabled(entity.getRepeatMonday());
        tuesdayText.setEnabled(entity.getRepeatTuesday());
        wednesdayText.setEnabled(entity.getRepeatWednesday());
        thursdayText.setEnabled(entity.getRepeatThrsday());
        fridayText.setEnabled(entity.getRepeatFriday());
        saturdayText.setEnabled(entity.getRepeatSaturday());
        sundayText.setEnabled(entity.getRepeatSunday());

        ImageButton editButton = (ImageButton) v.findViewById(R.id.edit_button);
        ImageButton deleteButton = (ImageButton) v.findViewById(R.id.delete_button);
        ImageButton previewButton = (ImageButton) v.findViewById(R.id.preview_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onClickEdit(entity);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onClickDelete(entity);
                }
            }
        });

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null) {
                    listener.onClickPreview(entity);
                }
            }
        });
        return v;
    }
}
