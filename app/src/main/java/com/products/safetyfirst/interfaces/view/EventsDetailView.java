package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.Pojos.EventModel;

/**
 * Created by vikas on 12/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface EventsDetailView {

    void setBookMark();

    void share();

    void setAction(int actionId);

    void setEvent(EventModel event);

    void onError(String message);

    void onSuccess();
}
