package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.News_model;

/**
 * Created by vikas on 12/10/17.
 */

public interface EventsDetailView {

    void setBookMark();

    void share();

    void setAction(int actionId);

    void setEvent(Event_model event);

    void onError(String message);

    void onSuccess();
}
