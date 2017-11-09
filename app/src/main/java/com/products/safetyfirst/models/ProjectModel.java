package com.products.safetyfirst.models;

/**
 * Created by vikas on 05/10/17.
 */

public class ProjectModel {
    private String username;
    private String company;
    private String description;
    Long timestamp;

    public ProjectModel() {

    }

    public ProjectModel(String username, String company, String description) {
        this.username = username;
        this.company = company;
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
