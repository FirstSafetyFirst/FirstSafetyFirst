package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.Event_model;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsPresenter {

    void onDestroy();

    void request();

    void getChildren(ArrayList<Event_model> events);

    void addAction(String mEventId, String mProfileKey, int mAction);

    void onActionError(String message);

    void onActionSuccess(String message);

    void getKeys(ArrayList<String> eventsArrayKey);
}
