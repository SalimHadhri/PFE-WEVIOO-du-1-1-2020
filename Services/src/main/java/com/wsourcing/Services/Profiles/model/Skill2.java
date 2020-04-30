package com.wsourcing.Services.Profiles.model;

public class Skill2 {

    private String name ;
    private String endorsements ;

    public Skill2() {
    }

    public Skill2(String name, String endorsements) {
        this.name = name;
        this.endorsements = endorsements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndorsements() {
        return endorsements;
    }

    public void setEndorsements(String endorsements) {
        this.endorsements = endorsements;
    }
}
