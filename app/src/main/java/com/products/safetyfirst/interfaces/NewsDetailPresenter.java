package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.News_model;

/**
 * Created by vikas on 06/10/17.
 */

public interface NewsDetailPresenter {
    void requestNews();

    void getNews(News_model news);

    void onDestroy();

    void setBookMark(String mNewsKey);
}
