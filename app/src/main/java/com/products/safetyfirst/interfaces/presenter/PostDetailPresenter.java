package com.products.safetyfirst.interfaces.presenter;


import com.products.safetyfirst.models.Comment;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

import java.util.ArrayList;
import java.util.List;

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
