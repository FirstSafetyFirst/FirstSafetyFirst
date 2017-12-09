package com.products.safetyfirst.interfaces.presenter;


import com.products.safetyfirst.Pojos.Comment;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.Pojos.UserModel;

import java.util.ArrayList;

/**
 * Created by vikas on 30/10/17.
 */

public interface PostDetailPresenter {

    void requestPost();

    void getPost(PostModel post);

    void getAuthor(UserModel user);

    void onDestroy();

    void setAns(String postKey, String answer);

    void setBookMark(String mPostKey);

    void requestAnswers(String postKey);

    void getAnswers(ArrayList<Comment> comments, ArrayList<String> keys);

    void addLike(String mPostKey, String mCommentKey);
}
