package com.ratulbintazul.www.drtazulislam.Activity;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ratulbintazul.www.drtazulislam.R;

import java.io.File;

public class PdfReaderActivity extends AppCompatActivity {

    WebView webView;
    Bundle b;
    String pdf_url = "";



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        //webView = (WebView) findViewById(R.id.wv_pdf_view);

        webView = new WebView(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b = getIntent().getExtras();
        pdf_url = getIntent().getStringExtra("downloadUrl");

        if (b != null) {

            //webView.setWebViewClient(new MyBrowser());
            //webView.getSettings().setBuiltInZoomControls(false);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.loadUrl(pdf_url);
            setContentView(webView);
        }

    }
}
