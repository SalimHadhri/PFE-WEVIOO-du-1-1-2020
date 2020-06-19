package com.wsourcing.Authentication.knowage;

public class KnowageUser {

    private String Doc_Name ;
    private String Token ;
    private String User_ID ;


    public KnowageUser(String doc_Name, String token, String user_ID) {
        Doc_Name = doc_Name;
        Token = token;
        User_ID = user_ID;
    }

    public String getDoc_Name() {
        return Doc_Name;
    }

    public void setDoc_Name(String doc_Name) {
        Doc_Name = doc_Name;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }
}
