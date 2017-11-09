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

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public long getNumShares() {
        return numShares;
    }

    public void setNumShares(long numShares) {
        this.numShares = numShares;
    }

    public long getNumViews() {
        return numViews;
    }

    public void setNumViews(long numViews) {
        this.numViews = numViews;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVisitor_list() {
        return visitor_list;
    }

    public void setVisitor_list(String visitor_list) {
        this.visitor_list = visitor_list;
    }

    public Map<String, Object> getAction() {
        return action;
    }

    public void setAction(Map<String, Object> action) {
        this.action = action;
    }

    public Map<String, Object> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Map<String, Object> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
