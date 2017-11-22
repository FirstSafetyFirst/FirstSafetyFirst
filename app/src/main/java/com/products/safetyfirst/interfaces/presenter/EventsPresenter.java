package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.Pojos.EventModel;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsPresenter {

    void onDestroy();

    void request();

    void getChildren(ArrayList<EventModel> events);

    void addAction(String mEventId, String mProfileKey, int mAction);

    void onActionError(String message);

    void onActionSuccess(String message);

    void getKeys(ArrayList<String> eventsArrayKey);
}
