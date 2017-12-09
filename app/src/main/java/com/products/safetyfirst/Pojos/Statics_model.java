package com.products.safetyfirst.Pojos;

/**
 * Created by profileconnect on 24/04/17.
 */

class Statics_model {

    private Integer point;
    private String texts;

    public Statics_model(String texts) {
        this.texts = texts;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }
}
