package com.products.safetyfirst.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 24/04/17.
 */
@IgnoreExtraProperties
public class PostModel {

    private String title;
    private String body;
    private String uid;
    private List<String> imageList;
    private List<String> fileList;
    private long timestamp;
    private String postKey;


    private String authorImageUrl;
    private String author;
    public int starCount = 0;
    public Map<String, Boolean> stars = new HashMap<>();

    private String image;
    //  public String video;
    public String file;
    private String link;


    public PostModel() {
    }

    public PostModel(String title, String body, String uid, String author, List<String> imageList, List<String> fileList, long timestamp, String previousPost ) {
        this.title = title;
        this.body = body;
        this.uid = uid;
        this.imageList = imageList;
        this.fileList = fileList;
        this.timestamp = timestamp;
        this.postKey = previousPost;
        this.author = author;
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
        return postKey;
    }

    public void setPreviousPost(String previousPost) {
        this.postKey = previousPost;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("image", image);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("authorImageUrl", authorImageUrl);
        result.put("postLink", link);
        result.put("imageList", imageList);

        return result;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
