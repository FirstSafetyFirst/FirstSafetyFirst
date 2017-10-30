package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;

/**
 * Created by vikas on 30/10/17.
 */

public interface PostDetailView {

    void setBookMark();

    void share();

    void addComment();

    void setPost(PostModel post);

    void setAuthor(UserModel user);

}
