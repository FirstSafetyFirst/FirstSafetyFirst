package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 06/10/17.
 */

public interface NewsDetailInteractor {
    void requestNews(String mNewsKey);

    void setBookMark(String mNewsKey);
}
