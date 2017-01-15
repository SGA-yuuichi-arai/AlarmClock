package com.so.okamnk.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;
import com.so.okamnk.alarmclock.util.AlarmRegistHelper;

import java.util.ArrayList;


public class AlarmListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<AlarmEntity> adapter;
    private ArrayList<AlarmEntity> entities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        if (savedInstanceState == null) {
            listView = (ListView) findViewById(R.id.alarm_list);

            ImageButton addButton = (ImageButton) findViewById(R.id.add_button);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AlarmRegistActivity.class);
                    intent.putExtra("isEditable", false);
                    startActivity(intent);
                }
            });
        }

        adapter = new AlarmListAdapter(getApplicationContext(), new AlarmListAdapter.OnAlarmListAdapterListener() {

            @Override
            public void onCheckedChangedAlarm(AlarmEntity entity, boolean isChecked) {

                AlarmDBAdapter dbAdapter = new AlarmDBAdapter(getApplicationContext());
                entity.setAlarmEnabled(isChecked);
                dbAdapter.saveAlarm(entity);

                AlarmRegistHelper registHelper = AlarmRegistHelper.getInstance();
                ArrayList alarmEntities = new ArrayList();

                if (isChecked) {
                    registHelper.registAsync(getApplicationContext(), alarmEntities, null);
                } else {
                    registHelper.unregistAsync(getApplicationContext(), alarmEntities, null);
                }
            }

            @Override
            public void onClickEdit(AlarmEntity entity) {

                Intent intent = new Intent(getApplicationContext(), AlarmRegistActivity.class);
                intent.putExtra("isEditable", true);
                startActivity(intent);

                AlarmRegistHelper registHelper = AlarmRegistHelper.getInstance();
                ArrayList alarmEntities = new ArrayList();
                alarmEntities.add(entity);
            }

            @Override
            public void onClickDelete(AlarmEntity entity) {

                AlarmDBAdapter dbAdapter = new AlarmDBAdapter(getApplicationContext());
                try {
                    dbAdapter.deleteAlarm(entity.getAlarmId());
                    AlarmRegistHelper registHelper = AlarmRegistHelper.getInstance();
                    ArrayList alarmEntities = new ArrayList();
                    alarmEntities.add(entity);
                    registHelper.unregistAsync(getApplicationContext(), alarmEntities, new AlarmRegistHelper.OnAlarmRegistHelperListener() {
                        @Override
                        public void onRegistration(int alarmID, AlarmRegistHelper.RegistReturnCode returnCode) {

                        }

                        @Override
                        public void onCompletion() {
                            listUpdate();
                        }
                    });
                } catch (RuntimeException e) {

                }

            }

            @Override
            public void onClickPreview(AlarmEntity entity) {

                Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                intent.putExtra("isPreview", true);
                // AlarmEntityがシリアライズ対応したらIntentにputする
                startActivity(intent);
            }
        });

        entities = new ArrayList<>();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listUpdate();
    }

    private void listUpdate() {
        AlarmDBAdapter dbAdapter = new AlarmDBAdapter(getApplicationContext());

        entities.clear();
        entities.addAll(dbAdapter.getAll());

        adapter.clear();
        adapter.addAll(entities);
        adapter.notifyDataSetChanged();
    }
}
