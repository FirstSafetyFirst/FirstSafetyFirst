package com.products.safetyfirst.models;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<String> imageList;     // For Multiple Image Upload

    public Discussion_model(){}

    public Discussion_model(String uid, String title, String authorImageUrl, String favicon, String body, Integer timestamp, String file, ArrayList<String> imageList) {
        this.title          = title;
        this.authorImageUrl = authorImageUrl;
        this.favicon        = favicon;
        this.body           = body;
        this.imageList      = imageList;
        this.file           = file;
        this.timestamp      = timestamp;
        this.uid            = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return authorImageUrl;
    }

    public void setImg_url(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
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

    public String getImage() {return image;}
    public String getPostAuthorUID() {return uid;}

    public void setDesc(String body) {
        this.body = body;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
