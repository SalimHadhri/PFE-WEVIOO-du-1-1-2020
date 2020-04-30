package com.wsourcing.Services.Profiles.model;

import java.util.ArrayList;
import java.util.List;

public class Experience {

    private List<Job> jobs ;
    private List<Education> education ;
    private ArrayList<String>  volunteering ;

    public Experience() {
    }

    public Experience(List<Job> jobs, List<Education> education, ArrayList<String> volunteering) {
        this.jobs = jobs;
        this.education = education;
        this.volunteering = volunteering;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public ArrayList<String> getVolunteering() {
        return volunteering;
    }

    public void setVolunteering(ArrayList<String> volunteering) {
        this.volunteering = volunteering;
    }
}
