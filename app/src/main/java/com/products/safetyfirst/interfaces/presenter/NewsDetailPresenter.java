package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.NewsModel;

/**
 * Created by vikas on 06/10/17.
 */

public interface NewsDetailPresenter {
    void requestNews();

    void getNews(NewsModel news);

    void onDestroy();

    void setBookMark(String mNewsKey);
}
