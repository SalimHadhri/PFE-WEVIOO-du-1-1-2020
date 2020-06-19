package com.wsourcing.Authentication.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


public class UserKnowage {




    private String username;

    private String password;







    public UserKnowage(String username, String password) {
        this.username = username;
        this.password = password;

    }



    protected UserKnowage(){}


    @Override
    public String toString() {
        return "UserKnowage{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
