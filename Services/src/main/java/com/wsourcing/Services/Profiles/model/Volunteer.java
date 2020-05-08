package com.wsourcing.Services.Profiles.model;

public class Volunteer {

    private  String title ;
    private String company ;
    private String         date_range ;
    private String location ;
    private String         cause ;
    private String description ;

    public Volunteer() {
    }

    public Volunteer(String title, String company, String date_range, String location, String cause, String description) {
        this.title = title;
        this.company = company;
        this.date_range = date_range;
        this.location = location;
        this.cause = cause;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate_range() {
        return date_range;
    }

    public void setDate_range(String date_range) {
        this.date_range = date_range;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}