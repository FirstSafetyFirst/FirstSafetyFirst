package com.products.safetyfirst.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.products.safetyfirst.R;

public class BasicRelativeLayout extends RelativeLayout {
    public BasicRelativeLayout(Context context) {
        super(context);
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.basic_layout, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BasicRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(R.layout.basic_layout, this);
    }
}
