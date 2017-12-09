package com.products.safetyfirst.Pojos;

/**
 * Created by vikas on 05/10/17.
 */

public class ProjectModel {
    private String designation;
    private String company;
    private String description;
    private String evaluation;
    private String years;

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    Long timestamp;

    public ProjectModel() {

    }

    public ProjectModel(String username, String company, String description) {
        this.designation = username;
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

    public String getDesignation() {

        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
