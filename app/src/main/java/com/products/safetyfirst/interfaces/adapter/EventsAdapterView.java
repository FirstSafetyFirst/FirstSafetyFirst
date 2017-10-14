package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.models.Event_model;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsAdapterView {

    void addAllEvents(ArrayList<Event_model> events);

    void addAllKeys(ArrayList<String> keys);

    void request();

}
