package com.products.safetyfirst.recycler.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.products.safetyfirst.R;

/**
 * Created by profileconnect on 12/04/17.
 */

class TrainingCenters extends RecyclerView.ViewHolder {

    private final RecyclerView events;
    public TrainingCenters(View v) {
        super(v);
        events = v.findViewById(R.id.events_holder);
    }


}
