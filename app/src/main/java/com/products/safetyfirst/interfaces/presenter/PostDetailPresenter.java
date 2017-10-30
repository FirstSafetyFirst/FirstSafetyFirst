package com.products.safetyfirst.interfaces.presenter;


import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 30/10/17.
 */

public interface PostDetailPresenter {

    void requestPost();

    void getPost(PostModel post);

    void getAuthor(UserModel user);

    void onDestroy();

    void setBookMark(String mPostKey);
}
