package com.products.safetyfirst.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vikas on 09-06-2017.
 *
 * Notes:  1. use username not full_name
 *         2. use photoUrl not userImage
 *         3. use answers_given not answersGiven
 *         4. use questions_asked not questionsAsked
 */

public class UserModel {
    public final Map<String, Boolean> newsBookmarks = new HashMap<>();
    private String name;
    private int rank;
    private String email;
    private Map<String, Object> following;
    private ArrayList<String> news;
    private ArrayList<String> topics;
    private boolean notGoodUser;
    private boolean verified;
    private String photoUrl;
    private int answers_given;
    private int questions_asked;
    private String company;
    private String designation;
    private String certificate;
    private long phone;
    private Map<String, Boolean> posts;
    private String city;

    public static final String USER_LINK = "users";
    public static final String USER_PHOTO_LINK = "photoUrl";
    public static final String USER_NAME_LINK = "username";
    public static final String USER_EMAIL_LINK = "email";
    public static final String USER_POSTS_LINK = "user-posts";

    public UserModel(){}


    public UserModel(String username, String email, String photoUrl){
        this.name = username;
        this.email      = email;
        this.photoUrl   = photoUrl;
    }

    public UserModel(String username, String email, String phone,String photoUrl,String certificate, ArrayList<String> news, ArrayList<String> topics){
        this.name = username;
        this.email      = email;
        this.phone= Long.parseLong(phone);
        this.photoUrl   = photoUrl;
        this.news       = news;
        this.topics     = topics;
        this.certificate = certificate;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setPhone(String phoneNo){
        phone= Long.parseLong(phoneNo);
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCompany(){ return company;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getFollowing() {
        return following;
    }

    public Map<String, Boolean> getPosts() {
        return posts;
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

    public int getAnswers_given() {
        return answers_given;
    }

    public void setAnswers_given(int answers_given) {
        this.answers_given = answers_given;
    }

    public int getQuestions_asked() {
        return questions_asked;
    }

    public void setQuestions_asked(int questions_asked) {
        this.questions_asked = questions_asked;
    }

    public String getCertificate() {
        return certificate;
    }

    public String getPhone(){

        return String.valueOf(phone);

    }

    public String getCity() {
        return city;
    }

}
