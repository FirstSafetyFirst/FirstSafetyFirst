package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.Pojos.EventModel;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsAdapterView {

    void addAllEvents(ArrayList<EventModel> events);

    void addAllKeys(ArrayList<String> keys);

    void request();

}
