package com.products.safetyfirst.interfaces.adapter;

import java.util.ArrayList;

/**
 * Created by ishita sharma on 11/5/2017.
 */

public interface BookmarkAdapterView {
    void addAllItem(ArrayList<Object> list);

    void removeBookmark();
    //TODO: review it later and add suitable parameters.
    void request();
}
