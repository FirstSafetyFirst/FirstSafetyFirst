package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.models.NewsModel;

/**
 * Created by vikas on 06/10/17.
 */

public interface NewsDetailView {

    void setBookMark();

    void share();

    void readMore();

    void setNews(NewsModel news);
}
