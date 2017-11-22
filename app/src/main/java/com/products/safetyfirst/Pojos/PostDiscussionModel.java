package com.products.safetyfirst.Pojos;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class PostDiscussionModel {
    public String key;
    public String title;
    public String body;
    public String author;
    public String authorEmail;
    public int starCount;
    public String authorPhoto;
    public long timestamp;

    public void setFromPostModel(PostModel post) {
        this.title = post.getTitle();
        this.body = post.getBody();
       // this.timestamp = post.getTimestamp();
    }
}
