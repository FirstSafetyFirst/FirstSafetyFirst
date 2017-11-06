package com.products.safetyfirst.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikas on 24/04/17.
 */

public class PostModel {

    String title;
    String body;
    String uid;
    List<String> imageList;
    List<String> fileList;
    long timestamp;
    String previousPost;

    public PostModel() {
    }

    public PostModel(String title, String body, String uid, List<String> imageList, List<String> fileList, long timestamp, String previousPost ) {
        this.title = title;
        this.body = body;
        this.uid = uid;
        this.imageList = imageList;
        this.fileList = fileList;
        this.timestamp = timestamp;
        this.previousPost = previousPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousPost() {
        return previousPost;
    }

    public void setPreviousPost(String previousPost) {
        this.previousPost = previousPost;
    }

    private static int lastContactId = 0;

    public static List<PostModel> createPostList(int numContacts, int offset) {
        List<PostModel> contacts = new ArrayList<PostModel>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new PostModel("demo " + ++lastContactId, "body", "uid", null, null, 0, "prev"));
        }

        return contacts;
    }
}
