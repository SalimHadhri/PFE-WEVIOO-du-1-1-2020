package com.wsourcing.Services.Profiles.model;


import java.util.ArrayList;

public class Accomplishment {

    private ArrayList<String>  publications ;
    private ArrayList<String> certifications ;
    private ArrayList<String> patents ;
    private ArrayList<String> courses ;
    private ArrayList<String> projects ;
    private ArrayList<String> honors ;
    private ArrayList<String> test_scores ;
    private ArrayList<String> languages ;
    private ArrayList<String> organizations ;

    public Accomplishment(ArrayList<String> publications, ArrayList<String> certifications,
                          ArrayList<String> patents, ArrayList<String> courses,
                          ArrayList<String> projects, ArrayList<String> honors,
                          ArrayList<String> test_scores, ArrayList<String> languages,
                          ArrayList<String> organizations) {
        this.publications = publications;
        this.certifications = certifications;
        this.patents = patents;
        this.courses = courses;
        this.projects = projects;
        this.honors = honors;
        this.test_scores = test_scores;
        this.languages = languages;
        this.organizations = organizations;
    }

    public Accomplishment() {
    }

    public ArrayList<String> getPublications() {
        return publications;
    }

    public void setPublications(ArrayList<String> publications) {
        this.publications = publications;
    }

    public ArrayList<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(ArrayList<String> certifications) {
        this.certifications = certifications;
    }

    public ArrayList<String> getPatents() {
        return patents;
    }

    public void setPatents(ArrayList<String> patents) {
        this.patents = patents;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<String> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<String> projects) {
        this.projects = projects;
    }

    public ArrayList<String> getHonors() {
        return honors;
    }

    public void setHonors(ArrayList<String> honors) {
        this.honors = honors;
    }

    public ArrayList<String> getTest_scores() {
        return test_scores;
    }

    public void setTest_scores(ArrayList<String> test_scores) {
        this.test_scores = test_scores;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public ArrayList<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(ArrayList<String> organizations) {
        this.organizations = organizations;
    }
}
