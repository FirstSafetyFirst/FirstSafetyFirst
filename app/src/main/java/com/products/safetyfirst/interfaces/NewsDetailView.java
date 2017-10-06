package com.products.safetyfirst.interfaces;

import com.products.safetyfirst.models.News_model;

/**
 * Created by vikas on 06/10/17.
 */

public interface NewsDetailView {

    void setBookMark();

    void share();

    void readMore();

    void setNews(News_model news);
}
