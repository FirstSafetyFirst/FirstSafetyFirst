package com.products.safetyfirst.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 26/09/17.
 */

public class Comment  {

    private String uid;
    private String author;
    public String text;
    private int upvoteCount = 0;
    private int downvoteCount = 0;
    private String xmlText = null;
    private String image = null;
    private String file = null;
    private Map<String, Boolean> upvoteusers = new HashMap<>();

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text, String xmlText, String image, String file , int upvoteCount ) {
        this.uid = uid;
        this.author = author;
        this.text = text;
        this.xmlText = xmlText;
        this.image = image;
        this.file = file;
        this.upvoteCount=upvoteCount;
    }

    public Comment(String uid, String author, String text, int upvoteCount ) {
        this.uid = uid;
        this.author = author;
        this.text = text;
        this.xmlText = text;
        this.upvoteCount=upvoteCount;
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

    public String getXmlText() {
        return xmlText;
    }

    public void setXmlText(String xmlText) {
        this.xmlText = xmlText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Map<String, Boolean> getUpvoteusers() {
        return upvoteusers;
    }

    public void setUpvoteusers(Map<String, Boolean> upvoteusers) {
        this.upvoteusers = upvoteusers;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("text", text);
        result.put("xmlText", xmlText);
        result.put("file", file);
        result.put("image", image);
        result.put("upvotecount",upvoteCount);
        result.put("upvoteusers",upvoteusers);
        return result;
    }

}

