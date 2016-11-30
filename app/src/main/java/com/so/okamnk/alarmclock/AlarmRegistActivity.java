package com.so.okamnk.alarmclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AlarmRegistActivity extends AppCompatActivity implements View.OnClickListener {

    public static ImageButton imageButton_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_regist);

        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("アラーム登録");

        imageButton_back = new ImageButton(this);

        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(this);


        ((Spinner) findViewById(R.id.spinner_pattern)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ((Spinner) findViewById(R.id.spinner_interval)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ((Spinner) findViewById(R.id.spinner_times)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*
        ArrayAdapter adapter = new ArrayAdapter(this, spinner_mannerMode);
        adapter.add("音を出す");
        adapter.add("バイブレーション");
        adapter.setDropDownViewResource(spinner_mannerMode);
        spinner_mannerMode = (Spinner) findViewById(spinner_mannerMode);
        spinner_mannerMode.setAdapter(adapter);
        */

        // Spinnerの項目選択時のリスナー
        ((Spinner) findViewById(R.id.spinner_mannerMode)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 選択された文字列を取得
                String str = getResources().getStringArray(R.array.mannerMode)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.imageButton_back:

                startActivity(new Intent(AlarmRegistActivity.this, AboutActivity.class));
                break;

        }

    }

}
