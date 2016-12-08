package com.so.okamnk.alarmclock;

import java.lang.Object;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.ArrayAdapter;
/**
 * Created by masa nomoto on 2016/11/21.
 */

public class SoundListUp {

    ArrayAdapter<String> adapter;

    private void getRingtoneList(){
/*
        //RingtoneManager ringtoneManager;
        Cursor cursor;
        private static SoundListUp instance = null;
        Context context;

        //ringtoneManager = new RingtoneManager(getApplicationContext());
        RingtoneManager ringtoneManager = new RingtoneManager(context.getApplicationContext());
        cursor = ringtoneManager.getCursor();

        if(cursor.moveToFirst()){
            while(cursor.moveToFirst()) {
                //リストビューに先頭の着信音から出力
            }
        }
*/
    }

    private void getMusicList(){

    }

    private void playSound(String uri){
/*
        Ringtone ringtone;

        Uri muri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone();

        ringtone.play();
*/
    }

    private void stopSound(String uri){
/*
        Ringtone ringtone;

        Uri muri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        ringtone = RingtoneManager.getRingtone();

        ringtone.stop();
*/
    }

}
