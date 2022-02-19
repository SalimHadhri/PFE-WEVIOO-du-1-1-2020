package com.wsourcing.Services.Profiles.model;

import java.util.ArrayList;
import java.util.List;


//Experience detained by the candidate in therm of jobs, education and volunteering
public class Experience {

    private List<Job> jobs ;
    private List<Education> education ;
    private ArrayList<Volunteer>  volunteering ;

    public Experience() {
    }

    public Experience(List<Job> jobs, List<Education> education, ArrayList<Volunteer> volunteering) {
        this.jobs = jobs;
        this.education = education;
        this.volunteering = volunteering;
    }

    public ArrayList<Volunteer> getVolunteering() {
        return volunteering;
    }

    public void setVolunteering(ArrayList<Volunteer> volunteering) {
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


}
