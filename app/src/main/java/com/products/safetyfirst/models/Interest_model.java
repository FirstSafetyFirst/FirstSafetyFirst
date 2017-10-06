package com.products.safetyfirst.models;

/**
 * Created by vikas on 05/10/17.
 */

public class Interest_model {

    private String interest;
    private boolean liked;

    public Interest_model() {

    }

    public Interest_model(String interest, boolean liked) {
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
