package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.Pojos.NewsModel;

import java.util.ArrayList;

/**
 * Created by vikas on 14/11/17.
 */

public interface NewsAdapterView {

    void addAllEvents(ArrayList<NewsModel> events);

    void addAllKeys(ArrayList<String> keys);

    void request();
}
