package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.PostAdapter;
import com.products.safetyfirst.impementations.Interactor.PostInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.PostAdapterView;
import com.products.safetyfirst.interfaces.interactor.PostInteractor;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.interfaces.view.PostsView;
import com.products.safetyfirst.Pojos.PostModel;

import java.util.ArrayList;

/**
 * Created by vikas on 01/11/17.
 */

public class PostPresenterImpl implements PostPresenter, PostInteractor.OnUpdateFinishedListener {
    private PostsView eventsView;
    private final PostInteractor eventsInteractor;
    private PostAdapterView eventsAdapterView;

    public PostPresenterImpl (PostsView eventsView){

        this.eventsView = eventsView;
        this.eventsInteractor = new PostInteractorImpl(this);

    }

    public PostPresenterImpl(PostAdapter adapter){
        this.eventsAdapterView = adapter;
        this.eventsInteractor = new PostInteractorImpl(this);
    }

    @Override
    public void onDestroy() {
        eventsView = null;
    }

    @Override
    public void request() {
        eventsInteractor.requestPosts();
    }

    @Override
    public void getChildren(ArrayList<PostModel> postModels) {
        eventsAdapterView.addAllEvents(postModels);
    }

    @Override
    public void addAction(String mEventId, String mProfileKey, int mAction) {
        if (eventsView != null) {
            eventsView.showProgress();
        }
        eventsInteractor.addAction(mEventId, mProfileKey, mAction);
    }

    @Override
    public void onActionError(String message) {
        if (eventsView != null) {
            eventsView.hideProgress();
        }
    }

    @Override
    public void onActionSuccess(String message) {

    }

    @Override
    public void getKeys(ArrayList<String> postArrayKey) {
        if(eventsAdapterView != null){
            eventsAdapterView.addAllKeys(postArrayKey);
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }
}
