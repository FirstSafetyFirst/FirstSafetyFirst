package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.models.EventModel;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;

/**
 * Created by vikas on 14/11/17.
 */

public interface PostAdapterView {

    void addAllEvents(ArrayList<PostModel> events);

    void addAllKeys(ArrayList<String> keys);

    void request();
}
