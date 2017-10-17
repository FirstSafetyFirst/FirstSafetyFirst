package com.products.safetyfirst.androidhelpers;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.products.safetyfirst.R;
import com.products.safetyfirst.interfaces.view.SimpleNotification;

import java.util.Random;

/**
 * Created by rishabh on 15/10/17.
 */

public class NotificationHelper implements SimpleNotification {
    private static final NotificationHelper ourInstance = new NotificationHelper();

    public static NotificationHelper getInstance() {
        return ourInstance;
    }

    private NotificationHelper() {
    }

    /**
     * Creates a simple Notification without any intent
     * @param context
     * @param title
     * @param text
     * @return Notification Id which can later be used to retrieve notification
     */
    @Override
    public int createNotif(Context context, String title, String text) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        int mNotificationId = random.nextInt(1000) + 100;

        mNotificationManager.notify(mNotificationId, mBuilder.build());

        return mNotificationId;
    }
}
