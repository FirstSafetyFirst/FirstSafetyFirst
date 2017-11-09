package com.products.safetyfirst.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.products.safetyfirst.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private Activity activity;
    private ProgressDialog progDailog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        activity = this;

        String url = getIntent().getStringExtra("Url");
        //  Toast.makeText(this, url, Toast.LENGTH_LONG).show();

        progDailog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);


        webView = findViewById(R.id.web);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });

        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }
}
