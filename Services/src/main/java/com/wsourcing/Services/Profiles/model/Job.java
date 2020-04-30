package com.wsourcing.Services.Profiles.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Job {

    private String title ;
    private String company;
    private String date_range;
    private String location;
    private String description;
    private String li_company_url;
    private int  duration ;

    private Skill skills ;

    public Job(String title, String company, String date_range, String location, String description, String li_company_url, int duration, Skill skills) {
        this.title = title;
        this.company = company;
        this.date_range = date_range;
        this.location = location;
        this.description = description;
        this.li_company_url = li_company_url;
        this.duration = duration;
        this.skills = skills;
    }

    public Job() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLi_company_url() {
        return li_company_url;
    }

    public void setLi_company_url(String li_company_url) {
        this.li_company_url = li_company_url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Skill getSkills() {
        return skills;
    }

    public void setSkills(Skill skills) {
        this.skills = skills;
    }

}
