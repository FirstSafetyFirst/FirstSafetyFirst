package com.products.safetyfirst.models;

import java.util.Map;

/**
 * Created by profileconnect on 24/04/17.
 */

public class EventModel {

    private String url,title,desc,thumb_url,organizer;
    private Object timestamp;
    private String deeplink;
    private long numShares;
    private long numViews;
    private String video_url;
    private String visitor_list;

    public Map<String, Object> action;
    public Map<String, Object> bookmarks;

    public EventModel(){

    }

    public EventModel(String video_url, String title, String desc, String thumb_url, String visitor_list) {
        this.video_url = video_url;
        this.title = title;
        this.desc = desc;
        this.thumb_url = thumb_url;
        this.visitor_list = visitor_list;
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

    public String getVideo() { return this.video_url; }

    public String getVisitors() {
        return this.visitor_list;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getThumbUrl() {
        return this.thumb_url;
    }

    public void setThumbUrl(String favicon_url) {
        this.thumb_url = favicon_url;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Object getTimestamp() {
        return timestamp;
    }

}
