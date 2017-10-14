package com.products.safetyfirst.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 24/04/17.
 */

public class Discussion_model {

    private String favicon;
    private Integer timestamp;

    private String authorImageUrl;
    private String uid;
    private String author;
    private String title;
    private String body;
    private String xmlBody;
    private int starCount = 0;
    private Map<String, Boolean> stars = new HashMap<>();

    private String image;
    private String file;
    private String link;
    private List<String> imageList;     // For Multiple Image Upload

    public Discussion_model() {
    }

    public Discussion_model(String uid, String title, String authorImageUrl, String body, Integer timestamp, String file, List<String> imageList) {
        this.title = title;
        this.authorImageUrl = authorImageUrl;
        this.body = body;
        this.imageList = imageList;
        this.file = file;
        this.timestamp = timestamp;
        this.uid = uid;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getXmlBody() {
        return xmlBody;
    }

    public void setXmlBody(String xmlBody) {
        this.xmlBody = xmlBody;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
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

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getDesc() {
        return body;
    }

    public void setDesc(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostAuthorUID() {
        return uid;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
