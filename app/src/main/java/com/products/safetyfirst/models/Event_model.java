package com.products.safetyfirst.models;

/**
 * Created by profileconnect on 24/04/17.
 */

public class Event_model {

    String url,title,desc,favicon_url,organizer;
    Integer timestamp;

    public Event_model(String url, String title, String desc, String favicon_url, String organizer) {
        this.url = url;
        this.title = title;
        this.desc = desc;
        this.favicon_url = favicon_url;
        this.organizer = organizer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFavicon_url() {
        return favicon_url;
    }

    public void setFavicon_url(String favicon_url) {
        this.favicon_url = favicon_url;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}
