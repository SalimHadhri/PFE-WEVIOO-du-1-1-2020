package com.wsourcing.Services.Profiles.model;

public class TechProfile {
    private float coef_skills ;
    private int  mean_duration;
    private int profile_categ_seniority;

    public TechProfile() {
    }

    public TechProfile(float coef_skills, int mean_duration, int profile_categ_seniority) {
        this.coef_skills = coef_skills;
        this.mean_duration = mean_duration;
        this.profile_categ_seniority = profile_categ_seniority;
    }

    public float getCoef_skills() {
        return coef_skills;
    }

    public void setCoef_skills(float coef_skills) {
        this.coef_skills = coef_skills;
    }

    public int getMean_duration() {
        return mean_duration;
    }

    public void setMean_duration(int mean_duration) {
        this.mean_duration = mean_duration;
    }

    public int getProfile_categ_seniority() {
        return profile_categ_seniority;
    }

    public void setProfile_categ_seniority(int profile_categ_seniority) {
        this.profile_categ_seniority = profile_categ_seniority;
    }
}
