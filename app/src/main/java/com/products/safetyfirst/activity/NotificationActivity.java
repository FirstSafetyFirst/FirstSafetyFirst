package com.products.safetyfirst.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.products.safetyfirst.R;
import com.products.safetyfirst.mNotification.MyFirebaseMessagingService;
import com.products.safetyfirst.adapters.NotificationAdapter;
import com.products.safetyfirst.Pojos.NotificationModel;

import io.realm.Realm;
import io.realm.RealmResults;

public class NotificationActivity extends BaseActivity {

    private RecyclerView notificationView;
    private NotificationAdapter notificationAdapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        realm = Realm.getDefaultInstance();

        View noNotification = findViewById(R.id.no_notification);
        RealmResults<NotificationModel> items = realm.where(NotificationModel.class).findAll();
        if(items.size() == 0)
            noNotification.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MyFirebaseMessagingService.unreadPreference, 0);
        editor.putString(MyFirebaseMessagingService.discussionNotif, "");
        editor.apply();

        notificationAdapter = new NotificationAdapter(this, realm);
        notificationView = findViewById(R.id.notification_view);
        notificationView.setLayoutManager(new LinearLayoutManager(this));
        notificationView.setAdapter(notificationAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
