package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.Pojos.KnowItItem;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 10/20/2017.
 */

public interface KnowItAdapterView {

    void addAllItems(ArrayList<KnowItItem> items);

    void request();
}
