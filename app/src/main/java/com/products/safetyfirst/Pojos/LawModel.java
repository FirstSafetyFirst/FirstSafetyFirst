package com.products.safetyfirst.Pojos;

/**
 * Created by Vikas on 15-05-2017.
 */

public class LawModel {
    private String img_url;
    private String title;

    public LawModel(String img_url, String title) {
        this.img_url = img_url;
        this.title = title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
