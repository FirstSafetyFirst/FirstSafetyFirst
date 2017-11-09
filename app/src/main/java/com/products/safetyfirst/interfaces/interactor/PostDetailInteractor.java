package com.products.safetyfirst.interfaces.interactor;

/**
 * Created by vikas on 30/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface PostDetailInteractor {

    void requestPost(String mPostKey, PostDetailInteractor.OnPostQueryFinishedListener listener);

    void requestAuthor(String mAuthorKey);

    void setBookMark(String mPostKey);

    void setViews(String mPostKey);

    void setComment(String mPostKey, String mAnswer);

    void requestComments(String mPostKey);

    void addLike(String mPostKey, String mCommentKey);

    interface OnPostQueryFinishedListener {

        void requestPostAuthor(String authorKey);
    }


}
