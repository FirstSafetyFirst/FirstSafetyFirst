package com.products.safetyfirst.impementations.presenter;

import com.products.safetyfirst.impementations.Interactor.NewsDetailInteractorImpl;
import com.products.safetyfirst.interfaces.interactor.NewsDetailInteractor;
import com.products.safetyfirst.interfaces.presenter.NewsDetailPresenter;
import com.products.safetyfirst.interfaces.view.NewsDetailView;
import com.products.safetyfirst.models.NewsModel;

/**
 * Created by vikas on 06/10/17.
 */

public class NewsDetailPresenterImpl implements NewsDetailPresenter {

    private NewsDetailView newsDetailView;
    private final NewsDetailInteractor newsDetailInteractor;
    private final String mNewsKey;

    public NewsDetailPresenterImpl(NewsDetailView newsDetailView, String mNewsKey) {
        this.newsDetailView = newsDetailView;
        this.mNewsKey = mNewsKey;
        this.newsDetailInteractor = new NewsDetailInteractorImpl(this);
    }


    @Override
    public void requestNews() {
        newsDetailInteractor.requestNews(mNewsKey);
    }

    @Override
    public void getNews(NewsModel news) {
        newsDetailView.setNews(news);
    }

    @Override
    public void onDestroy() {
        newsDetailView = null;
    }

    @Override
    public void setBookMark(String mNewsKey) {
        newsDetailInteractor.setBookMark(mNewsKey);
    }
}
