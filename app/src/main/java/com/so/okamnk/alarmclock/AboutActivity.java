package com.so.okamnk.alarmclock;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AboutActivity  extends AppCompatActivity implements View.OnClickListener {

    private String version;
    private String copyright;

    public static ImageButton imageButton_back;
    public static TextView textView_aboutApplication, textView_Version, textView_copyright, textView_openSourceLicense;
    public static WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //ActionBar actionBar = getActionBar();
        //actionBar.hide();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle("アプリについて");

        imageButton_back = new ImageButton(this);
        textView_aboutApplication = new TextView(this);
        textView_Version = new TextView(this);
        textView_copyright = new TextView(this);
        textView_openSourceLicense = new TextView(this);
        webview = new WebView(this);

        imageButton_back = (ImageButton)findViewById(R.id.imageButton_back);
        textView_aboutApplication = (TextView)findViewById(R.id.textView_aboutApplication);
        textView_Version = (TextView)findViewById(R.id.textView_Version);
        textView_copyright = (TextView)findViewById(R.id.textView_copyright);
        textView_openSourceLicense = (TextView)findViewById(R.id.textView_openSourceLicense);
        webview = (WebView)findViewById(R.id.webview);

        imageButton_back.setOnClickListener(this);


        //textView_Version.setText(packageInfo.versionName);

        // ActionBar actionBar = getActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.imageButton_back:

                startActivity(new Intent(AboutActivity.this, AlarmRegistActivity.class));
                break;

        }
    }

    private void displayView(){

        // PackageManager pm = new PackageManager();
        PackageManager pm;
        // PackageInfo packageInfo = pm.getPackageInfo(String packageName, int flags);

        // gpm = new PackageManager().getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
        //ActivityInfo gpm = pm.getPackageInfo(getPackageName(), PackageManager.GET_META_DATA);
        //copyright = gpm.metaData.getString("copyright");

        //textView_Version.setText(packageInfo.versionName);


    }

}

