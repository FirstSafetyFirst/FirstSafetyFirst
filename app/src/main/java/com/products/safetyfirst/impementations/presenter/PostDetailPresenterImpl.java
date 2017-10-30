package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.PostDetailInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.PostDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.interfaces.view.PostDetailView;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 30/10/17.
 */

public class PostDetailPresenterImpl implements PostDetailPresenter, PostDetailInteractor.OnPostQueryFinishedListener {

    private PostDetailView postDetailView;
    private PostDetailInteractor postDetailInteractor;
    private String postKey;

    public PostDetailPresenterImpl(PostDetailView postDetailView, String mPostKey){
        this.postDetailView = postDetailView;
        this.postDetailInteractor = new PostDetailInteractorImpl(this);
        this.postKey = mPostKey;
    }

    @Override
    public void requestPost() {
        postDetailInteractor.requestPost(postKey, this);
    }

    @Override
    public void getPost(PostModel post) {
        if(postDetailView != null){
            postDetailView.setPost(post);
        }
    }

    @Override
    public void getAuthor(UserModel user) {
        if(postDetailView != null){
            postDetailView.setAuthor(user);
        }
    }

    @Override
    public void onDestroy() {
        postDetailView = null;
        postDetailInteractor  = null;
    }

    @Override
    public void setBookMark(String mPostKey) {
        if(postDetailInteractor != null)
           postDetailInteractor.setBookMark(mPostKey);
    }

    @Override
    public void requestPostAuthor(String authorKey) {
        if(postDetailInteractor != null)
            postDetailInteractor.requestAuthor(authorKey);
    }
}
