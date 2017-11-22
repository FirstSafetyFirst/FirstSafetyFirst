package com.products.safetyfirst.Pojos;

/**
 * Created by vikas on 05/10/17.
 */

public class InterestModel {

    private String interest;
    private boolean liked;

    public InterestModel() {

    }

    public InterestModel(String interest, boolean liked) {
        this.interest = interest;
        this.liked = liked;

    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }


}
