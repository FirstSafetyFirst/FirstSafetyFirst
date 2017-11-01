package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.PostInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.PostInteractor;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.interfaces.view.PostView;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;

/**
 * Created by vikas on 01/11/17.
 */

public class PostPresenterImpl implements PostPresenter {

    private PostView postView;
    private PostInteractor postInteractor;

    public PostPresenterImpl(PostView postView){
        this.postView = postView;
        this.postInteractor = new PostInteractorImpl(this);
    }


    @Override
    public void onDestroy() {
        postView = null;
    }

    @Override
    public void requestFirstPosts() {
        if(postInteractor != null){
            postInteractor.requestFirstPosts();
        }
    }

    @Override
    public void requestPostByKey(String key, int page) {
        if(postInteractor != null){
            postInteractor.requestPost(key);
        }
    }

    @Override
    public void getChildren(ArrayList<PostModel> post) {
        if(postView != null){
            postView.getInitialPosts(post);
        }
    }

    @Override
    public void getAnother(ArrayList<PostModel> post) {
        if(postView != null){
            postView.getNextPost(post);
        }

    }
}
