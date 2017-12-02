package com.products.safetyfirst.Pojos;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikas on 24/04/17.
 */
@IgnoreExtraProperties
public class PostModel {

    private String author;
    private String authorImageUrl;
    private String body;
    private String deeplink;
    private String photoUrl;
    private int starCount = 0;
    private String title;
    private String uid;
    private List<String> imageList;
    private List<String> fileList;
    public Map<String, Boolean> stars = new HashMap<>();
    private String image;

    public PostModel() {
    }

    public PostModel(String title, String body, String uid, String author, List<String> imageList, List<String> fileList) {
        this.title = title;
        this.body = body;
        this.uid = uid;
        this.imageList = imageList;
        this.fileList = fileList;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public void setStars(Map<String, Boolean> stars) {
        this.stars = stars;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("image", image);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("authorImageUrl", authorImageUrl);
        result.put("imageList", imageList);

        return result;
    }
}
