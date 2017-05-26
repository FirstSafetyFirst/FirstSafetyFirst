package com.products.safetyfirst.models;

/**
 * Created by profileconnect on 24/04/17.
 */

public class News_model {

    private String title,img_url,favicon, body, uid, author;
    private Integer timestamp;

    public News_model(String title, String img_url, String favicon, String body,String author, Integer timestamp) {
        this.title = title;
        this.img_url = img_url;
        this.favicon = favicon;
        this.body = body;
        this.timestamp = timestamp;
        this.author = author;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor(){ return author;}

    public void setAuthor(String author) { this.author = author; }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
