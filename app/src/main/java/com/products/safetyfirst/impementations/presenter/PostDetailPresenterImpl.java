package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.CommentsAdapter;
import com.products.safetyfirst.impementations.Interactor.PostDetailInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.CommentsAdapterView;
import com.products.safetyfirst.interfaces.interactor.PostDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.interfaces.view.PostDetailView;
import com.products.safetyfirst.models.Comment;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikas on 30/10/17.
 */

public class PostDetailPresenterImpl implements PostDetailPresenter, PostDetailInteractor.OnPostQueryFinishedListener {

    private PostDetailView postDetailView;
    private PostDetailInteractor postDetailInteractor;
    private CommentsAdapterView commentsAdapterView;
    private String postKey;

    public PostDetailPresenterImpl(PostDetailView postDetailView, String mPostKey){
        this.postDetailView = postDetailView;
        this.postDetailInteractor = new PostDetailInteractorImpl(this);
        this.postKey = mPostKey;
    }

    public PostDetailPresenterImpl(CommentsAdapter adapter){
        this.commentsAdapterView = adapter;
        this.postDetailInteractor = new PostDetailInteractorImpl(this);
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
    public void setAns(String postKey, String answer) {
        if(postDetailInteractor != null)
            postDetailInteractor.setComment(postKey,answer);
    }

    @Override
    public void setBookMark(String mPostKey) {
        if(postDetailInteractor != null)
           postDetailInteractor.setBookMark(mPostKey);
    }

    @Override
    public void requestAnswers(String postKey) {
        if(postDetailInteractor != null)
            postDetailInteractor.requestComments(postKey);
    }

    @Override
    public void getAnswers(ArrayList<Comment> comments, ArrayList<String> keys) {
        if(commentsAdapterView != null){
            commentsAdapterView.addAllComments(comments);
            commentsAdapterView.addAllKeys(keys);
        }


    }

    @Override
    public void addLike(String mPostKey, String mCommentKey) {
        if(postDetailInteractor != null)
            postDetailInteractor.addLike(mPostKey, mCommentKey);
    }


    @Override
    public void requestPostAuthor(String authorKey) {
        if(postDetailInteractor != null)
            postDetailInteractor.requestAuthor(authorKey);
    }
}
