package com.wsourcing.Services.Profiles.model;


//Education accomplishments through the candidate studies
public class Education {

    private String name ;
    private String degree ;
    private String grades ;
    private String field_of_study ;
    private String date_range ;
    private String activities ;

    public Education() {
    }

    public Education(String name, String degree, String grades, String field_of_study, String date_range, String activities) {
        this.name = name;
        this.degree = degree;
        this.grades = grades;
        this.field_of_study = field_of_study;
        this.date_range = date_range;
        this.activities = activities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public String getField_of_study() {
        return field_of_study;
    }

    public void setField_of_study(String field_of_study) {
        this.field_of_study = field_of_study;
    }

    public String getDate_range() {
        return date_range;
    }

    public void setDate_range(String date_range) {
        this.date_range = date_range;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }
}
