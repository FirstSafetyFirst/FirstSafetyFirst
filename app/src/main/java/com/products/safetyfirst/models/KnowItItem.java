package com.products.safetyfirst.models;

import java.net.URL;

/**
 * Created by ishita sharma on 10/19/2017.
 */

public class KnowItItem {
    private static String item_name;
    private static URL thumb_url;
    public KnowItItem(){

    }
    public String getItem_name(){
        return item_name;
    }
    public URL getThumb_url(){
        return thumb_url;
    }
}
