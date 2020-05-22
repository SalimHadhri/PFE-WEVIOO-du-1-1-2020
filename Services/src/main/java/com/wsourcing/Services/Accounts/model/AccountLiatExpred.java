package com.wsourcing.Services.Accounts.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


public class AccountLiatExpred {





    @Id
    private int id ;
    private String nameUserAccountExpired ;
    private String liatExpired ;
    private String urlLinkdin ;


    public AccountLiatExpred() {
    }

    public AccountLiatExpred(String nameUserAccountExpired, String liatExpired, String urlLinkdin) {
        this.nameUserAccountExpired = nameUserAccountExpired;
        this.liatExpired = liatExpired;
        this.urlLinkdin = urlLinkdin;
    }


    public AccountLiatExpred(int id, String nameUserAccountExpired, String liatExpired, String urlLinkdin) {
        this.id = id;
        this.nameUserAccountExpired = nameUserAccountExpired;
        this.liatExpired = liatExpired;
        this.urlLinkdin = urlLinkdin;
    }

    public String getNameUserAccountExpired() {
        return nameUserAccountExpired;
    }

    public void setNameUserAccountExpired(String nameUserAccountExpired) {
        this.nameUserAccountExpired = nameUserAccountExpired;
    }

    public String getLiatExpired() {
        return liatExpired;
    }

    public void setLiatExpired(String liatExpired) {
        this.liatExpired = liatExpired;
    }

    public String getUrlLinkdin() {
        return urlLinkdin;
    }

    public void setUrlLinkdin(String urlLinkdin) {
        this.urlLinkdin = urlLinkdin;
    }

    @Override
    public String toString() {
        return "AccountLiatExpred{" +
                "nameUserAccountExpired='" + nameUserAccountExpired + '\'' +
                ", liatExpired='" + liatExpired + '\'' +
                ", urlLinkdin='" + urlLinkdin + '\'' +
                '}';
    }
}
