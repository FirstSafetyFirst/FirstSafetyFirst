package com.products.safetyfirst.interfaces.view;

import com.products.safetyfirst.Pojos.Comment;
import com.products.safetyfirst.Pojos.PostModel;
import com.products.safetyfirst.Pojos.UserModel;

import java.util.List;

/**
 * Created by vikas on 30/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface PostDetailView {

    void setBookMark();

    void share();

    void addComment();

    void setPost(PostModel post);

    void setAuthor(UserModel user);

    void setComments(List<Comment> comments, List<String> keys);

}
