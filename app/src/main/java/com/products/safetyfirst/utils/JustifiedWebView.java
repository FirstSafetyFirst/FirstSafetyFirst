package com.products.safetyfirst.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;


@SuppressWarnings({"ALL", "EmptyMethod"})
public class JustifiedWebView extends WebView {

    private String content;
    private String heading;

    public String getContent() {
        return content;
    }

    public String getHeading() {
        return heading;
    }

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

    public void setText(String content) {
        setText(content, "");
    }

    // heading must be accompanied by its styling or it will be shown as it is
    public void setText(String content, String heading) {
        content = content.replaceAll("\n", "<br>");
        this.content = content;
        this.heading = heading;
        String text = "<html><body>"
                + heading
                + "<p align=\"justify\" style=\" color: #2c3b42 \">"
                + content
                + "</p> "
                + "</body></html>";
        loadData(text, "text/html; charset=utf-8", "utf-8");
    }
}
