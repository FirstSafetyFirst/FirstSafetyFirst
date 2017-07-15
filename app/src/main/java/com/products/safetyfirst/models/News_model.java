package com.products.safetyfirst.models;

/**
 * Created by profileconnect on 24/04/17.
 */

public class News_model {

    private String title,imgUrl,favicon, body, uid, author;
    private Integer timestamp;

    public News_model(){}

    public News_model(String title, String imgUrl, String favicon, String body,String author, Integer timestamp) {
        this.title = title;
        this.imgUrl = imgUrl;
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
        return imgUrl;
    }

    public void setImg_url(String imgUrl) {
        this.imgUrl = imgUrl;
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
