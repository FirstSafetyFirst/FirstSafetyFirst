package com.products.safetyfirst.modelhelper;

import com.products.safetyfirst.models.Discussion_model;

import java.util.List;

/**
 * Created by rishabh on 12/10/17.
 */

public class PostHelper {

    private UserHelper userhelper = new UserHelper();

    public void createNewPost(String title, String body, String file,  List<String> imageList ) {
        int time = (int) System.currentTimeMillis();
        Discussion_model post = new Discussion_model(
                userhelper.getUserId(), title, userhelper.getUserImgUrl(), body, time, file, imageList);

    }
}
