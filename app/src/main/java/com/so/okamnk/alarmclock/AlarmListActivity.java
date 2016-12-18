package com.so.okamnk.alarmclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.so.okamnk.alarmclock.util.AlarmDBAdapter;
import com.so.okamnk.alarmclock.util.AlarmEntity;

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
        }

        adapter = new AlarmListAdapter(getApplicationContext());
        entities = new ArrayList<>();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AlarmDBAdapter dbAdapter = new AlarmDBAdapter(getApplicationContext());
//        entities.addAll(dbAdapter.getAll());

        // test
        entities.clear();
        entities.add(new AlarmEntity(0, "2016/12/17/20:23", "test", "20:27", "/tmp/test", 0,
                100, 0, 0, 0, 3, true,
                false, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(1, "2016/12/17/20:23", "test2", "20:27", "/tmp/test2", 0,
                100, 0, 0, 0, 3, true,
                true, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(0, "2016/12/17/20:23", "test", "20:27", "/tmp/test", 0,
                100, 0, 0, 0, 3, true,
                false, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(1, "2016/12/17/20:23", "test2", "20:27", "/tmp/test2", 0,
                100, 0, 0, 0, 3, true,
                true, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(0, "2016/12/17/20:23", "test", "20:27", "/tmp/test", 0,
                100, 0, 0, 0, 3, true,
                false, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(1, "2016/12/17/20:23", "test2", "20:27", "/tmp/test2", 0,
                100, 0, 0, 0, 3, true,
                true, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(0, "2016/12/17/20:23", "test", "20:27", "/tmp/test", 0,
                100, 0, 0, 0, 3, true,
                false, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(1, "2016/12/17/20:23", "test2", "20:27", "/tmp/test2", 0,
                100, 0, 0, 0, 3, true,
                true, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(0, "2016/12/17/20:23", "test", "20:27", "/tmp/test", 0,
                100, 0, 0, 0, 3, true,
                false, false, false, false,
                false, false, true));
        entities.add(new AlarmEntity(1, "2016/12/17/20:23", "test2", "20:27", "/tmp/test2", 0,
                100, 0, 0, 0, 3, true,
                true, false, false, false,
                false, false, true));

        adapter.clear();
        adapter.addAll(entities);
        adapter.notifyDataSetChanged();

    }
}
