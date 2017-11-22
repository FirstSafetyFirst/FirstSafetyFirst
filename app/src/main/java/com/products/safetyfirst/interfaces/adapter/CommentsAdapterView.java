package com.products.safetyfirst.interfaces.adapter;

import com.products.safetyfirst.Pojos.Comment;

import java.util.ArrayList;

/**
 * Created by vikas on 08/11/17.
 */

public interface CommentsAdapterView {

    void addAllComments(ArrayList<Comment> comments);

    void addAllKeys(ArrayList<String> keys);

    void request();
}
