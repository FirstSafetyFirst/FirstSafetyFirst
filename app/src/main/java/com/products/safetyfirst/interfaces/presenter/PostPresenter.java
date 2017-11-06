package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.Event_model;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;

/**
 * Created by vikas on 01/11/17.
 */

public interface PostPresenter {


    void onDestroy();

    void requestFirstPosts();

    void requestPostByKey(String key, int page);

    void getChildren(ArrayList<PostModel> post);

    void getAnother(ArrayList<PostModel> post);


}
