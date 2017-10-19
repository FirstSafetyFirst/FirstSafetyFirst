package com.products.safetyfirst.interfaces.view;

import android.content.Context;

import com.products.safetyfirst.androidhelpers.NotificationHelper;

/**
 * Created by rishabh on 15/10/17.
 */

public interface SimpleNotification {

    int createNotif(Context context, String title, String text);

    NotificationHelper.ProgressNotification createProgressNotif(Context context, String title, String text);
}
