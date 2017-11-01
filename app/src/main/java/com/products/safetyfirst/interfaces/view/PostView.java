package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.models.PostModel;

import java.util.List;

/**
 * Created by vikas on 01/11/17.
 */

public interface PostView {

    void onError(String message);

    void onSuccess(String message);

    void showProgress();

    void hideProgress();

    void navigateToHome();

    void getInitialPosts(List<PostModel> initialPosts);

    void getNextPost(List<PostModel> initialPosts);
}