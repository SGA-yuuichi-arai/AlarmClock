package com.so.okamnk.alarmclock;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.ContentObservable;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.List;

/**
 * Created by masa nomoto on 2016/11/21.
 */

public class SoundListActivity extends Activity{

    ArrayAdapter<String> adapter;
    ListView listView;
    Switch previewSwitch;
    TabHost tabhost;
    Button fixButton;
    Button cancelButton;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_list);

        //ビューオブジェクトの取得
        listView = (ListView) findViewById(R.id.soundlist);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_sound_list);
        previewSwitch = (Switch) findViewById(R.id.preview_switch);
        tabhost = (TabHost) findViewById(android.R.id.tabs);
        fixButton = (Button) findViewById(R.id.fix_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        //リストビューの設定
        listView.setAdapter(adapter);

        // リストビューのアイテムをタップ -> リスナーの登録
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;

                //タップしたアイテムの取得
                //String item = (String) listView.getItemAtPosition(position);

                //プレビュースイッチのON/OFFを判定
                if (previewSwitch.isChecked() == true) {    //ONの場合
                    playSound(position);
                } else {    //OFFの場合
                    stopSound(position);
                }
            }
        });

        //TABHOSTの設定
        tabhost.setup();
        //TABHOST->着信音
        TabSpec tabRington = tabhost.newTabSpec("tab_rington");
        tabRington.setIndicator("着信音");
        tabRington.setContent(R.id.tab_rington);
        tabhost.addTab(tabRington);

        //TABHOST -> ミュージック
        TabSpec tabMusic = tabhost.newTabSpec("tab_music");
        tabMusic.setIndicator("ミュージック");
        tabMusic.setContent(R.id.tab_rington);
        tabhost.addTab(tabMusic);

        //デフォルトで着信音タブをタップ
        getRingtoneList();

        //タブタップ時のリスナーの登録
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            //タブをタップした際の動作
            @Override
            public void onTabChanged(String tabId) {
                //タップされた時の処理を記述
                //着信音をタップ
                if(tabId == "tab_rington") {
                    getRingtoneList();
                }
                //ミュージックをタップ
                else if(tabId == "tab_music") {
                    getMusicList();
                }
            }
        });
    }

    public void getRingtoneList(){

        RingtoneManager ringtoneManager;
        Cursor cursor;

        //RingtonManagerの設定
        ringtoneManager = new RingtoneManager(getApplicationContext());
        cursor = ringtoneManager.getCursor();

        //リストビューをクリア
        adapter.clear();

        //着信音の出力
        while(cursor.moveToNext()) {
            //リストビューに先頭の着信音から出力
            adapter.add(cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX));
        }
    }

    private void getMusicList(){

        //ContentResolverクラスのインスタンスを作成
        ContentResolver resolver = getContentResolver();

        //ミュージックリストを取得
        Cursor cursor = resolver.query(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        new String[]{
                            MediaStore.Audio.Media.ALBUM,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.TITLE},
                            null,
                            null,
                            null
                        );

        //リストビューをクリア
        adapter.clear();

        //ミュージックリストの出力
        while(cursor.moveToNext()) {
            //リストビューにミュージックタイトルを出力
            adapter.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        }
    }

    private void playSound(int pos){

        Ringtone ringtone;

        //Uri muri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        //ringtone = RingtoneManager.getRingtone(getApplicationContext(),muri);

        //リストビューのpositionを指定
        RingtoneManager ringtoneManager = new RingtoneManager(getApplicationContext());
        ringtone = ringtoneManager.getRingtone(pos);

        //着信音を再生
        ringtone.play();
    }

    private void stopSound(int pos){

        Ringtone ringtone;

        //Uri muri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        //ringtone = RingtoneManager.getRingtone(getApplicationContext(),muri);

        //リストビューのpositionを指定
        RingtoneManager ringtoneManager = new RingtoneManager(getApplicationContext());
        ringtone = ringtoneManager.getRingtone(pos);

        //着信音を停止
        ringtone.stop();
    }

    public void onClickFixButton (View view) {

        RingtoneManager mRingtone = new RingtoneManager(getApplicationContext());

        RingtoneManager.setActualDefaultRingtoneUri(getApplicationContext(),RingtoneManager.TYPE_RINGTONE,uri);

        //アクティビティを終了させて前の画面に戻る
        finish();

        //アラーム登録画面に戻る
        //Intent intent = new Intent();
        //intent.setClassName("com.so.okamnk.alarmclock","com.so.okamnk.alarmclock.AlarmRegistActivity");

        //アラーム登録画面に遷移
        //startActivity(intent);
    }

    public void onClickCancelButton (View view) {

        //アクティビティを終了させて前の画面に戻る
        finish();

        //何もせずアラーム登録画面に戻る
        //Intent intent = new Intent();
        //intent.setClassName("com.so.okamnk.alarmclock","com.so.okamnk.alarmclock.AlarmRegistActivity");

        //アラーム登録画面に遷移
        //startActivity(intent);
    }

}