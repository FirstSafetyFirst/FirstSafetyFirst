package com.products.safetyfirst.models;

import java.util.ArrayList;

/**
 * Created by Vikas on 09-06-2017.
 */

public class UserModel {
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


    public UserModel(String name, String email, String photoUrl){
        this.name       = name;
        this.email      = email;
        this.photoUrl   = photoUrl;
    }

    public UserModel(String name, String email, String photoUrl,  ArrayList<String> followers,  ArrayList<String> following, ArrayList<String> news, ArrayList<String> topics){
        this.name       = name;
        this.email      = email;
        this.photoUrl   = photoUrl;
        this.followers  = followers;
        this.following  = following;
        this.news       = news;
        this.topics     = topics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
