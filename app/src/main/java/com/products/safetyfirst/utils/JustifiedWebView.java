package com.products.safetyfirst.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class JustifiedWebView extends WebView {
    public JustifiedWebView(Context context) {
        super(context);
    }

    public JustifiedWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JustifiedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public JustifiedWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        super.loadData(data, mimeType, encoding);
    }
}
