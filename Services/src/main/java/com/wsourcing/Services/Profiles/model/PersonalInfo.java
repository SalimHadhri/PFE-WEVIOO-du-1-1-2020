package com.wsourcing.Services.Profiles.model;

import java.util.ArrayList;


//Personal infos relating to the candidate
public class PersonalInfo {

    private String name;
    private String headline;
    private String company;
    private String  school;
    private String  location;
    private String summary;
    private String image;
    private String followers;
    private String email;
    private String  phone;
    private String connected;
    private ArrayList<String> websites;

    public PersonalInfo() {
    }

    public PersonalInfo(String name, String headline, String company, String school, String location, String summary, String image, String followers, String email, String phone, String connected, ArrayList<String> websites) {
        this.name = name;
        this.headline = headline;
        this.company = company;
        this.school = school;
        this.location = location;
        this.summary = summary;
        this.image = image;
        this.followers = followers;
        this.email = email;
        this.phone = phone;
        this.connected = connected;
        this.websites = websites;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }

    public ArrayList<String> getWebsites() {
        return websites;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }
}
