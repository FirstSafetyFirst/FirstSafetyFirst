package com.products.safetyfirst.utils;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rishabh on 19/10/17.
 */

public class StringHelper {
    private static final StringHelper ourInstance = new StringHelper();

    public static StringHelper getInstance() {
        return ourInstance;
    }

    private StringHelper() {
    }

    public String addHref(String body) {
        String [] parts = body.split("\\s+");

        // Attempt to convert each item into an URL.
        for( int i=0; i<parts.length; i++ ) {
            try {
                URL url = new URL(parts[i]);
                parts[i] = ("<a href=\"" + url + "\">" + url + "</a> ");
            } catch (MalformedURLException ignored) {

            }
        }

        return TextUtils.join(" ", parts);
    }
}
