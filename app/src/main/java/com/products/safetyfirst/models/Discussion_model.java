package com.products.safetyfirst.models;

/**
 * Created by profileconnect on 24/04/17.
 */

public class Discussion_model {

    private String title,img_url,favicon,desc;
    private Integer timestamp;

    public Discussion_model(){}

    public Discussion_model(String title, String img_url, String favicon, String desc, Integer timestamp) {
        this.title = title;
        this.img_url = img_url;
        this.favicon = favicon;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
