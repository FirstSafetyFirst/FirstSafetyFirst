package com.products.safetyfirst.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.products.safetyfirst.R;

/**
 * Created by rishabh on 4/22/17.
 * Needs Work
 * TODO : postponed will come back later (surely)
 */

public class BasicRelativeLayout extends RelativeLayout {

    private View mainView;

    public Toolbar getToolbar() {
        return toolbar;
    }

    private Toolbar toolbar;

    public BasicRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BasicRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BasicRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mainView = LayoutInflater.from(context).inflate(R.layout.basic_layout, this);
        addView(mainView);
        setBackgroundResource(R.color.background);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
    }
}
