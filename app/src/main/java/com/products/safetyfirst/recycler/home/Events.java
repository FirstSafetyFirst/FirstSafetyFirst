package com.products.safetyfirst.recycler.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 12/04/17.
 */

public class Events extends RecyclerView.ViewHolder {

    RecyclerView events;
    public Events(View v) {
        super(v);
        events = (RecyclerView) v.findViewById(R.id.events_holder);
    }

    public RecyclerView getEvents() {
        return events;
    }

    public void setEvents(RecyclerView events) {
        this.events = events;
    }
}
