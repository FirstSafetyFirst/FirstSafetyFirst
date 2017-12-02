package com.products.safetyfirst.Pojos;

import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 26/09/17.
 */

public class Comment  {

    private String uid;
    private String author;
    private String text;
    private int upvoteCount = 0;
    private int downvoteCount = 0;
    private String image = null;
    private Map<String, Boolean> upvoteusers = new HashMap<>();
    private Map<String, Boolean> downvoteusers = new HashMap<>();
    private @ServerTimestamp Date timestamp;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text, String image ) {
        this.uid = uid;
        this.author = author;
        this.text = text;
        this.image = image;
    }

    public Comment(String uid, String author, String text ) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(int upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public int getDownvoteCount() {
        return downvoteCount;
    }

    public void setDownvoteCount(int downvoteCount) {
        this.downvoteCount = downvoteCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Boolean> getUpvoteusers() {
        return upvoteusers;
    }

    public void setUpvoteusers(Map<String, Boolean> upvoteusers) {
        this.upvoteusers = upvoteusers;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Boolean> getDownvoteusers() {
        return downvoteusers;
    }

    public void setDownvoteusers(Map<String, Boolean> downvoteusers) {
        this.downvoteusers = downvoteusers;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("text", text);
        result.put("image", image);
        result.put("upvotecount",upvoteCount);
        result.put("upvoteusers",upvoteusers);
        return result;
    }

}

