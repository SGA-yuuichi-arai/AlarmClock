package com.so.okamnk.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by araiyuuichi on 2016/11/11.
 */

public class AboutActivity extends AppCompatActivity {

    private String version = "";
    private final String copyright = "Copyright \u00a9 2017, xxxxxxxx";

    public static ImageButton imageButton_back;
    public static TextView textView_aboutApplication, textView_Version, textView_copyright, textView_openSourceLicense;
    public static WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        textView_aboutApplication = (TextView) findViewById(R.id.textView_aboutApplication);
        textView_Version = (TextView) findViewById(R.id.textView_Version);
        textView_copyright = (TextView) findViewById(R.id.textView_copyright);
        textView_openSourceLicense = (TextView) findViewById(R.id.textView_openSourceLicense);
        webview = (WebView) findViewById(R.id.webview);

        displayView();

        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this, AlarmListActivity.class);
                startActivity(intent);
            }
        });

    }


    private void displayView() {

        Context context = getApplicationContext();
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
            Log.i("versionName", version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        textView_Version.setText("バージョン" + version);

        textView_copyright.setText(copyright);

        textView_openSourceLicense.setText("オープンソースライセンス：");

        webview.loadUrl("file:///android_asset/index.html");
        //webview.loadDataWithBaseURL(null, createHtml(), "text/html", "utf-8", null);

    }

    /**
     * WebViewに表示させるHTMLを作成
     *
     * @return
     */
    private String createHtml() {

        StringBuilder sb = new StringBuilder();

        sb.append("<html><head><style type=\"text/css\"> body { font-size: 16px; } p { font-size: 200%; } </style><title>license</title></head><body>");
        sb.append("<p>Notices for files:</p>");
        sb.append("<ul><li>xxx.jar</li></ul>");
        sb.append("<pre>" +
                "jarのライセンス文" +
                "</pre>");
        sb.append("</body></html>");

        return sb.toString();

    }

}

