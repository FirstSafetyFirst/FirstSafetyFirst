package com.products.safetyfirst.Pojos;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


/**
 * Created by rishabh on 4/1/17.
 */

public class NotificationModel extends RealmObject {

    public static final int NEWS = 1;
    public static final int NEWS_WITH_IMAGE = 2;
    public static final int COMMENT_ON_POST = 3;

    @PrimaryKey
    private String id;

    @Required
    private String body;

    private String title;

    private int type;

    public void setId(String id) {
        this.id = id;
    }

    private String extraString;

    private boolean seen;

    public NotificationModel() {
        Long idLong = System.currentTimeMillis();
        this.id = idLong.toString();     // To set a primary key using current timestamp
        this.seen = false;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExtraString() {
        return extraString;
    }

    public void setExtraString(String extraString) {
        this.extraString = extraString;
    }

    public String getId() {
        return id;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
