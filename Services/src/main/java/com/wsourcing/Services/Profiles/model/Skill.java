package com.wsourcing.Services.Profiles.model;


public class Skill {

    private int java ;
    private int jee ;

    public Skill(int java, int jee) {
        this.java = java;
        this.jee = jee;
    }

    public Skill() {
    }

    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getJee() {
        return jee;
    }

    public void setJee(int jee) {
        this.jee = jee;
    }
}
