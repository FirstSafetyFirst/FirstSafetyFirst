package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.Event_model;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public interface EventsAdapterView {

    void addAll(ArrayList<Event_model> events);

    void request();

}
