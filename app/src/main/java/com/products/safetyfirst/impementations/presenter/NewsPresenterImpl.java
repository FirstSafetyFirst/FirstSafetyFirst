package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.adapters.NewsAdapter;
import com.products.safetyfirst.impementations.Interactor.NewsInteractorImpl;
import com.products.safetyfirst.interfaces.adapter.NewsAdapterView;
import com.products.safetyfirst.interfaces.interactor.NewsInteractor;
import com.products.safetyfirst.interfaces.presenter.NewsPresenter;
import com.products.safetyfirst.interfaces.view.NewsView;
import com.products.safetyfirst.models.NewsModel;

import java.util.ArrayList;

/**
 * Created by vikas on 14/11/17.
 */

public class NewsPresenterImpl implements NewsPresenter, NewsInteractor.OnUpdateFinishedListener {

    private NewsView eventsView;
    private final NewsInteractor eventsInteractor;
    private NewsAdapterView eventsAdapterView;

    public NewsPresenterImpl(NewsView eventsView){

        this.eventsView = eventsView;
        this.eventsInteractor = new NewsInteractorImpl(this);

    }

    public NewsPresenterImpl(NewsAdapter adapter){
        this.eventsAdapterView = adapter;
        this.eventsInteractor = new NewsInteractorImpl(this);
    }

    @Override
    public void onDestroy() {
        eventsView = null;
    }

    @Override
    public void request() {
        eventsInteractor.requestNews();
    }

    @Override
    public void getChildren(ArrayList<NewsModel> news) {
        eventsAdapterView.addAllEvents(news);
    }

    @Override
    public void addAction(String mNewsId, String mProfileKey, int mAction) {
        if (eventsView != null) {
            eventsView.showProgress();
        }
        eventsInteractor.addAction(mNewsId, mProfileKey, mAction);
    }

    @Override
    public void onActionError(String message) {
        if (eventsView != null) {
            eventsView.hideProgress();
        }
    }

    @Override
    public void onActionSuccess(String message) {
        if (eventsView != null) {
            eventsView.hideProgress();
        }
    }

    @Override
    public void getKeys(ArrayList<String> newsArrayKey) {
        if(eventsAdapterView != null){
            eventsAdapterView.addAllKeys(newsArrayKey);
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }
}
