package com.products.safetyfirst.activity;

/**
 * Created by krishna on 6/6/17.
 */

import com.google.firebase.database.IgnoreExtraProperties;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

   private String name;
    private int rank;
    private String email;
    private ArrayList<String> followers;
    private ArrayList<String> following;
    private ArrayList<String> news;
    private ArrayList<String> topics;
    private boolean notGoodUser;
    private boolean verified;
    private String photoUrl;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFollowers() {
        return followers;
    }

    public void setFollowers(ArrayList<String> followers) {
        this.followers = followers;
    }

    public ArrayList<String> getFollowing() {
        return following;
    }

    public void setFollowing(ArrayList<String> following) {
        this.following = following;
    }

    public ArrayList<String> getNews() {
        return news;
    }

    public void setNews(ArrayList<String> news) {
        this.news = news;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public boolean isNotGoodUser() {
        return notGoodUser;
    }

    public void setNotGoodUser(boolean notGoodUser) {
        this.notGoodUser = notGoodUser;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name, String email, String photoUrl) {
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }




}

// [END blog_user_class]