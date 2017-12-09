package com.products.safetyfirst.Pojos;

/**
 * Created by vikas on 19/10/17.
 */

public class TrainingCenterModel {

    private float lat;
    private float lng;
    private String name;
    private int rating;
    private int shares;
    private String snippet;

    public TrainingCenterModel(){}

    public TrainingCenterModel(String name, float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.rating = 0;
        this.shares = 0;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getShares() {
        return shares;
    }

    public String getSnippet() {
        return snippet;
    }

}
